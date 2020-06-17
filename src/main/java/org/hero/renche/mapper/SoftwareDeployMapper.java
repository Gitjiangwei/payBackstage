package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.SoftwareDeploy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 软件部署表 Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface SoftwareDeployMapper extends BaseMapper<SoftwareDeploy> {

    SoftwareDeploy getSoftwareDeployByManId(@Param("managingPeopleId") String managingPeopleId);

}
