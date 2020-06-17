package org.hero.renche.service.impl;

import org.hero.renche.entity.HardwareDeployInfo;
import org.hero.renche.mapper.HardwareDeployInfoMapper;
import org.hero.renche.service.HardwareDeployInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 硬件部署清单表 服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Service
public class HardwareDeployInfoServiceImpl extends ServiceImpl<HardwareDeployInfoMapper, HardwareDeployInfo> implements HardwareDeployInfoService {

    @Autowired
    private HardwareDeployInfoMapper hardwareDeployInfoMapper;
    @Override
    public HardwareDeployInfo getHardwareDeployInfo(String managingPeopleId) {

        HardwareDeployInfo hardwareDeployInfo=hardwareDeployInfoMapper.getHardwareDeployInfo(managingPeopleId);
        return hardwareDeployInfo;
    }
}
