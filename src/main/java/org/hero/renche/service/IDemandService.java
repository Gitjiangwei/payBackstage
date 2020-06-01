package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.vo.DemandVo;

import java.util.List;

public interface IDemandService extends IService<Demand> {



    /**
     * 修改设备需求状态
     * @param demandId,IsSendKey
     * @return
     */
    Boolean updateIsSendKey(String demandId, String IsSendKey);

    /**
     * 修改通知工程人员领料状态
     * @param demandId
     * @return
     */
    Boolean AdviceStatus(String demandId,  String status , String taskId);


    /***
     * 修改设备需求状态
     * @param demandId
     * @return
     */
    Boolean updateDemandStatus(String demandId,String status);

    /**
     * 查询全部设备需求
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<DemandVo> queryDemand(DemandVo demand, Integer pageNo, Integer pageSize);

    /**
     * 查询任务设备需求
     * @param taskId
     * @return
     */
    List<DemandVo> queryTaskDemandList(String taskId);


    /**
     * 查询全部设备需求(只查询)
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<DemandVo> queryDemandStatus(DemandVo demand, Integer pageNo, Integer pageSize);

    /***
     * 删除设备需求
     * @param taskId
     * @return
     */
    Boolean delDemandByTaskId(String taskId);

    DemandVo getDemandByPrjItenId(String prjItemId);
}
