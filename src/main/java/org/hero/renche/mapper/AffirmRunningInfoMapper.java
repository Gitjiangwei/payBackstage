package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.AffirmRunningInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 运行确认（施工项目部) Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface AffirmRunningInfoMapper extends BaseMapper<AffirmRunningInfo> {

    AffirmRunningInfo getAffirmRunningInfo( @Param("managingPeopleId") String managingPeopleId);

}
