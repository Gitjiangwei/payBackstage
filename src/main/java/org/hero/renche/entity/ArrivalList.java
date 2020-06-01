package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tx_arrival_list")
@ApiModel(value="ArrivalList对象", description="")
public class ArrivalList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String arrivalId;

    @ApiModelProperty(value = "工程点名称")
    private String prjItemName;
    @ApiModelProperty(value = "工程点ID")
    private String prjItemId;

    @ApiModelProperty(value = "接收单位")
    private String receiver;

    @ApiModelProperty(value = "接收人")
    private String recipient;

    @ApiModelProperty(value = "接受日期")
    private Date receiptDate;

    @ApiModelProperty(value = "接收人手机号")
    private String phoneNumber;



}
