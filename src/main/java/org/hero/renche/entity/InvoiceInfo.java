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
 * 开票信息表
 *
 */
@Data
@TableName("tx_invoice_info")
public class InvoiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String invoiceId;
    /**开票时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd ")
    private Date invoiceTime;
    /**开票金额*/
    private String invoiceMoney;
    /**回款时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd ")
    private Date returnTime;
    /**回款金额*/
    private String returnMoney;
    /**备注*/
    private String remark;
    /**合同id*/
    private String contractId;
    /**附件关联id*/
    private String fileRelId;
    /**创建时间*/
    private Date createTime;


}
