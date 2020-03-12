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
 * 工程点信息表
 *
 */
@Data
@TableName("tx_project_item")
public class ProjectItemInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String prjItemId;
    /**工程点名称*/
    private String prjItemName;
    /**工程点编号*/
    private String prjItemNum;
    /**工程地址*/
    private String prjItemPlace;
    /**工程类型*/
    private String prjItemType;
    /**项目名称*/
    private String prjName;
    /**项目所属公司*/
    private String belongCompany;
    /**负责人*/
    private String personInCharge;
    /**联系电话*/
    private String personTel;
    /**工程进度*/
    private String progressOfItem;
    /**进场时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date entryTime;
    /**完成时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd ")
    private Date finishTime;
    /**创建时间*/
    private Date createTime;


}
