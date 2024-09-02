package com.hakaryzhang.lab.asset.admin.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import com.hakaryzhang.lab.asset.admin.common.Result;
import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import com.hakaryzhang.lab.asset.admin.enums.ContractApplicationStatus;
import com.hakaryzhang.lab.asset.admin.enums.PurchaseApplicationStatus;
import com.hakaryzhang.lab.asset.admin.service.purchase.PurchaseService;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationCreateForm;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationQueryForm;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhanghaijia
 */
@Slf4j
@RestController
@RequestMapping("/asset/admin/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;
    @PostMapping("/application/list")
    public Result getApplicationList(@RequestBody PurchaseApplicationQueryForm form) {
        return Result.success(
                purchaseService.queryPurchaseApplications(form)
        );
    }

    @PostMapping("/application/detail")
    public Result getApplicationDetail(Long id) {
        return Result.success();
    }

    @PostMapping("/application/create")
    public Result createApplication(@RequestBody PurchaseApplicationCreateForm form) {
        System.out.println(form);
        purchaseService.createApplication(form);
        return Result.success();
    }

    @PostMapping("/application/update")
    public Result updateApplication(@RequestBody PurchaseApplicationUpdateForm form) {
        boolean success = purchaseService.updateApplication(form);
        if (success) {
            return Result.success();
        } else {
            return Result.fail(500, "更新失败");
        }
    }

    @PostMapping("/application/statusChange")
    public Result changeApplicationStatus(Long id, Integer statusCode) {
        PurchaseApplicationStatus status = PurchaseApplicationStatus.of(statusCode);
        if (status == null) {
            return Result.fail(400, "Invalid status code");
        }
        if (!purchaseService.changeApplicationStatus(id, status)) {
            return Result.fail(500, "设备更新失败");
        }
        return Result.success();
    }

    @GetMapping("/application/status")
    public Result getApplicationStatus() {
        return  Result.success(Lists.newArrayList(PurchaseApplicationStatus.values()));
    }

    @GetMapping("/application/download")
    public void download(HttpServletResponse response) {
        List<PurchaseApplication> list = purchaseService.queryPurchaseApplications(new PurchaseApplicationQueryForm());

        //生成Excel
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("采购申请表", "采购申请表"), PurchaseApplication.class, list);
            String now = LocalDateTime.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            response.reset();
            String filename = "采购申请表" + "_" + now + ".xlsx";
            filename = new String(filename.getBytes(), StandardCharsets.ISO_8859_1);
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setHeader("content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
