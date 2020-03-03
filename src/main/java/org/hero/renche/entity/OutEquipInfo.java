package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备出库表
 *
 */
@Data
@TableName("tx_out_equip")
public class OutEquipInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String outId;
    /**设备id*/
    private String equipId;
    /**出库开始时间*/
    private String beginTime;
    /**出库结束时间*/
    private String endTime;
    /**工程点id*/
    private String prjItemId;
    /**创建时间*/
    private String createTime;

}
