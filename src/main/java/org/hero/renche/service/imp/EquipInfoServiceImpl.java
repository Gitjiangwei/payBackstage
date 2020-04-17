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

    @Override
    public Boolean updateDetailEquipInfo(EquipInfo equipInfo) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateDetailEquipInfo(equipInfo);
        if(resultOk>0){
            flag = true;
        }

        return flag;
    }

    @Override
    public Boolean updateEquipStatus(String equipId) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateEquipStatus(equipId);
        if (resultOk>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public List exportEquipInfoList(Map<String, String> map) {
        List list=equipInfoMapper.exportEquipInfoList(map);
        return list;
    }

    @Override
    public Boolean updateEquipStatusweix(String equipId) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateEuipStatusweix(equipId);
        if (resultOk>0){
            flag = true;
        }
        return flag;
    }

    /**
     * 设备报废
     *
     * @param equipId
     * @return
     */
    @Override
    public Boolean updateEuipStatusbaof(String equipId) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateEuipStatusbaof(equipId);
        if (resultOk>0){
            flag = true;
        }
        return flag;
    }
}
