package com.hakaryzhang.lab.asset.admin.mockDB.example;


import com.hakaryzhang.lab.asset.admin.entity.DataEntity;
import org.apache.commons.math3.util.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 * @author zhanghaijia
 */
public abstract class Example<T extends DataEntity> {
    private final Map<String, Pair<Class, Object>> equalConditionMap = new HashMap<>();
    private final Map<String, String> likeConditionMap = new HashMap<>();

    protected void addEqualCondition(String propName, Class clz, Object obj) {
        equalConditionMap.put(propName, new Pair<>(clz, obj));
    }

    protected void addLikeCondition(String propName, String value) {
        likeConditionMap.put(propName, value);
    }

    public boolean judge(T entity) {
        AtomicBoolean b = new AtomicBoolean(true);
        equalConditionMap.entrySet().stream()
                .forEach(entry -> {
                    Class<? extends DataEntity> eClz = entity.getClass();
                    String fieldName = entry.getKey();
                    Class fieldClz = entry.getValue().getFirst();
                    Object conditionValue = entry.getValue().getSecond();
                    try {
                        Field field = eClz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        b.set(b.get() && fieldClz.cast(field.get(entity)).equals(conditionValue));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                });
        likeConditionMap.entrySet().stream()
                .forEach(entry -> {
                    Class<? extends DataEntity> eClz = entity.getClass();
                    String fieldName = entry.getKey();
                    String fieldValue = entry.getValue();
                    try {
                        Field field = eClz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        b.set(b.get() && ((String) field.get(entity)).contains(fieldValue));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                });
        return b.get();
    }
}
