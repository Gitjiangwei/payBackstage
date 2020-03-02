package org.jeecg.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备维修表
 *
 */
@Data
@TableName("tx_repair_equip")
public class RepairEquipInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String repairId;
    /**设备id*/
    private String equidId;
    /**维修开始时间*/
    private String beginTime;
    /**维修结束时间*/
    private String endTime;
    /**维修原因*/
    private String repairReason;
    /**维修价格*/
    private String repairPrice;
    /**附件关联id*/
    private String fileRelId;
    /**创建时间*/
    private String createTime;


}
