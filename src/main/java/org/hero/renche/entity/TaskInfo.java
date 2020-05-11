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
 * 任务信息表
 *
 */
@Data
@TableName("tx_task_info")
public class TaskInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String taskId;
    /**关联工程点id*/
    private String prjItemId;
    /**工程点名称*/
    private String prjItemName;
    /**任务名称*/
    private String taskName;
    /**任务详情*/
    private String taskContent;
    /**负责人*/
    private String receiveUser;
    /**负责人*/
    private String receiveUserName;
    /**计划开始时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planStartTime;
    /**计划结束时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planEndTime;
    /**实际开始时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**实际结束时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**联系人*/
    private String contactPerson;
    /**联系电话*/
    private String contactTel;
    /**任务状态(0新建 1进行中 2已结束)*/
    private String status;
    /**关联附件id*/
    private String fileRelId;
    /**创建人*/
    private String createUser;
    /**创建人*/
    private String createUserName;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**是否生成需求设备(空为没有设备 0否 1是)*/
    private String isMakeDemand;


}
