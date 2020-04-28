package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.CompanyInfo;

import java.io.Serializable;

@Data
public class CompanyInfoVo extends CompanyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**类型名称**/
    private String typeName;
    /**附件个数**/
    private String fileCount = "0";
}
