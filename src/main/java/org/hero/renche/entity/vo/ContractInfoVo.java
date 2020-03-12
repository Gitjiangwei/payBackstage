package org.hero.renche.entity.vo;

import org.hero.renche.entity.ContractInfo;

import java.io.Serializable;

public class ContractInfoVo extends ContractInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyNameA;

    private String companyNameB;

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

}
