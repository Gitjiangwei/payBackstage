package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.modelData.ProjectItemModel;
import org.hero.renche.entity.vo.ProjectItemVo;

import java.util.List;

/**
 * 工程点信息 Mapper 接口
 *
 */
public interface ProjectItemInfoMapper extends BaseMapper<ProjectItemInfo> {

    List<ProjectItemVo> qryListProjectItemInfo(@Param("ProjectItemInfo") ProjectItemInfo projectItemInfo);

    String qryPrjItemIdByPrjItemName(String prjName);


    List<ProjectItemModel> qryProjectEquip(@Param("projectItemId") String projectItemId);
}
