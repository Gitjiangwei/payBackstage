package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 合同信息表
 *
 */
@Data
@TableName("tx_contract_info")
public class ContractInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String contractId;
    /**合同名称*/
    private String contractName;
    /**甲方公司*/
    private String partyA;
    /**甲方公司合同编号*/
    private String contractNoA;
    /**乙方公司*/
    private String partyB;
    /**乙方公司合同编号*/
    private String contractNoB;
    /**合同签订状态*/
    private String contractStatus;
    /**合同金额*/
    private String contractMoney;
    /**签收时间*/
    private String signInTime;
    /**提醒周期*/
    private String remindPeriod;
    /**合同k类型*/
    private String contractType;
    /**备注*/
    private String remark;
    /**要求部署时间*/
    private String requireDeployTime;
    /**招标id*/
    private String tenderId;
    /**电子版上传附件关联id*/
    private String elecFileRel;
    /**扫描件上传附件关联id*/
    private String fileRelId;
    /**创建时间*/
    private String createTime;

}
