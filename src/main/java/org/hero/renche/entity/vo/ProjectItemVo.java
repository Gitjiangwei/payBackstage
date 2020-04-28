package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.ProjectItemInfo;

import java.io.Serializable;

@Data
public class ProjectItemVo extends ProjectItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 所属公司名称**/
    private String companyName;
    /** 工程点类型名称**/
    private String itemTypeName;
    /** 关联合同id**/
    private String contractId;
    /** 关联合同名称**/
    private String contractName;
    /**附件个数**/
    private String fileCount = "0";

}
