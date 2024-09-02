package com.hakaryzhang.lab.asset.admin.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import com.hakaryzhang.lab.asset.admin.common.Result;
import com.hakaryzhang.lab.asset.admin.entity.Device;
import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import com.hakaryzhang.lab.asset.admin.enums.DeviceStatus;
import com.hakaryzhang.lab.asset.admin.enums.PurchaseApplicationStatus;
import com.hakaryzhang.lab.asset.admin.service.device.DeviceService;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceCreateForm;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceQueryForm;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceUpdateForm;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhanghaijia
 */
@Slf4j
@RestController
@RequestMapping("/asset/admin/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @PostMapping("/list")
    public Result getDeviceList(@RequestBody DeviceQueryForm form) {
        return Result.success(
            deviceService.queryDevice(form)
        );
    }

    @PostMapping("/detail")
    public Result getDeviceDetail(Long id) {
        return Result.success();
    }

    @PostMapping("/create")
    public Result createDevice(@RequestBody DeviceCreateForm form) {
        deviceService.createDevice(form);
        return Result.success();
    }

    @PostMapping("/update")
    public Result updateDevice(@RequestBody DeviceUpdateForm form) {
        if (!deviceService.updateDevice(form)) {
            return Result.fail(500, "设备更新失败");
        }
        return Result.success();
    }

    @GetMapping("/status")
    public Result getStatus() {
        return  Result.success(Lists.newArrayList(DeviceStatus.values()));
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        List<Device> list = deviceService.queryDevice(new DeviceQueryForm());

        //生成Excel
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("设备登记表", "设备登记表"), Device.class, list);
            String now = LocalDateTime.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            response.reset();
            String filename = "设备登记表" + "_" + now + ".xlsx";
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
