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
    @JsonFormat( timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**完成时间*/
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completeTime;
    /**工单状态*/
    private String status;
    /**附件关联id*/
    private String fileRelId;
}
