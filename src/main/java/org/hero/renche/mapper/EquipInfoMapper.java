package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.modelData.EquipinfoModel;

import java.util.List;
import java.util.Map;

/**
 * 设备信息 Mapper 接口
 *
 */
public interface EquipInfoMapper extends BaseMapper<EquipInfo> {


    List<EquipinfoModel> qryEquipList(@Param("equip")Map<String,String> map);

    List<EquipInfo> qryEquipListKey(@Param("equipInfo") EquipInfo equipInfo);

    int qryEquipKeyCount(@Param("purchaseId") String purchaseId);


    int updateDetailEquipInfo(@Param("equipInfo") EquipInfo equipInfo);

    int updateEquipStatus(@Param("equipId") String equipId);
    List exportEquipInfoList(@Param("equip")Map<String,String> map);

    int updateEuipStatusweix(@Param("equipId") String equipId);

}
