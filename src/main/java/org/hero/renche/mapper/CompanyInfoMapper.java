package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.CompanyInfo;

import java.util.List;

/**
 * 客户信息 Mapper 接口
 *
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo> {

    List<CompanyInfo> qryListCompanyInfo(@Param("CompanyInfo") CompanyInfo companyInfo);

    List<String> getIds(String companyName);

    String checkNameIsExsit(String companyNAme);

}
