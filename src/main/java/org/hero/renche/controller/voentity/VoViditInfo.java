package org.hero.renche.controller.voentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class VoViditInfo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**ID*/
    @TableId(type = IdType.UUID)
    private String visitId;
    /**客户信息id*/
    private String companyId;
    /**客户名称*/
    private String  companyName;
    /**拜访时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visitTime;
    /**拜访人*/
    private String visitor;
    /**拜访方式*/
    private String way;
    /**拜访内容*/
    private String content;
    /**拜访结果*/
    private String result;
    /**附件关联id*/
    private String fileRelId;
    private String fileNum;

}
