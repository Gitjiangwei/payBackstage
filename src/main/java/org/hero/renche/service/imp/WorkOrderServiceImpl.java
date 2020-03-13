package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.WorkOrderInfo;
import org.hero.renche.mapper.WorkOrderInfoMapper;
import org.hero.renche.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {


    @Autowired
    private WorkOrderInfoMapper workOrderInfoMapper;
    @Override
    public PageInfo<WorkOrderInfo> qryWorkOrderInfoList(WorkOrderInfo workOrderInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
       List<WorkOrderInfo> pageInfo=workOrderInfoMapper.qryWorkOrderInfoList(workOrderInfo);
        return new PageInfo<WorkOrderInfo>(pageInfo);
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
}
