package com.hakaryzhang.lab.asset.admin.mockDB.example;

import com.hakaryzhang.lab.asset.admin.entity.Device;

public class DeviceExample extends Example<Device>{
    public void andIdEqualTo(Long value) {
        addEqualCondition("id", Long.class, value);
    }

    public void andSnEqualTo(String value) {
        addEqualCondition("sn", String.class, value);
    }
    public void andDeviceNameEqualTo(String value) {
        addEqualCondition("deviceName", String.class, value);
    }

    public void andProductTypeEqualTo(String value) {
        addEqualCondition("productType", String.class, value);
    }
    public void andCompanyEqualTo(String value) {
        addEqualCondition("company", String.class, value);
    }
    public void andStatusCodeEqualTo(Integer value) {
        addEqualCondition("statusCode", String.class, value);
    }
    public void andSnLike(String value) {
        addLikeCondition("sn", value);
    }
    public void andDeviceNameLike(String value) {
        addLikeCondition("deviceName", value);
    }
    public void andProductNameLike(String value) {
        addLikeCondition("productName", value);
    }
    public void andCompanyLike(String value) {
        addLikeCondition("company", value);
    }

}
