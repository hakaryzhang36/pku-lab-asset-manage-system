package com.hakaryzhang.lab.asset.admin.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.hakaryzhang.lab.asset.admin.enums.PurchaseApplicationStatus;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationCreateForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhanghaijia
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseApplication extends DataEntity {

    @Excel(name = "产品名称")
    private String productName;
    @Excel(name = "型号")
    private String productType;
    @Excel(name = "厂家")
    private String company;
    @Excel(name = "总价格")
    private Integer totalPrice;
    @Excel(name = "数量")
    private Integer count;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "申请状态码")
    private Integer statusCode;
    @Excel(name = "申请状态")
    private String statusDesc;

    public PurchaseApplication(PurchaseApplicationCreateForm form) {
        this.productName = form.getProductName();
        this.productType = form.getProductType();
        this.company = form.getCompany();
        this.totalPrice = form.getTotalPrice();
        this.count = form.getCount();
        this.remark = form.getRemark();
        this.statusCode = PurchaseApplicationStatus.SUBMITTED.getCode();
        this.statusDesc = PurchaseApplicationStatus.SUBMITTED.getDesc();
    }

    @Override
    public PurchaseApplication copy() {
        PurchaseApplication copy = new PurchaseApplication();
        copy.setId(this.getId());
        copy.setProductName(this.getProductName());
        copy.setProductType(this.getProductType());
        copy.setCompany(this.getCompany());
        copy.setTotalPrice(this.getTotalPrice());
        copy.setCount(this.getCount());
        copy.setRemark(this.getRemark());
        copy.setStatusCode(this.getStatusCode());
        copy.setStatusDesc(this.getStatusDesc());
        return copy;
    }
}
