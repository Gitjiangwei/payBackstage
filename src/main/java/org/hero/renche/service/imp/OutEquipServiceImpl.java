package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.OutEquipInfo;
import org.hero.renche.mapper.OutEquipInfoMapper;
import org.hero.renche.service.IDemandService;
import org.hero.renche.service.IOutEquipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OutEquipServiceImpl extends ServiceImpl<OutEquipInfoMapper, OutEquipInfo> implements IOutEquipService {


    @Autowired
    private OutEquipInfoMapper outEquipInfoMapper;

    @Autowired
    private IDemandService demandService;

    @Override
    public Boolean equipInfoOut(String equipIds, String prjItemId) {
        Boolean flag = false;
        List<OutEquipInfo> outEquipInfoList = new ArrayList<OutEquipInfo>();
        List<String> stringList = new ArrayList<String>(Arrays.asList(equipIds.split(",")));
        Demand demand=demandService.getDemandByPrjItenId(prjItemId);



        for (int i = 0; i<stringList.size(); i++){
            OutEquipInfo outEquipInfo = new OutEquipInfo();
            outEquipInfo.setOutId(UUID.randomUUID().toString().replace("-",""));
            outEquipInfo.setEquipId(stringList.get(i));
            outEquipInfo.setPrjItemId(prjItemId);
            outEquipInfoList.add(outEquipInfo);
        }
        int insertOutEuqip = outEquipInfoMapper.insertOutEquipInfo(outEquipInfoList);
        if(insertOutEuqip>0){
            int updateEquipStatus = outEquipInfoMapper.updateEquipListKeys(stringList);
            if(updateEquipStatus>0){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Boolean delOutEquip(String outId) {
        Boolean flag = false;
        OutEquipInfo outEquipInfo = outEquipInfoMapper.qryOutEquipId(outId);
        int result = outEquipInfoMapper.delOutEquip(outId);
        if(result>0){
            int results = outEquipInfoMapper.updateEquipListKeyDelete(Arrays.asList(outEquipInfo.getEquipId().split(",")));
            if (results>0){
                flag = true;
            }
        }
        return flag;
    }
}
