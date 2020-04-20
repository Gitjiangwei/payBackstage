package org.hero.renche.entity.vo;

import org.hero.renche.entity.ProjectItemInfo;

import java.io.Serializable;

public class ProjectItemVo extends ProjectItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 所属公司名称**/
    private String companyName;
    /** 工程点类型名称**/
    private String itemTypeName;
    /** 关联合同id**/
    private String contractId;
    /** 关联合同名称**/
    private String contractName;
    /**附件个数**/
    private String fileCount = "0";

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public String getContractId() { return contractId; }

    public void setContractId(String contractId) { this.contractId = contractId; }

    public String getContractName() { return contractName; }

    public void setContractName(String contractName) { this.contractName = contractName; }

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getItemTypeName() { return itemTypeName; }

    public void setItemTypeName(String itemTypeName) { this.itemTypeName = itemTypeName; }



}
