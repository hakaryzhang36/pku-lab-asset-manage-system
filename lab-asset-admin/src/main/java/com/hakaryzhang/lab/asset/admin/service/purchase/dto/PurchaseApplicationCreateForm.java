package com.hakaryzhang.lab.asset.admin.service.purchase.dto;

import com.hakaryzhang.lab.asset.admin.enums.PurchaseApplicationStatus;
import lombok.Data;

@Data
public class PurchaseApplicationCreateForm {
    private String productName;
    private String productType;
    private String company;
    private Integer totalPrice;
    private Integer count;
    private String remark;
}
