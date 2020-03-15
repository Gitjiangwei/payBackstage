package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.CompanyInfo;

import java.util.List;
import java.util.Map;

/**
 * 客户信息 Mapper 接口
 *
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {

    String qryCompanyIdByname(String CompanyName);

    List<CompanyInfo> qryListCompanyInfo(@Param("CompanyInfo") CompanyInfo companyInfo);

/*

    int upCompanyNameById(@Param(value = "companyName") String companyName, @Param(value = "companyId") String companyId);
*/

    List qryCompanyNames();
    List<Map<String, String>> qryCompanyName();

    List<String> getIds(String companyName);

    String checkNameIsExsit(String companyNAme);

}
