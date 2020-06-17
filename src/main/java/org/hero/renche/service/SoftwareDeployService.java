package org.hero.renche.service;

import org.hero.renche.entity.SoftwareDeploy;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 软件部署表 服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface SoftwareDeployService extends IService<SoftwareDeploy> {

    SoftwareDeploy getSoftwareDeployByManId(String managingPeopleId);

}
