package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.Demand;

import java.io.Serializable;

@Data
public class DemandVo extends Demand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**物料名称*/
    private String materialName;
    /**物料编号*/
    private String materialNo;
    /**物料型号*/
    private String materialType;
    /**物料单位*/
    private String materialUnit;
    /**创建人姓名*/
    private String createUserName;

    /*工程点ID*/
    private String prjItemId;
}
