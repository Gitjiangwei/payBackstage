package org.hero.renche.entity.vo;

import org.hero.renche.entity.ProjectItemInfo;

import java.io.Serializable;

public class ProjectItemVo extends ProjectItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyName;

    private String itemTypeName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }



}
