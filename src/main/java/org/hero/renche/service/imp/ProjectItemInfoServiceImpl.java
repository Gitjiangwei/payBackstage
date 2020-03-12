package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.mapper.ProjectItemInfoMapper;
import org.hero.renche.service.IProjectItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectItemInfoServiceImpl extends ServiceImpl<ProjectItemInfoMapper, ProjectItemInfo> implements IProjectItemInfoService {

    @Autowired
    private ProjectItemInfoMapper projectItemInfoMapper;

    @Override
    public PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<ProjectItemVo> projectItemInfoList = null;
        try {
            projectItemInfoList = projectItemInfoMapper.qryListProjectItemInfo(projectItemInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
//        List<ProjectItemVo> projectItemInfoList = projectItemInfoMapper.qryListProjectItemInfo(projectItemInfo);
        return new PageInfo<ProjectItemVo>(projectItemInfoList);
    }

}
