package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.Demand;

import java.util.List;

public interface DemandMapper extends BaseMapper<Demand> {

    /**
     * 添加设备需求
     * @param demand
     * @return
     */
    int saveDemand(Demand demand);

    /***
     * 修改设备需求
     * @param demand
     * @return
     */
    int updateDemand(@Param("Demand")  Demand demand);

    /***
     * 设备需求退回
     * @param demand
     * @return
     */
    int updateDemandStatus(Demand demand);


    /***
     * 设备需求查询
     * @param demand
     * @return
     */
    List<Demand> queryDemand(@Param("Demand")  Demand demand);

    /***
     * 查询任务需要设备
     * @param taskId
     * @return
     */
    List<Demand> queryTaskDemand(@Param("taskId")  String taskId);

    /***
     * 设备需求查询(只查处理或者未处理)
     * @param demand
     * @return
     */
    List<Demand> queryDemandStatus(Demand demand);

    /***
     * 根据任务id删除设备需求
     * @param taskIds
     * @return
     */
    int delDemandByTaskId(@Param("list") List<String> taskIds);


    /***
     * 删除设备需求
     * @param demand
     * @return
     */
    int delDemand(@Param("list") List<String> demand);

    /***
     * 修改设备需求单处理时间
     * @param demandId
     * @return
     */
    int updateWhetherTime(@Param("demandId")String demandId);

    /***
     * 根据任务id将需要设备生成设备需求
     * @param taskIds
     * @return
     */
    int toMakeDemand(@Param("list") List<String> taskIds);

    Demand getDemandByPrjItenId(String prjItemId);

    /**
     * 根据prjItemId删除设备需求
     */
    int deleteByPriItemId(String prjItemId);


    /**
     * 根据prjItemId批量删除设备需求
     */
    int deleteByPriItemIds(@Param("list") List<String> priItrmIdList);

}
