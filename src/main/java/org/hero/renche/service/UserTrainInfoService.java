package org.hero.renche.service;

import org.hero.renche.entity.UserTrainInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户培训（验收各方） 服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface UserTrainInfoService extends IService<UserTrainInfo> {

    UserTrainInfo getUserTrainInfo (String managingPeopleId);

}
