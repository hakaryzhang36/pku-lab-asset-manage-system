package com.hakaryzhang.lab.asset.admin.mockDB;

import com.hakaryzhang.lab.asset.admin.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghaijia
 */
public abstract class Table<K extends DataEntity> {

    protected Long nextId = 1L;

    protected List<K> data = new ArrayList<>();

    public abstract boolean write();

    public long getNextId() {
        return nextId++;
    }

    public abstract List<K> readAll();

    public boolean add(K obj) {
        if (obj == null) {
            return false;
        }
        data.add(obj);
        return write();
    }
}
