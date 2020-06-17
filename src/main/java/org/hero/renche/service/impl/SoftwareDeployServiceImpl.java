package org.hero.renche.service.impl;

import org.hero.renche.entity.SoftwareDeploy;
import org.hero.renche.mapper.SoftwareDeployMapper;
import org.hero.renche.service.SoftwareDeployService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 软件部署表 服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Service
public class SoftwareDeployServiceImpl extends ServiceImpl<SoftwareDeployMapper, SoftwareDeploy> implements SoftwareDeployService {

    @Autowired
    private SoftwareDeployMapper softwareDeployMapper;

    @Override
    public SoftwareDeploy getSoftwareDeployByManId(String managingPeopleId) {
        SoftwareDeploy softwareDeploy=softwareDeployMapper.getSoftwareDeployByManId(managingPeopleId);
        return softwareDeploy;
    }
}
