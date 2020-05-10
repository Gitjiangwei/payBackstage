package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/***
 * 设备需求表
 */
@Data
@TableName("tx_demand")
public class Demand implements Serializable {


    /**设备需求ID*/
    @TableId(type = IdType.UUID)
    private String demandId;

    /**设备名称*/
    private String equipmentName;

    /**设备型号*/
    private String equipmentModel;

    /**设备数量*/
    private String equipmentNumber;

    /**创建时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**创建人*/
    private String createName;

/*    *//**状态  0：未发送 1：已发送 2：未处理 3：已处理*//*
    private String isSend;*/

    /**处理时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date whetherTime;

    /**备注*/
    private String remarks;

    /**退回原因*/
    private String reasons;

    /**修改时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**设备拥有方式（0租赁 1购买）*/
    private String haveWay;

    /*状态 0：未处理  1：已处理  2：已通知*/
    private String status;
    /*工程点ID*/
    private String prjItemId;
}
