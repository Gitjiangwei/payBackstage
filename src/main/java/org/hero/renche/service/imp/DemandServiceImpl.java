package org.hero.renche.service.imp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.entity.Demand;
import org.hero.renche.mapper.DemandMapper;
import org.hero.renche.service.IDemandService;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements IDemandService {

    @Autowired
    private DemandMapper demandMapper;

   // private SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    /***
     * 添加设备需求
     * @param demand
     * @return
     */
    @Override
    public Boolean saveDemand(Demand demand) {

        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Boolean isFlag = false;
        demand.setDemandId(UUID.randomUUID().toString().replace("-",""));
        if(sysUser!=null){
            demand.setCreateName(sysUser.getUsername());
        }else{
            demand.setCreateName("");
        }
        int result = demandMapper.saveDemand(demand);
        if(result>0){
            isFlag = true;
        }
        return isFlag;
    }

    /***
     * 修改设备需求
     * @param demand
     * @return
     */
    @Override
    public Boolean updateDemand(Demand demand) {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Boolean isFlag = false;
        if(demand.getDemandId()!=null && !demand.getDemandId().equals("")){
            if(sysUser!=null){
                demand.setCreateName(sysUser.getUsername());
            }else{
                demand.setCreateName("");
            }
            int result = demandMapper.updateDemand(demand);
            if(result>0){
                isFlag = true;
            }
        }
        return isFlag;
    }

    /**
     * 修改设备需求状态
     *
     * @param demandId,IsSendKey
     * @return
     */
    @Override
    public Boolean updateIsSendKey(String demandId,String IsSendKey) {
        Boolean isFlag = false;
        if(demandId!=null && !demandId.equals("")){
            Demand demand = new Demand();
          //  demand.setIsSend(IsSendKey);
            demand.setDemandId(demandId);
            int result = demandMapper.updateDemand(demand);
            if (result > 0){
                if(IsSendKey.equals("2")){
                    int resultTime = demandMapper.updateWhetherTime(demandId);
                    if(resultTime>0){
                        isFlag = true;
                    }
                }else {
                    isFlag = true;
                }
            }
        }
        return isFlag;
    }

    @Override
    public Boolean AdviceStatus(String demandId,  String status) {
        Boolean isFlag = false;
        if(demandId!=null && !demandId.equals("")){
            Demand demand = new Demand();
            demand.setStatus(status);
            demand.setDemandId(demandId);
            int result = demandMapper.updateDemand(demand);
            if (result > 0){
                    isFlag = true;
            }
        }
        return isFlag;
    }

    /***
     * 设备需求退回
     * @param demandId
     * @param reasons
     * @return
     */
    @Override
    public Boolean updateDemandStatus(String demandId, String reasons) {
        Boolean isFlag = false;
        if(demandId!=null&& !demandId.equals("")){
            Demand demand = new Demand();
            demand.setDemandId(demandId);
            demand.setReasons(reasons);
            int result = demandMapper.updateDemandStatus(demand);
            if(result > 0){
                isFlag = true;
            }
        }
        return isFlag;
    }

    /**
     * 查询全部设备需求
     *
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Demand> queryDemand(Demand demand, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        demand.setMakeDemand("1");
        List<Demand> demandList = demandMapper.queryDemand(demand);
        return new PageInfo<Demand>(demandList);
    }

    /**
     * 查询任务设备需求
     * @param taskId
     * @return
     */
    @Override
    public List<Demand> queryTaskDemandList(String taskId){
        return demandMapper.queryTaskDemand(taskId);
    }

    /**
     * 查询全部设备需求(只查询)
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Demand> queryDemandStatus(Demand demand, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Demand> demandList = demandMapper.queryDemandStatus(demand);
        return new PageInfo<Demand>(demandList);
    }

    /***
     * 删除设备需求
     * @param demandId
     * @return
     */
    @Override
    public Boolean delDemand(String demandId) {
        Boolean isFlag = false;
        if(demandId!=null && !demandId.equals("")){
            List<String> list = new ArrayList<String>();
            list.add(demandId);
            int result = demandMapper.delDemand(list);
            if(result>0){
                isFlag = true;
            }
        }
        return isFlag;
    }

    /**
     * 批量删除设备需求
     *
     * @param demandIds
     * @return
     */
    @Override
    public Boolean delDemands(String demandIds) {
        Boolean isFlag = false;
        if(demandIds != null && !demandIds.equals("")){
            List<String> list = new ArrayList<String>();
            char a = demandIds.charAt(demandIds.length()-1);
            if(a == ','){
                demandIds = demandIds.substring(0,demandIds.length()-1);
            }

            if(demandIds!=null && !demandIds.equals("")){
                if(demandIds.contains(",")){
                    list = new ArrayList<String>(Arrays.asList(demandIds.split(",")));
                }else {
                    list.add(demandIds);
                }
                int result = demandMapper.delDemand(list);
                if(result>0){
                    isFlag = true;
                }
            }
        }
        return isFlag;
    }

    /***
     * 根据任务id删除设备需求
     * @param taskId
     * @return
     */
    @Override
    public Boolean delDemandByTaskId(String taskId) {
        Boolean isFlag = false;
        if(taskId!=null && !taskId.equals("")){
            List<String> ids = new ArrayList<>();
            if(taskId.contains(",")){
                ids = new ArrayList<String>(Arrays.asList(taskId.split(",")));
            }else {
                ids.add(taskId);
            }

            int result = demandMapper.delDemandByTaskId(ids);
            if(result>0){
                isFlag = true;
            }
        }
        return isFlag;
    }

    /**
     * 根据任务id将任务需要设备生成设备需求
     * @param taskId
     * @return
     */
    @Override
    public boolean toMakeDemand(String taskId){
        Boolean isFlag = false;
        List<String> ids = new ArrayList<>();
        if(taskId.contains(",")){
            ids = new ArrayList<String>(Arrays.asList(taskId.split(",")));
        }else {
            ids.add(taskId);
        }

        int result = demandMapper.toMakeDemand(ids);
        if(result>0){
            isFlag = true;
        }
        return isFlag;
    }

}
