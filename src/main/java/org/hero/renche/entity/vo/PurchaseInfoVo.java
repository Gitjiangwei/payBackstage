package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.PurchaseInfo;

import java.io.Serializable;

@Data
public class PurchaseInfoVo extends PurchaseInfo implements Serializable {

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
    /**采购来源名称*/
    private String companyName;
    /*关联任务ID*/
    private String taskId;
    /*关联任务名称*/
    private String taskName;
    /*关联任务ID*/
    private String prjItemId;
    /*工程点ID*/
    private String prjItemName;
    /*通知状态（0不可通知，1可通知，2已通知）*/
    private Integer adviceStatus;
}
