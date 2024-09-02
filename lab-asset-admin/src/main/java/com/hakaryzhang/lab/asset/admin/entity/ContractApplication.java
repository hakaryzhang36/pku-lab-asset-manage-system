package com.hakaryzhang.lab.asset.admin.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.hakaryzhang.lab.asset.admin.enums.ContractApplicationStatus;
import com.hakaryzhang.lab.asset.admin.service.contract.dto.ContractApplicationCreateForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author zhanghaijia
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractApplication extends DataEntity{
    @Excel(name = "合同名称")
    private String name;
    @Excel(name = "公司")
    private String company;
    @Excel(name = "审批状态")
    private String statusDesc;
    @Excel(name = "状态码")
    private Integer statusCode;
    @Excel(name = "附件地址")
    private String fileLocalPathsStr;

    public ContractApplication(ContractApplicationCreateForm form) {
        this.name = form.getName();
        this.company = form.getCompany();
        this.statusDesc = ContractApplicationStatus.SUBMITTED.getDesc();
        this.statusCode = ContractApplicationStatus.SUBMITTED.getCode();
        this.fileLocalPathsStr = String.join(",", form.getFilePaths());
    }
}
