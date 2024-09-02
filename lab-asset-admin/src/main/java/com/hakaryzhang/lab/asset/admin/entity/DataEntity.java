package com.hakaryzhang.lab.asset.admin.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

@Data
public abstract class DataEntity implements Serializable {
    @Excel(name = "序号", orderNum = "-1")
    private Long id;

    public <E extends DataEntity> E copy() {
        return SerializationUtils.clone((E)this);
    }
}
