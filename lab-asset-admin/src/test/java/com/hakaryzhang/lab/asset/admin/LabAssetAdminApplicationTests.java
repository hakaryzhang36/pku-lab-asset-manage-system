package com.hakaryzhang.lab.asset.admin;

import com.hakaryzhang.lab.asset.admin.entity.DataEntity;
import com.hakaryzhang.lab.asset.admin.entity.Device;
import com.hakaryzhang.lab.asset.admin.entity.PurchaseApplication;
import com.hakaryzhang.lab.asset.admin.service.device.DeviceService;
import com.hakaryzhang.lab.asset.admin.service.purchase.PurchaseService;
import com.hakaryzhang.lab.asset.admin.service.contract.ContractService;
import com.hakaryzhang.lab.asset.admin.service.contract.dto.ContractApplicationCreateForm;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LabAssetAdminApplicationTests {

	@Autowired
	PurchaseService purchaseService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	ContractService contractService;


	@Test
	public void testContextLoads() {
		PurchaseApplication app = new PurchaseApplication();
		app.setProductName("productName");
//		purchaseService.createApplication(app.getProductName(), app.getProductType(), app.getCompany(), app.getTotalPrice(), app.getCount(), app.getRemark(), PurchaseApplicationStatus.SUBMITTED);
//		System.out.println(purchaseService.queryPurchaseApplications());
	}

	@Test
	public void testDeviceInsert() {
		Device device = new Device();
		device.setDeviceName("productName");
		device.setSn("SN001");
//		deviceService.createDevice(device.getProductName(), device.getProductType(), device.getCompany(), device.getSn(), device.getRemark(), DeviceStatus.USING);
		System.out.println(deviceService.checkSnUniqueness("sn"));
		System.out.println(deviceService.checkSnUniqueness("SN001"));
	}



	@Test
	public void testContractQuery() {

	}


}
