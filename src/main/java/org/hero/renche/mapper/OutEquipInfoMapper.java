package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.OutEquipInfo;

import java.util.List;

/**
 * 设备出库信息 Mapper 接口
 *
 */
public interface OutEquipInfoMapper extends BaseMapper<OutEquipInfo> {


    //将设备当前状态改成正在使用并增加一次使用次数
    int updateEquipListKeys(@Param("list") List<String> equipIds);

    //将设备当前状态改成空闲
    int updateEquipListKeyDelete(@Param("list") List<String> equipIds);

    //设备出库
    int insertOutEquipInfo(@Param("list") List<OutEquipInfo> outEquipInfo);

    //设备重新入库
    int delOutEquip(@Param("outId") String outId);

    OutEquipInfo qryOutEquipId(@Param("outId") String outId);
}
