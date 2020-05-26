package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.MaterialInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-05-25
 */
public interface MaterialInfoMapper extends BaseMapper<MaterialInfo> {

    List<MaterialInfo> qryMaterialInfoList(@Param("MaterialInfo") MaterialInfo materialInfo);

    String getMaterialNo(String pyName);

}
