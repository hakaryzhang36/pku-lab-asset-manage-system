package com.hakaryzhang.lab.asset.admin.mockDB.table;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.entity.DataEntity;
import com.hakaryzhang.lab.asset.admin.mockDB.Table;
import com.hakaryzhang.lab.asset.admin.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanghaijia
 */
@Component
public class ContractApplicationTable extends Table<ContractApplication> {
    private String tablePath = "";

    @PostConstruct
    private void init() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            // 加载 Windows 配置
            tablePath = "./src/main/resources/mockDatabase/ContractApplicationData.xls";
        } else {
            // 加载 Linux 配置
            tablePath = "/usr/ContractApplicationData.xls";
        }
        Resource resource = new FileSystemResource(tablePath);



        try {
            if (resource.exists()) {
                InputStream inputStream = resource.getInputStream();
                data = ExcelImportUtil.importExcel(inputStream, ContractApplication.class, new ImportParams());
                nextId = 1L + data.stream().map(ContractApplication::getId).max(Long::compareTo).orElse(0L);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean write() {
        try(FileOutputStream fos = new FileOutputStream(tablePath)) {
            data.sort(Comparator.comparing(DataEntity::getId));
            Workbook workbook = ExcelUtils.buildWorkbook(
                    data.stream().map(d -> (ContractApplication)d.copy()).collect(Collectors.toList()),
                    ContractApplication.class,
                    new ExportParams());
            workbook.write(fos);
            fos.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<ContractApplication> readAll() {
        return data;
    }
}
