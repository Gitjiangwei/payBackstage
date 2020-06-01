package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.EquipInfo;

import java.io.Serializable;

@Data
public class EquipInfoVo extends EquipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**物料名称*/
    private String materialName;
    /**物料编号*/
    private String materialNo;
    /**物料型号*/
    private String materialType;
    /**物料单位*/
    private String materialUnit;
    /**库存数量*/
    private String count;
    /**库存使用中数量*/
    private String inuseCount;
    /**库存空闲数量*/
    private String freeCount;
    /**库存报废数量*/
    private String scripCount;
    /**库存维修数量*/
    private String maintenonceCount;

}
