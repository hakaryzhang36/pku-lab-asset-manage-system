package com.hakaryzhang.lab.asset.admin.mockDB.example;

import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.entity.Device;

public class ContractApplicationExample extends Example<ContractApplication>{
    public void andIdEqualTo(Long value) {
        addEqualCondition("id", Long.class, value);
    }
    public void andNameEqualTo(String value) {
        addEqualCondition("name", String.class, value);
    }
    public void andCompanyEqualTo(String value) {
        addEqualCondition("company", String.class, value);
    }
    public void andStatusCodeEqualTo(Integer value) {
        addEqualCondition("statusCode", Integer.class, value);
    }
    public void andNameLike(String value) {
        addLikeCondition("name", value);
    }
    public void andCompanyLike(String value) {
        addLikeCondition("company", value);
    }

}
