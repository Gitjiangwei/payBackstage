package org.hero.renche.controller.voentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class VoWorkOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**ID*/
    private String workId;
    /**工程点id*/
    private String prjItemId;
    /**工程点名称*/
    private String prjItemName;
    /**任务工单名称*/
    private String  workName;
    /**创建人*/
    private String createPerson;
    /**负责人*/
    private String chargePerson;
    /**任务描述*/
    private String describe;
    /**创建时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd  HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**完成时间*/
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completeTime;
    /**工单状态*/
    private String status;
    /**附件关联id*/
    private String fileRelId;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String workServiceId;
    /*公司ID*/
    private String companyId;
    /*拜访人*/
    private String visitor;
    /*拜访时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitTime;
    /*计划执行时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planExecuTime;
    /*实际执行时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realityExecuTime;
    /*计划参与人数*/
    private Integer planPersonNum;
    /*实际参与人数*/
    private Integer realityPersonNum;
    /*任务内容与计划*/
    private String content;
    /*执行情况*/
    private String result;
    /*用户评价*/
    private  String evaluate;
    /*备注（意见）*/
    private String remark;
    /*附件Id*/
    private String fileRelId1;
    /*公司名称*/
    private String companyName;
    /*客户电话*/
    private String phone;
    /*计划结束时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planOutTime;
    /*实际结束时间*/
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realityOutTime;

    /*状态*/
    private String state;

}
