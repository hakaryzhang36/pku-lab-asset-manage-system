package com.hakaryzhang.lab.asset.admin.service.purchase.dto;

import lombok.Data;

@Data
public class PurchaseApplicationQueryForm {
    private Long id;
    private String productName;
    private String productType;
    private String company;
    private Integer statusCode;
}
