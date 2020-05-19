package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.entity.vo.TenderInfoVo;

import java.util.List;

/**
 * 招标信息 Mapper 接口
 *
 */
public interface TenderInfoMapper extends BaseMapper<TenderInfo> {

    List<TenderInfoVo> qryTenderList(@Param("TenderInfo") TenderInfo tenderInfo);

    List exportTenderInfoList(@Param("TenderInfo") TenderInfo tenderInfo);

    TenderInfo qryTenderById(@Param("tenderId") String tenderId);

    int updateFileIds(@Param("TenderInfo") TenderInfo tenderInfo);

    String qryFileIdsById(String tenderId);
}
