package org.hero.renche.service.impl;

import org.hero.renche.entity.UserTrainInfo;
import org.hero.renche.mapper.UserTrainInfoMapper;
import org.hero.renche.service.UserTrainInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户培训（验收各方） 服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Service
public class UserTrainInfoServiceImpl extends ServiceImpl<UserTrainInfoMapper, UserTrainInfo> implements UserTrainInfoService {

    @Autowired
    private UserTrainInfoMapper userTrainInfoMapper;

    @Override
    public UserTrainInfo getUserTrainInfo(String managingPeopleId) {
        UserTrainInfo userTrainInfo=userTrainInfoMapper.getUserTrainInfo( managingPeopleId);

        return userTrainInfo;
    }
}
