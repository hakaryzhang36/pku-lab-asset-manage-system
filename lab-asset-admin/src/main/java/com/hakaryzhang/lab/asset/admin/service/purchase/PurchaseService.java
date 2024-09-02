package com.hakaryzhang.lab.asset.admin.service.purchase;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import com.hakaryzhang.lab.asset.admin.enums.PurchaseApplicationStatus;
import com.hakaryzhang.lab.asset.admin.repo.PurchaseRepository;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationCreateForm;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationQueryForm;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhanghaijia
 */
@Service
@Slf4j
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepo;

    public List<PurchaseApplication> queryPurchaseApplications(PurchaseApplicationQueryForm form) {
        return purchaseRepo.queryApplicationsBySelective(form);
    }

    public PurchaseApplication getApplicationDetail(Long id) {
        return purchaseRepo.getApplicationById(id);
    }

    public boolean createApplication(PurchaseApplicationCreateForm form) {
        PurchaseApplication app = new PurchaseApplication(form);
        return purchaseRepo.createApplication(app) != null;
    }

    public boolean updateApplication(PurchaseApplicationUpdateForm form) {
        PurchaseApplication app = purchaseRepo.getApplicationById(form.getId());
        app.setProductName(form.getProductName());
        app.setProductType(form.getProductType());
        app.setCompany(form.getCompany());
        app.setTotalPrice(form.getTotalPrice());
        app.setCount(form.getCount());
        app.setRemark(form.getRemark());
        return purchaseRepo.updateApplication(app);
    }

    public boolean changeApplicationStatus(Long id, PurchaseApplicationStatus status) {
        PurchaseApplication app = purchaseRepo.getApplicationById(id);
        if (app != null) {
            app.setStatusCode(status.getCode());
            app.setStatusDesc(status.getDesc());
            return purchaseRepo.updateApplication(app);
        }
        return false;
    }
}
