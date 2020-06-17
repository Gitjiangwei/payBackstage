package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tx_lease_return")
@ApiModel(value="LeaseReturn对象", description="")
public class LeaseReturn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备租赁归还ID")
    @TableId(type = IdType.UUID)
    private String leaseReturnId;

    @ApiModelProperty(value = "工程点ID")
    private String prjItemId;

    @ApiModelProperty(value = "接收单位")
    private String receiver;

    @ApiModelProperty(value = "接收人")
    private String recipient;

    @ApiModelProperty(value = "归还单位")
    private String returnCompany;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "接收人电话")
    private String phoneNumber;
    @ApiModelProperty(value = "归还人")
    private String returnPerson;

    @ApiModelProperty(value = "归还人电话")
    private String returnPhoNum;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
