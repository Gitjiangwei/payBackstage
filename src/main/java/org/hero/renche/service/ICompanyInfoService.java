package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.vo.CompanyInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ICompanyInfoService extends IService<CompanyInfo> {
    String qryCompanyIdByname(String CompanyName);

    PageInfo<CompanyInfoVo> qryCompanyInfo(CompanyInfo companyInfo, Integer page, Integer pageSize);

    List<String> getIds(String companyName);

    List<Map<String, String>> qryCompanyName();

    boolean updateFileIds(CompanyInfo companyInfo);

    String exportCompanyInfo(Map<String, String> map, HttpServletResponse response);

    PageInfo<CompanyInfo> qryCompanyNameList(String companyName, Integer page, Integer pageSize);
    int qryCompanynameById(String companyId);

}

