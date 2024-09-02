package com.hakaryzhang.lab.asset.admin.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    /**
     * excel 导出
     *
     * @param list           数据
     * @param title          标题
     * @param sheetName      sheet名称
     * @param pojoClass      pojo类型
     * @param isCreateHeader 是否创建表头
     */
    public static Workbook buildWorkbook(List<?> list, String title, String sheetName, Class<?> pojoClass, boolean isCreateHeader) throws IOException {
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        exportParams.setCreateHeadRows(isCreateHeader);
        return defaultBuild(list, pojoClass, exportParams);
    }

    /**
     * excel 导出
     *
     * @param list      数据
     * @param title     标题
     * @param sheetName sheet名称
     * @param pojoClass pojo类型
     */
    public static Workbook buildWorkbook(List<?> list, String title, String sheetName, Class<?> pojoClass) throws IOException {
        return defaultBuild(list, pojoClass, new ExportParams(title, sheetName, ExcelType.XSSF));
    }

    /**
     * excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param exportParams 导出参数
     */
    public static Workbook buildWorkbook(List<?> list, Class<?> pojoClass, ExportParams exportParams) throws IOException {
        return defaultBuild(list, pojoClass, exportParams);
    }

    /**
     * excel 导出
     *
     * @param list 数据
     */
    public static Workbook buildWorkbook(List<Map<String, Object>> list) throws IOException {
        return defaultBuild(list);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list         数据
     * @param pojoClass    pojo类型
     * @param exportParams 导出参数
     */
    private static Workbook defaultBuild(List<?> list, Class<?> pojoClass, ExportParams exportParams) throws IOException {
        list = list == null ? Lists.newArrayList() : list;
        return ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
    }

    /**
     * 默认的 excel 导出
     *
     * @param list 数据
     */
    private static Workbook defaultBuild(List<Map<String, Object>> list) throws IOException {
        return ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
    }

    /**
     * 下载
     *
     * @param fileName 文件名称
     * @param response
     * @param workbook excel数据
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            workbook.close();
        }
    }


    /**
     * Excel 类型枚举
     */
    enum ExcelTypeEnum {
        XLS("xls"), XLSX("xlsx");
        private String value;

        ExcelTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /*public void setValue(String value) {
            this.value = value;
        }
         */
    }
}
