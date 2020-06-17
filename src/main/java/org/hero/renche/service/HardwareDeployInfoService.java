package org.hero.renche.service;

import org.hero.renche.entity.HardwareDeployInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 硬件部署清单表 服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface HardwareDeployInfoService extends IService<HardwareDeployInfo> {

    HardwareDeployInfo getHardwareDeployInfo(String managingPeopleId);

}
