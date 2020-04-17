package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.modelData.EquipinfoModel;

import java.util.List;
import java.util.Map;

public interface IEquipinfoService extends IService<EquipInfo> {


    /**
     * 查询库存整体
     * @param
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<EquipinfoModel> qryEqipCountList(Map<String,String> map, Integer pageNo, Integer pageSize);


    /**
     * 根据设备型号id查询详情
     * @param equipInfo
     * @return
     */
    PageInfo<EquipInfo> qryEquipListKeyDetail(EquipInfo equipInfo,Integer pageNo,Integer pageSize);

    /**
     * 根据设备型号id查询详情只查询空闲和维修状态的设备
     * @param equipInfo
     * @return
     */
    PageInfo<EquipInfo> qryEquipListKey(EquipInfo equipInfo,Integer pageNo,Integer pageSize);


    /**
     * 修改设备库存的数据
     * @param equipInfo
     * @return
     */
    Boolean updateDetailEquipInfo(EquipInfo equipInfo);


    Boolean updateEquipStatus(String equipId);

    List exportEquipInfoList(Map<String,String> map);

    /**
     * 设备进入维修
     * @param equipId
     * @return
     */
    Boolean updateEquipStatusweix(String equipId);

    /**
     * 设备报废
     * @param equipId
     * @return
     */
    Boolean updateEuipStatusbaof(String equipId);
}
