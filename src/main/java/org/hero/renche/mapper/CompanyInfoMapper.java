package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hero.renche.entity.CompanyInfo;

import java.util.List;

/**
 * 客户信息 Mapper 接口
 *
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {

    String qryCompanyIdByname(String CompanyName);

    List<CompanyInfo> qryListCompanyInfo(CompanyInfo companyInfo);


    int upCompanyNameById(String companyName,String companyId);

}
