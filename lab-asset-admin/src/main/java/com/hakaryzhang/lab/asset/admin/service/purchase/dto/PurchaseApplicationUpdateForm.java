package com.hakaryzhang.lab.asset.admin.service.purchase.dto;

import lombok.Data;

@Data
public class PurchaseApplicationUpdateForm {
    private Long id;
    private String productName;
    private String productType;
    private String company;
    private Integer totalPrice;
    private Integer count;
    private String remark;
}
