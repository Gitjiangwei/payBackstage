package org.hero.renche.service;

import org.hero.renche.entity.AffirmRunningInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 运行确认（施工项目部) 服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface AffirmRunningInfoService extends IService<AffirmRunningInfo> {

    AffirmRunningInfo getAffirmRunningInfo(String managingPeopleId);

}
