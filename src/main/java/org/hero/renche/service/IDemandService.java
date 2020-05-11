package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.Demand;

import java.util.List;

public interface IDemandService extends IService<Demand> {


    /***
     * 添加设备需求
     * @param demand
     * @return
     */
    Boolean saveDemand(Demand demand);

    /***
     * 修改设备需求
     * @param demand
     * @return
     */
    Boolean updateDemand(Demand demand);

    /**
     * 修改设备需求状态
     * @param demandId,IsSendKey
     * @return
     */
    Boolean updateIsSendKey(String demandId, String IsSendKey);

    /**
     * 修改通知工程人员领料状态
     * @param demandId
     * @param adviceStatus
     * @return
     */
    Boolean AdviceStatus(String demandId,  String status);


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
     * 查询任务设备需求
     * @param taskId
     * @return
     */
    List<Demand> queryTaskDemandList(String taskId);


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
     * @param taskId
     * @return
     */
    Boolean delDemandByTaskId(String taskId);

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

    /**
     * 任务需要设备生成设备需求
     * @param taskId
     * @return
     */
    boolean toMakeDemand(String taskId);
}
