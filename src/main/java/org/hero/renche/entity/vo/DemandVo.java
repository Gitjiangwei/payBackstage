package org.hero.renche.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/***
 * 设备需求表
 */
@Data
@TableName("tx_demand")
public class DemandVo implements Serializable {


    /**设备需求ID*/
    @TableId(type = IdType.UUID)
    private String demandId;

    /**设备名称*/
    private String materialName;
    /**物料单位*/
    private String materialUnit;

    /**设备型号*/
    private String equipmentModel;

    /**设备数量*/
    private String needNumber;


    /**备注*/
    private String remarks;


    /*工程点ID*/
    private String prjItemId;

}
