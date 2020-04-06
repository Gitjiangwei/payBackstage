package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.WorkOrderInfo;

import java.util.List;
import java.util.Map;

/**
 * 工单信息 Mapper 接口
 *
 */
public interface WorkOrderInfoMapper extends BaseMapper<WorkOrderInfo> {

    List<VoWorkOrderInfo> qryWorkOrderInfoList(@Param("VoWorkOrderInfo") VoWorkOrderInfo V);

    int addWorkOrderInfo(@Param("WorkOrderInfo") WorkOrderInfo workOrderInfo);
    int removeWorkOrderByIds( List<String> workIds);
    int qryWorkOrderInfoListById(@Param("workIds") List workIds);
    List<Map<String,String>> prjItemName();
    int upWorkOrderInfo(@Param("WorkOrderInfo") WorkOrderInfo workOrderInfo);
    int updateFileIds(@Param("ids") String ids, @Param("workId") String workId);
    String qryFileIdByWorkId(@Param("workId") String workId);
}
