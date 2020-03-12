package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备信息表
 *
 */
@Data
@TableName("tx_equip_info")
public class EquipInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String equipId;
    /**设备名称*/
    private String equipName;
    /**型号*/
    private String equipModel;
    /**编号*/
    private String equipNo;
    /**价格*/
    private String equipPrice;
    /**设备状态*/
    private String equipStatus;
    /**设备情况*/
    private String condition;
    /**使用次数*/
    private String useTimes;
    /**采购id*/
    private String purchaseId;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**厂家设备编号*/
    private String manufacoryNo;


}
