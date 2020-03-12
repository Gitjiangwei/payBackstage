package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ProjectItemVo;

public interface IProjectItemInfoService extends IService<ProjectItemInfo> {

    PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize);

}
