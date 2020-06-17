package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 运行确认（施工项目部)
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tx_affirm_running_info")
@ApiModel(value="AffirmRunningInfo对象", description="运行确认（施工项目部)")
public class AffirmRunningInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "运行确认ID")
    /**ID*/
    @TableId(type = IdType.UUID)
    private String affirmRuningId;

    @ApiModelProperty(value = "人员管理签收单ID")
    private String managingPeopleId;

    @ApiModelProperty(value = "系统运行： (1系统运转正常 ,2 正常办理制卡/退卡 ,3 接口 正常发送/接收闸机数据  ,4定时向基建管理系统上传数据 ☐)")
    private String sysRun;

    @ApiModelProperty(value = "硬件运行：( 1人员闸机运行正常 ,2 车辆道闸运行正常   , 3人员闸机刷卡（ 指纹可选） 进出正常, 4 车辆道闸车牌识别（ 刷卡可选） 进出正常 , 5 线路工程人员信息移动采集设备（ 线路专用） 刷卡进出正常  , 6 线路工程人员信息移动采集设备（ 线路专用） 与人员管理系统数据同步正常 )")
    private String hardwareRun;


}
