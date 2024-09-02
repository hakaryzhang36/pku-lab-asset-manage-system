package com.hakaryzhang.lab.asset.admin.mockDB.example;

import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;

public class PurchaseApplicationExample extends Example<PurchaseApplication>{
    public void andIdEqualTo(Long value) {
        addEqualCondition("id", Long.class, value);
    }
    public void andProductNameEqualTo(String value) {
        addEqualCondition("productName", String.class, value);
    }
    public void andProductTypeEqualTo(String value) {
        addEqualCondition("productType", String.class, value);
    }
    public void andCompanyEqualTo(String value) {
        addEqualCondition("company", String.class, value);
    }
    public void andStatusCodeEqualTo(Integer value) {
        addEqualCondition("statusCode", Integer.class, value);
    }
    public void andProductNameLike(String value) {
        addLikeCondition("productName", value);
    }
    public void andProductTypeLike(String value) {
        addLikeCondition("productType", value);
    }
    public void andCompanyLike(String value) {
        addLikeCondition("company", value);
    }

}
