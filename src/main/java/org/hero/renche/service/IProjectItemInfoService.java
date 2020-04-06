package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.modelData.ProjectItemModel;
import org.hero.renche.entity.vo.ProjectItemVo;

public interface IProjectItemInfoService extends IService<ProjectItemInfo> {

    PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize);


    /**
     * 查询工程点所使用的设备
     * @param projectItemId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<ProjectItemModel> qryProjectItemEquip(String projectItemId,Integer pageNo,Integer pageSize);

}
