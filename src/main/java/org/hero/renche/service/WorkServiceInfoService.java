package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.WorkOrderInfo;
import org.hero.renche.entity.WorkServiceInfo;
import org.hero.renche.entity.vo.WorkServiceInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WorkServiceInfoService {

    boolean addWorkServiceInfo(WorkServiceInfo workServiceInfo);
   PageInfo<WorkServiceInfoVo> qryworkServiceInfo(WorkServiceInfoVo workServiceInfoVo, Integer pageNo,Integer pageSize);
   boolean upWorkSeriviceInfo( WorkServiceInfo workServiceInfo);
   String exportWorkServiceInfo(Map<String, String>  map, HttpServletResponse response);
   String qryWorkIdByWorkServiceId(String workServiceId);

}
