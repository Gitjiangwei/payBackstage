package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.vo.ContractInfoVo;

public interface IContractInfoService extends IService<ContractInfo> {

    PageInfo<ContractInfoVo> qryContractInfo(ContractInfo contractInfo, Integer page, Integer pageSize);

}
