package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ContractInfoVo;
import org.hero.renche.entity.vo.ProjectItemVo;

public interface IContractInfoService extends IService<ContractInfo> {

    PageInfo<ContractInfoVo> qryContractInfo(ContractInfo contractInfo, Integer page, Integer pageSize);

    PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize, String contractId);

    PageInfo<ProjectItemVo> qryProItemByContractId(Integer page, Integer pageSize, String contractId);

    boolean addProjectItem(String contractId, String prjItemIds);

    boolean delProjectItem(String contractId, String prjItemId);

}
