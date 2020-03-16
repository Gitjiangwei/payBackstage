package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.WorkOrderInfo;
import org.hero.renche.mapper.ProjectItemInfoMapper;
import org.hero.renche.mapper.WorkOrderInfoMapper;
import org.hero.renche.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {


    @Autowired
    private WorkOrderInfoMapper workOrderInfoMapper;
    @Autowired
    private ProjectItemInfoMapper projectItemInfoMapper;
    @Override
    public PageInfo<VoWorkOrderInfo> qryWorkOrderInfoList(VoWorkOrderInfo voWorkOrderInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
       List<VoWorkOrderInfo> pageInfo=workOrderInfoMapper.qryWorkOrderInfoList(voWorkOrderInfo);
        return new PageInfo<VoWorkOrderInfo>(pageInfo);
    }

    @Override
    public boolean addWorkOrderInfo(WorkOrderInfo workOrderInfo) {

        int i=workOrderInfoMapper.addWorkOrderInfo(workOrderInfo);
        if(i>0){
            return  true;
        }else {
            return false;
        }

    }

    @Override
    public boolean removeWorkOrderById(String id) {
        int i=workOrderInfoMapper.deleteById(id);
        if(i>0){
            return  true;
        }else {
            return false;
        }
    }

    @Override
    public boolean removeWorkOrderByIds(List<String> workIds) {

        int i=workOrderInfoMapper.removeWorkOrderByIds(workIds);
        if(i>0){
            return  true;
        }else {
            return false;
        }
    }

    @Override
    public int qryWorkOrderInfoListById(List workIds) {
        int i=workOrderInfoMapper.qryWorkOrderInfoListById(workIds);
        return 0;
    }

    @Override
    public List<Map<String, String>> prjItemName() {
        List<Map<String, String>> list= workOrderInfoMapper.prjItemName();
        return list;
    }

    @Override
    public String qryPrjItemIdByPrjItemName(String prjName) {
           String id=  projectItemInfoMapper.qryPrjItemIdByPrjItemName(prjName);
        return id;
    }

    @Override
    public int upWorkOrderInfo(WorkOrderInfo workOrderInfo) {
        int i =workOrderInfoMapper.upWorkOrderInfo(workOrderInfo);
        return i;
    }
}
