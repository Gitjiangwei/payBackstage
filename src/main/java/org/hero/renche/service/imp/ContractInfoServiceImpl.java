package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ContractInfoVo;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.mapper.ContractInfoMapper;
import org.hero.renche.mapper.ProjectItemInfoMapper;
import org.hero.renche.service.IContractInfoService;
import org.hero.renche.service.IProjectItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractInfoServiceImpl extends ServiceImpl<ContractInfoMapper, ContractInfo> implements IContractInfoService {

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Transactional
    @Override
    public PageInfo<ContractInfoVo> qryContractInfo(ContractInfo contractInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<ContractInfoVo> contractInfoList = contractInfoMapper.qryListContractInfo(contractInfo);
        return new PageInfo<ContractInfoVo>(contractInfoList);
    }

}
