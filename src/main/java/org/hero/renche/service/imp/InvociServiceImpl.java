package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.vo.ContractInfoVo;
import org.hero.renche.entity.vo.InvociInfoVo;
import org.hero.renche.mapper.InvociInfoMapper;
import org.hero.renche.service.InvociService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class InvociServiceImpl extends ServiceImpl<InvociInfoMapper, InvociInfo> implements InvociService {

    @Autowired
    private InvociInfoMapper invociInfoMapper;

    @Override
    public PageInfo<InvociInfoVo> qryInvociInfo(InvociInfoVo invociInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<InvociInfoVo> invociInfos=invociInfoMapper.qryInvociInfoList(invociInfo);
        return new PageInfo<InvociInfoVo>(invociInfos);
    }

    @Override
    public boolean updateFileIds(InvociInfo invociInfo) {

        boolean flag = false;
        String checkFileIds = invociInfo.getFileRelId();
        String oldFileRelId = invociInfoMapper.qryFileIdByInvociId(invociInfo.getInvociId());
        List<String> checkFileIdList = new ArrayList<>(Arrays.asList(checkFileIds.split(",")));
        for(String checkFile : checkFileIdList){
            if(oldFileRelId.contains(checkFile)){
                oldFileRelId = oldFileRelId.replace(checkFile+",","");
            }
        }

        invociInfo.setFileRelId(oldFileRelId);
        int result = invociInfoMapper.updateFileIds(invociInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

}
