package org.hero.renche.entity.vo;

import org.hero.renche.entity.ProjectItemInfo;

import java.io.Serializable;

public class ProjectItemVo extends ProjectItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
