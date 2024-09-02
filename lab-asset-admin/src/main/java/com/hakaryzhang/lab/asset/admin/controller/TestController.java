package com.hakaryzhang.lab.asset.admin.controller;


import com.hakaryzhang.lab.asset.admin.common.Result;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/t")
    public Result test() {
        return Result.success();
    }
}
