package com.hakaryzhang.lab.asset.admin.enums;

import lombok.AllArgsConstructor;

/**
 * @author zhanghaijia
 */
@AllArgsConstructor
public enum DeviceStatus {
    USING(0, "使用中"),
    IDLE(1, "闲置中"),
    SCRAPPED(2, "已报废"),;

    private final int code;
    private final String desc;

    public static DeviceStatus of(int code) {
        for (DeviceStatus v : DeviceStatus.values()) {
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
