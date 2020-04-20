package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.vo.DemandVo;
import org.springframework.beans.BeanUtils;

import java.util.stream.Stream;

public interface IDemandService extends IService<Demand> {


    /***
     * 添加设备需求
     * @param demandVo
     * @return
     */
    Boolean saveDemand(DemandVo demandVo);

    /***
     * 修改设备需求
     * @param demandVo
     * @return
     */
    Boolean updateDemand(DemandVo demandVo);

    /**
     * 修改设备需求状态
     * @param demandId,IsSendKey
     * @return
     */
    Boolean updateIsSendKey(String demandId, String IsSendKey);


    /***
     * 设备需求退回
     * @param demandId
     * @param reasons
     * @return
     */
    Boolean updateDemandStatus(String demandId,String reasons);

    /**
     * 查询全部设备需求
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Demand> queryDemand(Demand demand, Integer pageNo, Integer pageSize);


    /**
     * 查询全部设备需求(只查询)
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Demand> queryDemandStatus(Demand demand, Integer pageNo, Integer pageSize);


    /***
     * 删除设备需求
     * @param demandId
     * @return
     */
    Boolean delDemand(String demandId);

    /**
     * 批量删除设备需求
     * @param demandIds
     * @return
     */
    Boolean delDemands(String demandIds);
}
