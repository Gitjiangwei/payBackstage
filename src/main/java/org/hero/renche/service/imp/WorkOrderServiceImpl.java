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

import java.util.*;

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

    @Override
    public boolean updateFileIds(String ids, String workId) {
        String oldfileId=workOrderInfoMapper.qryFileIdByWorkId(workId);
        String[] fileIds=oldfileId.split(",");
        List<String> oldFlieRelIdList= Arrays.asList(fileIds);
        List<String> delFlieRelIdList=Arrays.asList(ids.split(","));
        String newFileRelId = "";
        LinkedList<String> result = new LinkedList<>(oldFlieRelIdList);
        HashSet<String> set = new HashSet<>(delFlieRelIdList);
        Iterator<String> itor = result.iterator();
        while(itor.hasNext()){
            if(set.contains(itor.next())){
                itor.remove();
            }
        }
        newFileRelId = Arrays.toString(result.toArray());
        if(newFileRelId != null && !newFileRelId.equals("")) {
            newFileRelId = newFileRelId.substring(1);
            newFileRelId = newFileRelId.substring(0, newFileRelId.length() - 1);
            if(!newFileRelId.equals("")) {
                char a = newFileRelId.charAt(newFileRelId.length() - 1);
                if (a == ',') {
                    newFileRelId = newFileRelId.substring(0, newFileRelId.length() - 1);
                }
            }
        }else{
            newFileRelId = "";
        }

        int i= workOrderInfoMapper.updateFileIds(newFileRelId,workId);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }
}
