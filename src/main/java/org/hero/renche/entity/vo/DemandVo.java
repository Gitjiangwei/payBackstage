package org.hero.renche.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class DemandVo implements Serializable {


    /**设备需求ID*/
    private String demandId;

    /**设备名称*/
    private String equipmentName;

    /**设备型号*/
    private String equipmentModel;

    /**设备数量*/
    private String equipmentNumber;

    /**状态  0：未发送 1：已发送 2：未处理 3：已处理*/
    private String isSend;

    /**备注*/
    private String remarks;
}
