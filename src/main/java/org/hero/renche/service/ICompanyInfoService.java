package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.CompanyInfo;

import java.util.List;
import java.util.Map;

import java.util.List;

public interface ICompanyInfoService extends IService<CompanyInfo> {
    String qryCompanyIdByname(String CompanyName);

    PageInfo<CompanyInfo> qryCompanyInfo(CompanyInfo companyInfo, Integer page, Integer pageSize);

    List<String> getIds(String companyName);

    String checkNameIsExsit(String companyName);

    List<Map<String, String>> qryCompanyName();

}

