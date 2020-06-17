package org.hero.renche.service.impl;

import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ManagingPeopleInfo;
import org.hero.renche.entity.vo.ManagingPeopleInfoVo;
import org.hero.renche.mapper.ManagingPeopleInfoMapper;
import org.hero.renche.service.ManagingPeopleInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 人员管理签收单 服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@Service
public class ManagingPeopleInfoServiceImpl extends ServiceImpl<ManagingPeopleInfoMapper, ManagingPeopleInfo> implements ManagingPeopleInfoService {

    @Autowired
    private ManagingPeopleInfoMapper managingPeopleInfoMapper;

    @Override
    public PageInfo<ManagingPeopleInfoVo> qryManagingPeopleInfo(ManagingPeopleInfoVo managingPeopleInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<ManagingPeopleInfoVo> managingPeopleInfoList=managingPeopleInfoMapper.qryManagingPeopleInfo(managingPeopleInfo);
        return new  PageInfo<ManagingPeopleInfoVo>(managingPeopleInfoList);
    }


}
