package org.hero.renche.entity.vo;

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

    /**备注*/
    private String remarks;

    /*拥有方式*/
    private String haveWay;
}
