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
 * 软件部署表
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tx_software_deploy")
@ApiModel(value="SoftwareDeploy对象", description="软件部署表")
public class SoftwareDeploy implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String softwareDeployId;

    @ApiModelProperty(value = "人员管理签收单ID")
    private String managingPeopleId;

    @ApiModelProperty(value ="\"软件部署项目名称 （0未选择，1一键安装部署正常 ， 2 硬件驱动已安装 ， 3开机自 启已实现 ，  4 服务检测正常 ， 5在线注册已完成 ， 6 权限已分配   ，7基本信息已配置 ， 8\n" +
            "线路工程人员信息移动采集设备（ 线路专用） APP 已部署 ）\"" )
    private String softwareDeployName;


}
