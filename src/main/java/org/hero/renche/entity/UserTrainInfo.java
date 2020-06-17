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
 * 用户培训（验收各方）
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tx_user_train_info")
@ApiModel(value="UserTrainInfo对象", description="用户培训（验收各方）")
public class UserTrainInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**ID*/
    @TableId(type = IdType.UUID)
    private String userTrainId;

    @ApiModelProperty(value = "人员管理签收单ID")
    private String managingPeopleId;

    @ApiModelProperty(value = "资料移交：用户 操作手册 （0，未移交，1移交）")
    private String dataTransfer;

    @ApiModelProperty(value = "系统培训另内容：（ 1软件功能介绍 ，  2 制卡（ 办证）操作 ，  3系统升级，  4 系统安装 5数据备份 ， 6 数据恢复，  7 用户答疑 ）")
    private String trainContent;


}
