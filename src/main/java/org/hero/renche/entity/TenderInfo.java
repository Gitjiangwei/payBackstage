package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 招标信息表
 *
 */
@Data
@TableName("tx_tender_info")
public class TenderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String tenderId;
    /**招标编号*/
    private String tenderNo;
    /**项目名称*/
    private String prjName;
    /**招标公司*/
    private String tenderCompany;
    /**报价*/
    private String tenderOffer;
    /**保证金*/
    private String deposit;
    /**有无退回*/
    private String isBack;
    /**创建时间*/
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd ")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    private Date createTime;

    /**招标代理机构*/
    private String agency;
    /**采购人*/
    private String purchasePerson;
    /**服务费*/
    private String serviceMoney;
    /**交保证金时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private  Date payTime;
    /**退保证金时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recedeTime ;
    /**保证金缴费方式*/
    private String payWay;
    /**应退保证金*/
    private String recedeDeposit;
    /**计划结束时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planOutTime;
    /**实际结束时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realityOutTime;
    /**备注*/
    private String remark;
    /**关联附件id*/
    private String fileRelId;

}
