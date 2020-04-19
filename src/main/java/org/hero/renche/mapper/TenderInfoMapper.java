package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.entity.vo.ContractInfoVo;

import java.util.List;

/**
 * 招标信息 Mapper 接口
 *
 */
public interface TenderInfoMapper extends BaseMapper<TenderInfo> {

    List<TenderInfo> qryTenderList(@Param("TenderInfo") TenderInfo tenderInfo);
    int addTenser(@Param("TenderInfo") TenderInfo tenderInfo);
    int deleteBatchTenderInfo(List<String> paramIds);
    List exportTenderInfoList(@Param("TenderInfo") TenderInfo tenderInfo);
    int updateTenderInfo(@Param("TenderInfo") TenderInfo tenderInfo);

    TenderInfo qryTenderById(@Param("tenderId") String tenderId);
}
