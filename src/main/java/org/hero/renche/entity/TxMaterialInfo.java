package org.hero.renche.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TxMaterialInfo对象", description="")
public class TxMaterialInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "物料编号")
    private String materialNo;

    @ApiModelProperty(value = "物料名称")
    private String name;

    @ApiModelProperty(value = "物料型号")
    private String type;

    @ApiModelProperty(value = "物料单位")
    private String unit;


}
