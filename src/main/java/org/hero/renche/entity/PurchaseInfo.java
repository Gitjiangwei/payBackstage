package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 采购信息表
 *
 */
@Data
@TableName("tx_purchase_info")
public class PurchaseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private  String purchaseId;
    /**采购物品名称*/
    private String purchaseItem;
    /**采购物品型号*/
    private String itemModel;
    /**采购物品单价*/
    private String price;
    /**采购物品数量*/
    private String quantity;
    /**采购人*/
    private String purchaser;
    /**采购时间*/
    private String purchaseTime;
    /**采购来源*/
    private String whichCompany;
    /**是否签收*/
    private String isarrival;
    /**签收时间*/
    private String arrivalTime;
    /**关联附件id*/
    private String fileRelId;
    /**插入时间*/
    private String createTime;


}
