package com.hakaryzhang.lab.asset.admin.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import com.hakaryzhang.lab.asset.admin.common.Result;
import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.entity.Device;
import com.hakaryzhang.lab.asset.admin.enums.ContractApplicationStatus;
import com.hakaryzhang.lab.asset.admin.service.contract.ContractService;
import com.hakaryzhang.lab.asset.admin.service.contract.dto.ContractApplicationCreateForm;
import com.hakaryzhang.lab.asset.admin.service.contract.dto.ContractApplicationQueryForm;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhanghaijia
 */
@Slf4j
@RestController
@RequestMapping("/asset/admin/contract")
public class ContractController {
    @Autowired
    ContractService contractService;

    @PostMapping("/application/list")
    public Result searchContractApplication(@RequestBody ContractApplicationQueryForm form) {
        if (form == null) {
            form = new ContractApplicationQueryForm();
        }
        return Result.success(
                contractService.queryContractApplications(form)
        );
    }

    @PostMapping("/application/detail")
    public Result getApplicationDetail(Long id) {
        return Result.success();
    }

    @PostMapping("/application/create")
    public Result createApplication(@RequestBody ContractApplicationCreateForm form) throws IOException {
        contractService.createApplication(form);
        return Result.success();
    }

    @PostMapping("/application/statusChange")
    public Result changeApplicationStatus(Long id, Integer statusCode) {
        ContractApplicationStatus status = ContractApplicationStatus.of(statusCode);
        if (status == null) {
            return Result.fail(400, "Invalid status code");
        }
        if (!contractService.changeApplicationStatus(id, status)) {
            return Result.fail(500, "设备更新失败");
        }
        return Result.success();
    }

    @PostMapping("/attachment/upload")
    public Result upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        try {
            String[] arr = file.getOriginalFilename().split("\\.");
            String fileType = arr[arr.length - 1];
            String filePath = contractService.saveContractFile(file.getInputStream(), fileType);
            return Result.success(filePath);
        }catch (Exception e) {
            System.out.println(e);
            return Result.fail(500, e.getMessage());
        }
    }

    @GetMapping("/attachment/download")
    public void download(@RequestParam("path") String path, HttpServletResponse response) {
        File file = new File(path);
        System.out.println(file.getName());
        String fileName = file.getName();
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("content-Type", contentType);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/" + fileName.split("\\.")[1]);


        // 将文件内容写入输出流
        try (InputStream inputStream = Files.newInputStream(Paths.get(file.getPath()));
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/application/status")
    public Result getApplicationStatus() {
        return  Result.success(Lists.newArrayList(ContractApplicationStatus.values()));
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        List<ContractApplication> list = contractService.queryContractApplications(new ContractApplicationQueryForm());

        //生成Excel
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("合同审批表", "合同审批表"), ContractApplication.class, list);
            String now = LocalDateTime.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            response.reset();
            String filename = "合同审批表" + "_" + now + ".xlsx";
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
