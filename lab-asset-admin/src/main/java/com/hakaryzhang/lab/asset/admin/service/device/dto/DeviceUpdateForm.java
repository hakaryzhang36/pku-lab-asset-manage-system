package com.hakaryzhang.lab.asset.admin.service.device.dto;

import lombok.Data;

@Data
public class DeviceUpdateForm {
    private Long id;
    private String deviceName;
    private String productType;
    private String company;
    private String sn;
    private String remark;
    private Integer statusCode;
}
