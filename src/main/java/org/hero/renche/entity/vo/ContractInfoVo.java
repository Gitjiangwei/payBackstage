package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.ContractInfo;

import java.io.Serializable;

@Data
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

}
