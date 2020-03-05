package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.mapper.CompanyInfoMapper;
import org.hero.renche.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyInfoServiceImp extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements ICompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Transactional
    @Override
    public PageInfo<CompanyInfo> qryCompanyInfo(CompanyInfo companyInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<CompanyInfo> companyInfoList = companyInfoMapper.qryListCompanyInfo(companyInfo);
        return new PageInfo<CompanyInfo>(companyInfoList);
    }

}
