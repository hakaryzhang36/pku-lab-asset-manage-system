package com.hakaryzhang.lab.asset.admin.repo;

import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.mockDB.example.ContractApplicationExample;
import com.hakaryzhang.lab.asset.admin.mockDB.executor.Executor;
import com.hakaryzhang.lab.asset.admin.mockDB.executor.ExecutorFactory;
import com.hakaryzhang.lab.asset.admin.mockDB.table.ContractApplicationTable;
import com.hakaryzhang.lab.asset.admin.service.contract.dto.ContractApplicationQueryForm;
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
public class ContractRepository {
    Executor<ContractApplication, ContractApplicationTable> contractApplicationExecutor = ExecutorFactory.getContractApplicationTableExecutor();

    public List<ContractApplication> queryAllApplications() {
        return contractApplicationExecutor.selectAll();
    }

    public ContractApplication getApplicationById(Long id) {
        return contractApplicationExecutor.selectById(id);
    }

    public Long createApplication(ContractApplication app) {
        return contractApplicationExecutor.insert(app);
    }

    public boolean updateApplicationSelective(ContractApplication app) {
        return contractApplicationExecutor.updateByPrimaryKeySelective(app);
    }

    public List<ContractApplication> queryApplicationBySelective(ContractApplicationQueryForm form) {
        ContractApplicationExample example = new ContractApplicationExample();
        if (form.getName() != null) {
            example.andNameLike(form.getName());
        }
        if (form.getCompany() != null) {
            example.andCompanyLike(form.getCompany());
        }
        if (form.getStatusCode() != null) {
            example.andStatusCodeEqualTo(form.getStatusCode());
        }
        if (form.getId() != null) {
            example.andIdEqualTo(form.getId());
        }
        return contractApplicationExecutor.selectByExample(example);
    }
}
