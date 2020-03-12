package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.WorkOrderInfo;

import java.util.List;

/**
 * 工单信息 Mapper 接口
 *
 */
public interface WorkOrderInfoMapper extends BaseMapper<WorkOrderInfo> {

    List<WorkOrderInfo> qryWorkOrderInfoList(@Param("WorkOrderInfo") WorkOrderInfo workOrderInfo);

    int addWorkOrderInfo(@Param("WorkOrderInfo") WorkOrderInfo workOrderInfo);
}
