package org.hero.renche.entity.vo;

import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.InvociInfo;

import java.io.Serializable;

public class InvociInfoVo extends InvociInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**合同名称**/
    private String contractName;
    /**附件个数**/
    private String fileCount = "0";

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

}
