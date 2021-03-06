package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.WorkOrderInfo;
import org.jeecg.modules.system.entity.SysUser;

import java.util.List;
import java.util.Map;

public interface WorkOrderService {

    PageInfo<VoWorkOrderInfo> qryWorkOrderInfoList(VoWorkOrderInfo VoWorkOrderInfo, Integer pageNo, Integer pageSize);
    boolean addWorkOrderInfo(WorkOrderInfo workOrderInfo);
    boolean removeWorkOrderById(String id);
    boolean removeWorkOrderByIds(List<String> workIds);
    int qryWorkOrderInfoListById(List workIds);
    List<Map<String,String>> prjItemName();
    String qryPrjItemIdByPrjItemName(String prjName);
    int upWorkOrderInfo(WorkOrderInfo workOrderInfo);
    boolean updateFileIds( String ids,String workId);
    List exportWorkOrderInfoList(VoWorkOrderInfo voWorkOrderInfo);
    List<String> qryWorkIdListByWorkName(String workName);
    PageInfo<SysUser>  qrySysUserList(String name, Integer pageNo, Integer pageSize);
    PageInfo<ProjectItemInfo>  qryProjectItemInfoList(String name, Integer pageNo, Integer pageSize);
    int upWorkOrderInfo1(String state ,String workId );
}
