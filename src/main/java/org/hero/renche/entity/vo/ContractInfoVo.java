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
    /**回款金额**/
    private String allReturnMoney;
    /**回款占比**/
    private String returnMoneyPercent;

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
