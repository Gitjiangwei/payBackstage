package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.UserTrainInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户培训（验收各方） Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface UserTrainInfoMapper extends BaseMapper<UserTrainInfo> {


    UserTrainInfo getUserTrainInfo(@Param("managingPeopleId") String managingPeopleId);

}
