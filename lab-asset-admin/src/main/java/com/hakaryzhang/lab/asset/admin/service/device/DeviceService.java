package com.hakaryzhang.lab.asset.admin.service.device;

import com.hakaryzhang.lab.asset.admin.entity.Device;
import com.hakaryzhang.lab.asset.admin.enums.DeviceStatus;
import com.hakaryzhang.lab.asset.admin.repo.DeviceRepository;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceCreateForm;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceQueryForm;
import com.hakaryzhang.lab.asset.admin.service.device.dto.DeviceUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanghaijia
 */
@Service
@Slf4j
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepo;

    public List<Device> queryDevice(DeviceQueryForm form) {
        return deviceRepo.queryDeviceBySelective(form);
    }

    public Device getDeviceDetail(Long id) {
        return deviceRepo.getDeviceById(id);
    }

    public boolean createDevice(DeviceCreateForm form) {
        if (checkSnUniqueness(form.getSn())) {
            return false;
        }
        DeviceStatus status = DeviceStatus.of(form.getStatusCode());
        if (status == null) {
            throw new IllegalArgumentException("Invalid device status code");
        }
        Device app = new Device(form.getDeviceName(), form.getProductType(), form.getCompany(),
                form.getSn(), status.getDesc(), form.getStatusCode(), form.getRemark());
        return deviceRepo.createDevice(app) != null;
    }

    public boolean updateDevice(DeviceUpdateForm form) {
        if (!checkSnUniqueness(form.getSn())) {
            return false;
        }
        DeviceStatus status = DeviceStatus.of(form.getStatusCode());
        if (status == null) {
            return false;
        }
        Device app = deviceRepo.getDeviceById(form.getId());
        app.setDeviceName(form.getDeviceName());
        app.setProductType(form.getProductType());
        app.setCompany(form.getCompany());
        app.setSn(form.getSn());
        app.setRemark(form.getRemark());
        app.setStatusDesc(status.getDesc());
        app.setStatusCode(status.getCode());
        return deviceRepo.updateDevice(app);
    }

    public boolean checkSnUniqueness(String sn) {
        return deviceRepo.getBySn(sn) != null;
    }

    public boolean scrapDevice(Long id) {
        Device device = deviceRepo.getDeviceById(id);
        if (device != null) {
            DeviceStatus status = DeviceStatus.of(device.getStatusCode());
            if (!DeviceStatus.SCRAPPED.equals(status)) {
                device.setStatusCode(DeviceStatus.SCRAPPED.getCode());
                device.setStatusDesc(DeviceStatus.SCRAPPED.getDesc());
                return deviceRepo.updateDevice(device);
            }
        }
        return false;
    }
}
