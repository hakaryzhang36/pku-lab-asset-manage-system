package com.hakaryzhang.lab.asset.admin.mockDB.executor;

import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.entity.Device;
import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import com.hakaryzhang.lab.asset.admin.mockDB.table.ContractApplicationTable;
import com.hakaryzhang.lab.asset.admin.mockDB.table.DeviceTable;
import com.hakaryzhang.lab.asset.admin.mockDB.table.PurchaseApplicationTable;
import com.hakaryzhang.lab.asset.admin.mockDB.Table;
import com.hakaryzhang.lab.asset.admin.utils.ApplicationContextHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghaijia
 */
@Component
@DependsOn("applicationContextHolder")
public class ExecutorFactory {
    private static final Map<Class<? extends Table>, Executor> executorMap = new HashMap<>();

    private ExecutorFactory(){
        Map<String, Table> beanOfTable = ApplicationContextHolder.getApplicationContext().getBeansOfType(Table.class);
        for (Table table : beanOfTable.values()) {
            executorMap.put(table.getClass(), Executor.of(table));
        }
    }

    public static Executor<PurchaseApplication, PurchaseApplicationTable> getPurchaseApplicationTableExecutor() {
        return executorMap.getOrDefault(PurchaseApplicationTable.class, null);
    }

    public static Executor<Device, DeviceTable> getDeviceTableExecutor() {
        return executorMap.getOrDefault(DeviceTable.class, null);
    }

    public static Executor<ContractApplication, ContractApplicationTable> getContractApplicationTableExecutor() {
        return executorMap.getOrDefault(ContractApplicationTable.class, null);
    }
}
