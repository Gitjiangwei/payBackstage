package org.jeecg.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

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
    private String entryTime;
    /**完成时间*/
    private String finishTime;
    /**创建时间*/
    private String createTime;


}
