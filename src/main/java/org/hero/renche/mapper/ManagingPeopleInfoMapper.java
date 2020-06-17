package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.ManagingPeopleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hero.renche.entity.vo.ManagingPeopleInfoVo;

import java.util.List;

/**
 * <p>
 * 人员管理签收单 Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface ManagingPeopleInfoMapper extends BaseMapper<ManagingPeopleInfo> {

    List<ManagingPeopleInfoVo> qryManagingPeopleInfo(@Param("ManagingPeopleInfoVo") ManagingPeopleInfoVo managingPeopleInfo);

}
