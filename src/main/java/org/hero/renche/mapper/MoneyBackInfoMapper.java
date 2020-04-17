package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.MoneyBackInfo;
import org.hero.renche.entity.vo.MoneyBackInfoVo;

import java.util.List;

/**
 *  开票信息 Mapper 接口
 *
 */
public interface MoneyBackInfoMapper extends BaseMapper<MoneyBackInfo> {

   List<MoneyBackInfoVo> qryListBackInfo(@Param("MoneyBackInfo") MoneyBackInfo moneyBackInfo);

    int updateFileIds(@Param("MoneyBackInfo") MoneyBackInfo moneyBackInfo);

    String qryFileIdsById(String backId);
}
