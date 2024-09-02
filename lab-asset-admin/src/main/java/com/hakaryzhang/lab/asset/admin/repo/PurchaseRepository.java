package com.hakaryzhang.lab.asset.admin.repo;

import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import com.hakaryzhang.lab.asset.admin.mockDB.example.DeviceExample;
import com.hakaryzhang.lab.asset.admin.mockDB.example.PurchaseApplicationExample;
import com.hakaryzhang.lab.asset.admin.mockDB.executor.Executor;
import com.hakaryzhang.lab.asset.admin.mockDB.executor.ExecutorFactory;
import com.hakaryzhang.lab.asset.admin.mockDB.table.PurchaseApplicationTable;
import com.hakaryzhang.lab.asset.admin.service.purchase.dto.PurchaseApplicationQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhanghaijia
 */
@Repository
@DependsOn("executorFactory")
@Slf4j
public class PurchaseRepository {
    Executor<PurchaseApplication, PurchaseApplicationTable> appTableExecutor = ExecutorFactory.getPurchaseApplicationTableExecutor();

    public List<PurchaseApplication> queryApplicationsBySelective(PurchaseApplicationQueryForm form) {
        PurchaseApplicationExample example = new PurchaseApplicationExample();
        if (form.getId() != null) {
            example.andIdEqualTo(form.getId());
        }
        if (form.getProductName() != null) {
            example.andProductNameLike(form.getProductName());
        }
        if (form.getProductType() != null) {
            example.andProductTypeLike(form.getProductType());
        }
        if (form.getCompany() != null) {
            example.andCompanyLike(form.getCompany());
        }
        if (form.getStatusCode() != null) {
            example.andStatusCodeEqualTo(form.getStatusCode());
        }
        return appTableExecutor.selectByExample(example);
    }

    public PurchaseApplication getApplicationById(Long id) {
        return appTableExecutor.selectById(id);
    }

    public Long createApplication(PurchaseApplication app) {
        return appTableExecutor.insert(app);
    }

    public boolean updateApplication(PurchaseApplication app) {
        return appTableExecutor.updateByPrimaryKeySelective(app);
    }
}
