package org.hero.renche.mapper;

import com.alibaba.druid.util.StringUtils;
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
    int updateDemand(Demand demand);

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
     * 设备需求查询(只查处理或者未处理)
     * @param demand
     * @return
     */
    List<Demand> queryDemandStatus(Demand demand);


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

}
