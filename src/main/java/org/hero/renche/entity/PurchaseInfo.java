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
    /**关联物料id*/
    private String materialId;
    /**任务ID*/
    private String taskId;
    /**采购物品单价*/
    private String price;
    /**采购物品数量*/
    private Integer quantity;
    /**采购人*/
    private String purchaser;
    /**采购时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseTime;
    /**采购来源*/
    private String whichCompany;
    /**是否到货*/
    private String isarrival;
    /**签收时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrivalTime;
    /**关联附件id*/
    private String fileRelId;
    /**插入时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**创建人*/
    private String createUser;
    /**总价格*/
    private String totalPrice;
    /**备注*/
    private String remark;
    /**是否入库*/
    private String isstorage;
    /**设备拥有方式（0租赁 1购买）*/
    private Integer haveWay;
    /**租赁到期日期*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String expirationDate;


}
