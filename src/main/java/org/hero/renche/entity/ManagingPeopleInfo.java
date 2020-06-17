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
 * 人员管理签收单
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tx_managing_people_info")
@ApiModel(value="ManagingPeopleInfo对象", description="人员管理签收单")
public class ManagingPeopleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "人员管理签收单ID")
    /**ID*/
    @TableId(type = IdType.UUID)
    private String managingPeopleId;

    @ApiModelProperty(value = "工程点ID")
    private String prjItemId;

    @ApiModelProperty(value = "验收日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkTime;

    @ApiModelProperty(value = "建设单位")
    private String constructionCompany;

    @ApiModelProperty(value = "监理单位")
    private String supervisorCompany;

    @ApiModelProperty(value = "施工单位")
    private String roadworkCompany;

    @ApiModelProperty(value = "实施厂商")
    private String implementCompany;

    @ApiModelProperty(value = "软件部署")
    private String softwareDeployId;

    @ApiModelProperty(value = "硬件部署")
    private String hardwareDeployId;

    @ApiModelProperty(value = "用户培训id")
    private String userTrainId;

    @ApiModelProperty(value = "运行确认")
    private String affirmRuningId;

    @ApiModelProperty(value = "用户意见")
    private String userRemark;

    @ApiModelProperty(value = "实施厂商签字")
    private String implement;

    @ApiModelProperty(value = "实施厂商签字日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date implementDate;

    @ApiModelProperty(value = "施工项目部签字")
    private String roadwork;

    @ApiModelProperty(value = "施工项目部签字日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date roadworkDate;

    @ApiModelProperty(value = "监理项目部签字")
    private String supervisor;

    @ApiModelProperty(value = "监理项目部签字日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date supervisorDate;

    @ApiModelProperty(value = "业主项目部签字")
    private String user;

    @ApiModelProperty(value = "业主项目部签字日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userDate;

    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
