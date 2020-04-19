package org.hero.renche.entity.vo;

import org.hero.renche.entity.ContractInfo;

import java.io.Serializable;

public class ContractInfoVo extends ContractInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**甲方公司名称**/
    private String companyNameA;
    /**乙方公司名称**/
    private String companyNameB;
    /**项目名称**/
    private String prjName;
    /**发票金额**/
    private String allInvociMoney;
    /**发票占比**/
    private String invociMoneyPercent;
    /**回款金额**/
    private String allReturnMoney;
    /**回款占比**/
    private String returnMoneyPercent;
    /**附件个数**/
    private String fileCount = "0";
    /**合同类型名称**/
    private String contractTypeName;

    public String getAllInvociMoney() {
        return allInvociMoney;
    }

    public void setAllInvociMoney(String allInvociMoney) {
        this.allInvociMoney = allInvociMoney;
    }

    public String getInvociMoneyPercent() {
        return invociMoneyPercent;
    }

    public void setInvociMoneyPercent(String invociMoneyPercent) {
        this.invociMoneyPercent = invociMoneyPercent;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public String getCompanyNameA() {
        return companyNameA;
    }

    public void setCompanyNameA(String companyNameA) {
        this.companyNameA = companyNameA;
    }

    public String getCompanyNameB() {
        return companyNameB;
    }

    public void setCompanyNameB(String companyNameB) {
        this.companyNameB = companyNameB;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public String getAllReturnMoney() {
        return allReturnMoney;
    }

    public void setAllReturnMoney(String allReturnMoney) {
        this.allReturnMoney = allReturnMoney;
    }

    public String getReturnMoneyPercent() {
        return returnMoneyPercent;
    }

    public void setReturnMoneyPercent(String returnMoneyPercent) {
        this.returnMoneyPercent = returnMoneyPercent;
    }

}
