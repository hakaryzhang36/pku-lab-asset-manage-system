package com.hakaryzhang.lab.asset.admin.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhanghaijia
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device extends DataEntity{
    @Excel(name = "设备名称")
    private String deviceName;
    @Excel(name = "型号")
    private String productType;
    @Excel(name = "厂家")
    private String company;
    @Excel(name = "序列号")
    private String sn;
    @Excel(name = "状态")
    private String statusDesc;
    @Excel(name = "状态码")
    private Integer statusCode;
    @Excel(name = "备注")
    private String remark;

    @Override
    public Device copy() {
        Device copy = new Device();
        copy.setId(this.getId());
        copy.setDeviceName(this.getDeviceName());
        copy.setProductType(this.getProductType());
        copy.setCompany(this.getCompany());
        copy.setSn(this.getSn());
        copy.setRemark(this.getRemark());
        copy.setStatusCode(this.getStatusCode());
        copy.setStatusDesc(this.getStatusDesc());
        return copy;
    }
}
