package com.hakaryzhang.lab.asset.admin.service.contract.dto;

import lombok.Data;

@Data
public class ContractApplicationQueryForm {
    private String name;
    private String company;
    private Integer statusCode;
    private Long id;
}
