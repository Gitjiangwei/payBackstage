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
 * 硬件部署清单表
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tx_hardware_deploy_info")
@ApiModel(value="HardwareDeployInfo对象", description="硬件部署清单表")
public class HardwareDeployInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "硬件部署ID")
    /**ID*/
    @TableId(type = IdType.UUID)
    private String hardwareDeployId;

    @ApiModelProperty(value = "人员管理签收单ID")
    private String managingPeopleId;

    @ApiModelProperty(value = "制证设备（ 通用） 部署：(0，未选择1 身份证阅读器 ,  2摄像头 ,  3 证卡打印机 ,4 热敏打印机 )")
    private String madeCertificate;

    @ApiModelProperty(value = "人员闸机（ 变电） 部署：( 0，未选择1.闸机组装 2. 通电检测 3.进出调试 )")
    private String personnelGate;

    @ApiModelProperty(value = "车辆道闸（ 变电） 部署： (0未选择 ，1闸机组装 , 2 通电检测 , 3进出调试（ 车牌识别或刷卡) )")
    private String carGate;

    @ApiModelProperty(value = "线路工程人员信息移动采集设备（线路专用）部署：移动终端组装 (0,未选择， 1已选择）")
    private String moveEquipment;


}
