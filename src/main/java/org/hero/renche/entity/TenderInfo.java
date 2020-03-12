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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
