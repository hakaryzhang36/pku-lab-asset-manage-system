package com.hakaryzhang.lab.asset.admin.service.contract.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhanghaijia
 */
@Data
public class ContractApplicationCreateForm {
    private String name;
    private String company;
    private List<String> filePaths;
}
