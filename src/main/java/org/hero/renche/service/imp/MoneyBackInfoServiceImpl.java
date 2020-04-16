package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MoneyBackInfo;
import org.hero.renche.entity.vo.MoneyBackInfoVo;
import org.hero.renche.mapper.MoneyBackInfoMapper;
import org.hero.renche.service.IMoneyBackInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class MoneyBackInfoServiceImpl extends ServiceImpl<MoneyBackInfoMapper, MoneyBackInfo> implements IMoneyBackInfoService {

    @Autowired
    private MoneyBackInfoMapper moneyBackInfoMapper;

    @Override
    public PageInfo<MoneyBackInfoVo> qryBackInfoByContractId(MoneyBackInfo moneyBackInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<MoneyBackInfoVo> backList = moneyBackInfoMapper.qryListBackInfo(moneyBackInfo);
        return new PageInfo<MoneyBackInfoVo>(backList);
    }

    @Override
    public boolean updateFileIds(MoneyBackInfo moneyBackInfo) {
        boolean flag = false;
        String chectFileIds = moneyBackInfo.getFileRelId();
        String oldFileRelId = moneyBackInfoMapper.qryFileIdsById(moneyBackInfo.getBackId());
        List<String> chectFileIdList = new ArrayList<>(Arrays.asList(chectFileIds.split(",")));
        for(String items : chectFileIdList){
            oldFileRelId =  oldFileRelId.replace(items+",","");
        }

        moneyBackInfo.setFileRelId(oldFileRelId);
        int result = moneyBackInfoMapper.updateFileIds(moneyBackInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

}
