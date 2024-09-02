package com.hakaryzhang.lab.asset.admin.mockDB.table;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hakaryzhang.lab.asset.admin.entity.DataEntity;
import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import com.hakaryzhang.lab.asset.admin.mockDB.Table;
import com.hakaryzhang.lab.asset.admin.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanghaijia
 */
@Component
public class PurchaseApplicationTable extends Table<PurchaseApplication> {
    private String tablePath = "";

    @PostConstruct
    private void init() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            // 加载 Windows 配置
            tablePath = "./src/main/resources/mockDatabase/PurchaseApplicationData.xls";
        } else {
            // 加载 Linux 配置
            tablePath = "/usr/PurchaseApplicationData.xls";
        }
        Resource resource = new FileSystemResource(tablePath);

        try {
            if (resource.exists()) {
                InputStream inputStream = resource.getInputStream();
                data = ExcelImportUtil.importExcel(inputStream, PurchaseApplication.class, new ImportParams());
                nextId = 1L + data.stream().map(PurchaseApplication::getId).max(Long::compareTo).orElse(0L);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean write() {
        try (FileOutputStream fos = new FileOutputStream(tablePath)) {
            data.sort(Comparator.comparing(PurchaseApplication::getId));
            Workbook workbook = ExcelUtils.buildWorkbook(
                    data.stream().map(PurchaseApplication::copy).collect(Collectors.toList()),
                    PurchaseApplication.class,
                    new ExportParams());
            workbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    @Override
    public List<PurchaseApplication> readAll() {
        return data;
    }
}
