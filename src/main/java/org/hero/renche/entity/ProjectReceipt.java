package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 工程验收单
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-19
 */
@Data
@TableName("tx_project_receipt")
@ApiModel(value="ProjectReceipt对象", description="工程验收单")
public class ProjectReceipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工程验收单ID")
    @TableId(type = IdType.UUID)
    private String projectReceiptId;

    @ApiModelProperty(value = "工程点ID")
    private String prjItemId;

    @ApiModelProperty(value = "项目类型（0：租赁 ； 1：购买）")
    private Integer projectType;

    @ApiModelProperty(value = "项目内容")
    private String projectContent;

    @ApiModelProperty(value = "完成情况")
    private String evaluate;

    @ApiModelProperty(value = "承建单位意见")
    private String contractorOpinion;

    @ApiModelProperty(value = "承建单位验收人签字")
    private String contractorPerson;

    @ApiModelProperty(value = "承建单位验收人签字日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date contractorSignatureDate;

    @ApiModelProperty(value = "建设单位意见")
    private String buildOpinion;

    @ApiModelProperty(value = "建设单位验收人签字日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date buildSignatureDate;

    @ApiModelProperty(value = "建设单位验收人签字")
    private String buildPerson;

    @ApiModelProperty(value = "租赁开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate1;

    @ApiModelProperty(value = "租赁结束时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate1;

    @ApiModelProperty(value = "附件ID")
    private String fileRelId;



}
