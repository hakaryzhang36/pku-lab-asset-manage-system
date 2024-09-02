package com.hakaryzhang.lab.asset.admin.service.contract;

import com.hakaryzhang.lab.asset.admin.entity.ContractApplication;
import com.hakaryzhang.lab.asset.admin.enums.ContractApplicationStatus;
import com.hakaryzhang.lab.asset.admin.repo.ContractRepository;
import com.hakaryzhang.lab.asset.admin.service.contract.dto.ContractApplicationCreateForm;
import com.hakaryzhang.lab.asset.admin.service.contract.dto.ContractApplicationQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author zhanghaijia
 */
@Service
@Slf4j
public class ContractService {
    @Autowired
    ContractRepository contractRepo;

    public List<ContractApplication> queryContractApplications(ContractApplicationQueryForm form) {
        return contractRepo.queryApplicationBySelective(form);
    }

    public ContractApplication getApplicationDetail(Long id) {
        return contractRepo.getApplicationById(id);
    }

    public boolean createApplication(ContractApplicationCreateForm form) throws IOException {
        ContractApplication app = new ContractApplication(form);
        Long id = contractRepo.createApplication(app);
        if (id != null) {
            app.setId(id);
            return contractRepo.updateApplicationSelective(app);
        } else {
            return false;
        }
    }

    public boolean changeApplicationStatus(Long id, ContractApplicationStatus status) {
        ContractApplication app = contractRepo.getApplicationById(id);
        if (app != null) {
            app.setStatusCode(status.getCode());
            app.setStatusDesc(status.getDesc());
            return contractRepo.updateApplicationSelective(app);
        }
        return false;
    }

    public String saveContractFile(InputStream is, String fileType) throws IOException {
        if (fileType.equals("xlsx")) {
            fileType = "xls";
        }

        String filePath;


        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            // 加载 Windows 配置
            filePath = "./src/main/resources/contract/" + System.currentTimeMillis() + "." + fileType;
            while (Files.exists(Paths.get(filePath), LinkOption.NOFOLLOW_LINKS)) {
                filePath = "./src/main/resources/contract/" + System.currentTimeMillis() + "." + fileType;
            }
        } else {
            // 加载 Linux 配置
            filePath = "/home/contract/" + System.currentTimeMillis() + "." + fileType;
            while (Files.exists(Paths.get(filePath), LinkOption.NOFOLLOW_LINKS)) {
                filePath = "/home/contract/" + System.currentTimeMillis() + "." + fileType;
            }
        }

        // 创建输出流
        OutputStream os = Files.newOutputStream(Paths.get(filePath));
        // 创建缓冲区
        byte[] buffer = new byte[1024];
        int bytesRead;

        // 从输入流中读取数据，并写入到文件中
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        // 关闭输入流和输出流
        is.close();
        os.flush();
        os.close();
        return filePath;
    }

    public File getAttachmentFile(String path) {
        return new File(path);
    }
}
