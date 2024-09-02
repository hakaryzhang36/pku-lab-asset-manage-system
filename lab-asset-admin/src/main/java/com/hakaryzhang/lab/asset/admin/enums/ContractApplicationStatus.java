package com.hakaryzhang.lab.asset.admin.enums;

import lombok.AllArgsConstructor;

/**
 * @author zhanghaijia
 */
@AllArgsConstructor
public enum ContractApplicationStatus {
    SUBMITTED(0, "已提交"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已驳回"),;

    private final int code;
    private final String desc;

    public static ContractApplicationStatus of(int code) {
        for (ContractApplicationStatus v : ContractApplicationStatus.values()) {
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
