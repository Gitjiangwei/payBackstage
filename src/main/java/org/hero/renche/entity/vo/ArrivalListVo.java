package org.hero.renche.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hero.renche.entity.Demand;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
public class ArrivalListVo implements Serializable {

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receiptDate;

    @ApiModelProperty(value = "接收人手机号")
    private String phoneNumber;

    /*设备清单*/
    private List<Demand> demandList;

    @Override
    public String toString() {
        return "ArrivalListVo{" +
                "arrivalId='" + arrivalId + '\'' +
                ", prjItemName='" + prjItemName + '\'' +
                ", prjItemId='" + prjItemId + '\'' +
                ", receiver='" + receiver + '\'' +
                ", recipient='" + recipient + '\'' +
                ", receiptDate=" + receiptDate +
                ", phoneNumber=" + phoneNumber +
                ", demandList=" + demandList +
                '}';
    }
}
