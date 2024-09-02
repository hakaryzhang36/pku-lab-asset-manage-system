package com.hakaryzhang.lab.asset.admin.mockDB.executor;

import com.hakaryzhang.lab.asset.admin.entity.DataEntity;
import com.hakaryzhang.lab.asset.admin.mockDB.Table;
import com.hakaryzhang.lab.asset.admin.mockDB.example.Example;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanghaijia
 */
public class Executor<E extends DataEntity, T extends Table<E>> {
    private T table;

    private Executor() {}

    static <E extends DataEntity, T extends Table<E>> Executor<E, T> of(T table) {
        Executor<E, T> executor = new Executor<>();
        executor.table = table;
        return executor;
    }

    public List<E> selectAll() {
        List<E> list = table.readAll();
        return list.stream().map(d -> (E)d.copy()).collect(Collectors.toList());
    }

    public Long insert(E obj) {
        if (obj.getId() != null) {
            return null;
        }
        long id = table.getNextId();
        obj.setId(id);
        return table.add(obj) ? id : null;
    }

    public E selectById(Long id) {
        return table.readAll().stream()
                .filter(obj -> obj.getId().equals(id))
                .map(d -> (E)d.copy())
                .findFirst()
                .orElse(null);
    }

    public boolean updateByPrimaryKeySelective(E obj) {
        if (obj.getId() == null) {
            return false;
        }
        table.readAll().stream()
                .filter(d -> d.getId().equals(obj.getId()))
                .forEach(d -> {
                    for (Field field : obj.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        try {
                            if (field.get(obj) != null) {
                                field.set(d, field.get(obj));
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        return table.write();
    }

    public <EX extends Example<E>> List<E> selectByExample(EX example) {
        return table.readAll().stream()
                .filter(example::judge)
                .map(d -> (E)d.copy())
                .collect(Collectors.toList());
    }
}
