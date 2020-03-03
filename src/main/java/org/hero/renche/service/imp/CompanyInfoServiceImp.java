package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.mapper.CompanyInfoMapper;
import org.hero.renche.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyInfoServiceImp extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements ICompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;
}
