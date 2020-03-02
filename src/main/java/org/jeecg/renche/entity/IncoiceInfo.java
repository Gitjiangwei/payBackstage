package org.jeecg.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 开票信息表
 *
 */
@Data
@TableName("tx_invoice_info")
public class IncoiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String invoiceId;
    /**开票时间*/
    private String invoiceTime;
    /**开票金额*/
    private String invoiceMoney;
    /**回款时间*/
    private String returnTime;
    /**回款金额*/
    private String returnMoney;
    /**合同id*/
    private String contractId;
    /**附件关联id*/
    private String fileRelId;


}
