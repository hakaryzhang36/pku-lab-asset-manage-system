package com.hakaryzhang.lab.asset.admin.repo;

import com.hakaryzhang.lab.asset.admin.entity.Device;
import com.hakaryzhang.lab.asset.admin.mockDB.example.DeviceExample;
import com.hakaryzhang.lab.asset.admin.mockDB.executor.Executor;
import com.hakaryzhang.lab.asset.admin.mockDB.executor.ExecutorFactory;
import com.hakaryzhang.lab.asset.admin.mockDB.table.DeviceTable;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceQueryForm;
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
public class DeviceRepository {
    Executor<Device, DeviceTable> deviceTableExecutor = ExecutorFactory.getDeviceTableExecutor();

    public List<Device> queryDeviceBySelective(DeviceQueryForm form) {
        DeviceExample example = new DeviceExample();
        if (form.getId() != null) {
            example.andIdEqualTo(form.getId());
        }
        if (form.getDeviceName() != null) {
            example.andDeviceNameLike(form.getDeviceName());
        }
        if (form.getProductType() != null) {
            example.andProductNameLike(form.getProductType());
        }
        if (form.getCompany() != null) {
            example.andCompanyLike(form.getCompany());
        }
        if (form.getSn() != null) {
            example.andSnLike(form.getSn());
        }
        if (form.getStatusCode() != null) {
            example.andStatusCodeEqualTo(form.getStatusCode());
        }
        return deviceTableExecutor.selectAll();
    }

    public Device getDeviceById(Long id) {
        return deviceTableExecutor.selectById(id);
    }

    public Long createDevice(Device device) {
        return deviceTableExecutor.insert(device);
    }

    public boolean updateDevice(Device device) {
        return deviceTableExecutor.updateByPrimaryKeySelective(device);
    }

    public Device getBySn(String sn) {
        DeviceExample example = new DeviceExample();
        example.andSnEqualTo(sn);
        return deviceTableExecutor.selectByExample(example).stream().findFirst().orElse(null);
    }
}
