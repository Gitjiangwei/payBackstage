package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.ArrivalList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hero.renche.entity.vo.ArrivalListVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-05-29
 */
public interface ArrivalListMapper extends BaseMapper<ArrivalList> {

    /**
     * 分页查询
     * @param arrivalListVo
     * @return
     */
    List<ArrivalListVo> qryListArrivalListVo(@Param("ArrivalListVo") ArrivalListVo arrivalListVo);
    /**
     * 根据prjItemId删除设备需求
     */
    int deleteByPrjItemId(String priItrmId);

    /**
     * 根据prjItemId批量删除设备需求
     */
    int deleteByPrjItemIds(@Param("list") List<String> priItrmIdList);

}
