package org.hero.renche.entity.vo;

import org.hero.renche.entity.ProjectItemInfo;

public class ProjectItemTransformation {

    public ProjectItemInfo toPo(ProjectItemVo vo){
        ProjectItemInfo po = new ProjectItemInfo();
        po.setPrjItemId(vo.getPrjItemId());
        po.setPrjItemName(vo.getPrjItemName());
        po.setPrjItemNum(vo.getPrjItemNum());
        po.setPrjItemType(vo.getPrjItemType());
        po.setPrjName(vo.getPrjName());
        po.setPrjItemPlace(vo.getPrjItemPlace());
        po.setPersonInCharge(vo.getPersonInCharge());
        po.setPersonTel(vo.getPersonTel());
        po.setBelongCompany(vo.getBelongCompany());
        po.setProgressOfItem(vo.getProgressOfItem());
        po.setEntryTime(vo.getEntryTime());
        po.setFinishTime(vo.getFinishTime());
        po.setRequireDeployTime(vo.getRequireDeployTime());
        return  po;
    }

    public ProjectItemVo toVo(ProjectItemInfo po){
        ProjectItemVo vo = new ProjectItemVo();
        vo.setPrjItemId(po.getPrjItemId());
        vo.setPrjItemName(po.getPrjItemName());
        vo.setPrjItemNum(po.getPrjItemNum());
        vo.setPrjItemType(po.getPrjItemType());
        vo.setPrjName(po.getPrjName());
        vo.setPrjItemPlace(po.getPrjItemPlace());
        vo.setPersonInCharge(po.getPersonInCharge());
        vo.setPersonTel(po.getPersonTel());
        vo.setBelongCompany(po.getBelongCompany());
        vo.setProgressOfItem(po.getProgressOfItem());
        vo.setEntryTime(po.getEntryTime());
        vo.setFinishTime(po.getFinishTime());
        vo.setRequireDeployTime(po.getRequireDeployTime());
        return  vo;
    }

}
