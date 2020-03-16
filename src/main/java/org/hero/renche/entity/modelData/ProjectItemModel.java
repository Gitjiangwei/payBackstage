package org.hero.renche.entity.modelData;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectItemModel implements Serializable {

    //出库Id
    private String outId;

    //设备名称
    private String equipName;

    //设备型号
    private String equipModel;

    //设备库存编号
    private String equipNo;

    //设备使用次数
    private String useTimes;

}
