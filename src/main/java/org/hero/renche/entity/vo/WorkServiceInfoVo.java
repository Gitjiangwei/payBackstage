package org.hero.renche.entity.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class WorkServiceInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**ID*/
    @TableId(type = IdType.UUID)
    private String workServiceId;
    /*工单id*/
    private String workId;
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
    private String fileRelId;
    /*工单名称*/
    private String workName;
    /*公司名称*/
    private String companyName;
    /*客户电话*/
    private String phone;





}
