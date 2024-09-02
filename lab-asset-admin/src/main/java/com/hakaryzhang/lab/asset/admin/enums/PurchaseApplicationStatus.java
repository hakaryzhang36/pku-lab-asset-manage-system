package com.hakaryzhang.lab.asset.admin.enums;

import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import lombok.AllArgsConstructor;

/**
 * @author zhanghaijia
 */
@AllArgsConstructor
public enum PurchaseApplicationStatus {
    SUBMITTED(0, "已提交"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已驳回"),;

    private final int code;
    private final String desc;

    public static PurchaseApplicationStatus of(int code) {
        for (PurchaseApplicationStatus v : PurchaseApplicationStatus.values()) {
            if (v.getCode() == code) {
                return v;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
