package org.hero.renche.service.impl;

import org.hero.renche.entity.AffirmRunningInfo;
import org.hero.renche.mapper.AffirmRunningInfoMapper;
import org.hero.renche.service.AffirmRunningInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 运行确认（施工项目部) 服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Service
public class AffirmRunningInfoServiceImpl extends ServiceImpl<AffirmRunningInfoMapper, AffirmRunningInfo> implements AffirmRunningInfoService {

    @Autowired
    private AffirmRunningInfoMapper affirmRunningInfoMapper;

    @Override
    public AffirmRunningInfo getAffirmRunningInfo(String managingPeopleId) {

        AffirmRunningInfo affirmRunningInfo=affirmRunningInfoMapper.getAffirmRunningInfo(managingPeopleId);
        return affirmRunningInfo;
    }
}
