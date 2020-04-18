package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ProjectItemVo;

import java.util.List;

/**
 * 合同信息 Mapper 接口
 *
 */
public interface ProContractRelMapper {

    List<ProjectItemVo> qryProItemByContractId(@Param("contractId") String contractId);

    int addProjectItem(@Param("contractId") String contractId, @Param("prjItemId") String prjItemId);

    int delProjectItem(@Param("contractId") String contractId, @Param("prjItemId") String prjItemId);

}
