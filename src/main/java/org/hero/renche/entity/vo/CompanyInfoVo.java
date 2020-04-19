package org.hero.renche.entity.vo;

import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.ContractInfo;

import java.io.Serializable;

public class CompanyInfoVo extends CompanyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**类型名称**/
    private String typeName;
    /**附件个数**/
    private String fileCount = "0";

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
