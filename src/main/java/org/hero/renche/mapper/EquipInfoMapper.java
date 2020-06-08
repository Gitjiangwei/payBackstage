package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.Dict;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.modelData.EquipinfoModel;
import org.hero.renche.entity.vo.EquipInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 设备信息 Mapper 接口
 *
 */
public interface EquipInfoMapper extends BaseMapper<EquipInfo> {


    List<EquipInfoVo> qryEquipList(@Param("EquipInfoVo") EquipInfoVo equipInfoVo);

    List<EquipInfoVo> qryEquipListKey(@Param("EquipInfo") EquipInfo equipInfo);

    List<EquipInfoVo> qryEquipListKeys(@Param("EquipInfo") EquipInfo equipInfo);

    int qryEquipKeyCount(@Param("materialId") String materialId);


    int updateDetailEquipInfo(@Param("equipInfo") EquipInfo equipInfo);

    int updateEquipStatus(@Param("equipId") String equipId);

    int updateEuipStatusweix(@Param("equipId") String equipId);

    int updateEuipStatusbaof(@Param("equipId") String equipId);

    /**
     * 根据拥有方式和物料ID查找空闲的设备库存
     */
     List<EquipInfo> getEquipinfo(@Param("materialId")  String materialId ,@Param("haveWay")  Integer haveWay );

}
