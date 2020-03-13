package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.WorkOrderInfo;

import java.util.List;

public interface WorkOrderService {

    PageInfo<WorkOrderInfo> qryWorkOrderInfoList(WorkOrderInfo workOrderInfo,Integer pageNo,Integer pageSize);
    boolean addWorkOrderInfo(WorkOrderInfo workOrderInfo);
    boolean removeWorkOrderById(String id);
    boolean removeWorkOrderByIds(List<String> workIds);
    int qryWorkOrderInfoListById(List workIds);
}
