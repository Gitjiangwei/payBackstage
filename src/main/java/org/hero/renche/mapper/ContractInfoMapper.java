package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.vo.ContractInfoVo;

import java.util.List;

/**
 * 合同信息 Mapper 接口
 *
 */
public interface ContractInfoMapper extends BaseMapper<ContractInfo> {

    List<ContractInfoVo> qryListContractInfo(@Param("ContractInfo") ContractInfo contractInfo);

    int updateFileIds(@Param("ContractInfo") ContractInfo contractInfo);

    List<ContractInfo> qryIdListByRemaindType(String type);

}
