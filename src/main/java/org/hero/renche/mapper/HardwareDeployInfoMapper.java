package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.HardwareDeployInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 硬件部署清单表 Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface HardwareDeployInfoMapper extends BaseMapper<HardwareDeployInfo> {

    HardwareDeployInfo getHardwareDeployInfo(@Param("managingPeopleId") String managingPeopleId);

}
