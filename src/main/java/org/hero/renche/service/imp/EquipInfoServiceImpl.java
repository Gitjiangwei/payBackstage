package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.modelData.EquipinfoModel;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.service.IEquipinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipInfoServiceImpl extends ServiceImpl<EquipInfoMapper,EquipInfo> implements IEquipinfoService {

    @Autowired
    private EquipInfoMapper equipInfoMapper;



    @Override
    public PageInfo<EquipinfoModel> qryEqipCountList(Map<String,String> map, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<EquipinfoModel> equipList = equipInfoMapper.qryEquipList(map);
        return new PageInfo<EquipinfoModel>(equipList);
    }

    @Override
    public PageInfo<EquipInfo> qryEquipListKeyDetail(EquipInfo equipInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<EquipInfo> equipInfoList = equipInfoMapper.qryEquipListKey(equipInfo);
        return new PageInfo<EquipInfo>(equipInfoList);
    }
}
