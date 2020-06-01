package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.vo.DemandVo;

import java.util.List;

public interface DemandMapper extends BaseMapper<Demand> {

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
    List<DemandVo> queryDemand(@Param("DemandVo") DemandVo demand);

    /***
     * 查询任务需要设备
     * @param taskId
     * @return
     */
    List<DemandVo> queryTaskDemand(@Param("taskId")  String taskId);

    /***
     * 设备需求查询(只查处理或者未处理)
     * @param demand
     * @return
     */
    List<DemandVo> queryDemandStatus(DemandVo demand);

    /***
     * 根据任务id删除设备需求
     * @param taskIds
     * @return
     */
    int delDemandByTaskId(@Param("list") List<String> taskIds);

    /***
     * 修改设备需求单处理时间
     * @param demandId
     * @return
     */
    int updateWhetherTime(@Param("demandId")String demandId);

    DemandVo getDemandByPrjItenId(String prjItemId);

}
