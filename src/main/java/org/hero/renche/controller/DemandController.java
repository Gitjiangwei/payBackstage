package org.hero.renche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.vo.DemandVo;
import org.hero.renche.service.IDemandService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/renche/demand")
public class DemandController {

    @Autowired
    private IDemandService demandService;

    @AutoLog("添加设备需求")
    @PostMapping(value = "/saveDemand")
    public Result<Demand>  saveDemand(@RequestBody Demand demand){
        Result<Demand> result = new Result<>();
        demand.setCreateTime(new Date());
        try {
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            demand.setCreateUser(sysUser.getId());
            boolean ok = demandService.save(demand);
            result.success("添加成功！");
            result.setResult(demand);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }


    @AutoLog("修改设备需求")
    @PostMapping(value = "/updateDemand")
    public Result<Demand>  updateDemand(@RequestBody Demand demand){
        Result<Demand> result = new Result<>();
        Demand demandEntity = demandService.getById(demand.getDemandId());
        if (demandEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = demandService.updateById(demand);
            if (ok) {
                result.success("修改成功!");
            }else {
                result.error500("修改失败！");
            }
        }
        return result;
    }


    @AutoLog("修改设备需求状态")
    @PostMapping(value = "/updateStatus")
    public Result<T> updateStatus(@RequestParam(name = "demandId") String demandId,
                                  @RequestParam(name = "status") String status){
        Result<T> result = new Result<T>();
        if(demandId==null || demandId.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = demandService.updateIsSendKey(demandId,status);
            if(resultOk){
                if(status.equals("1")){
                    result.success("发送成功");
                }else {
                    result.success("处理成功");
                }
            }else {
                result.error500("修改失败！");
            }
        }
        return result;
    }


    @AutoLog("通知工程人员领料")
    @PostMapping(value = "/advice1")
    public Result<T> adviceStatus(@RequestParam(name = "demandId") String demandId,
                                  @RequestParam(name = "status") String status,
                                  @RequestParam(name = "taskId") String taskId){
        Result<T> result = new Result<T>();
        if(demandId==null || demandId.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = demandService.AdviceStatus(demandId,status,taskId);
            if(resultOk){
                    result.success("通知成功");
            }else {
                result.error500("通知失败！");
            }
        }
        return result;
    }


    @AutoLog("修改处理状态")
    @PostMapping(value = "/updateStatus1")
    public Result<T> updateStatus1(@RequestParam(name = "demandId") String demandId,
                                  @RequestParam(name = "status") String status
                                  ){
        Result<T> result = new Result<T>();
        if(demandId==null || demandId.equals("")){
            result.error500("参数丢失！");
        }else{

            Boolean resultOk = demandService.updateDemandStatus(demandId,status);
            if(resultOk){
                result.setSuccess(true);
            }else {
                result.error500("处理失败！");
            }
        }
        return result;
    }




    @AutoLog("设备需求退回")
    @PostMapping(value = "/updateTuihui")
    public Result<T> updateDemandStatus(@RequestParam(name = "demandId") String demandId,
                                        @RequestParam(name = "reasons") String reasons){
        Result<T> result = new Result<T>();
        if(demandId==null || demandId.equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = demandService.updateDemandStatus(demandId,reasons);
            if(resultOk){
                result.success("退回成功");
            }else {
                result.error500("退回失败！");
            }
        }
        return result;
    }

    @AutoLog("查询全部设备需求")
    @GetMapping(value = "/queryDemand")
    public Result<PageInfo<DemandVo>> queryDemand(DemandVo demand, @RequestParam(name = "pageNo") Integer pageNo,
                                                @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<DemandVo>> result = new Result<>();
        PageInfo<DemandVo> demandPageInfo = demandService.queryDemand(demand,pageNo,pageSize);
        result.setResult(demandPageInfo);
        result.setSuccess(true);
        return result;
    }

    @AutoLog("查询任务需要设备")
    @GetMapping(value = "/queryDemandList")
    public Result<List<DemandVo>> queryDemandList(@RequestParam(name = "taskId",required = false) String taskId){
        Result<List<DemandVo>> result = new Result<>();
        List<DemandVo> demandPageInfo = demandService.queryTaskDemandList(taskId);
        result.setResult(demandPageInfo);
        result.setSuccess(true);
        return result;
    }

    @AutoLog("查询已处理和未处理的设备需求")
    @GetMapping(value = "/queryDemandStatus")
    public Result<PageInfo<DemandVo>> queryDemandStatus(DemandVo demand,@RequestParam(name = "pageNo") Integer pageNo,
                                                @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<DemandVo>> result = new Result<>();
        PageInfo<DemandVo> queryDemandStatus = demandService.queryDemandStatus(demand,pageNo,pageSize);
        result.setResult(queryDemandStatus);
        result.setSuccess(true);
        return result;
    }


    @AutoLog("删除设备需求")
    @PostMapping(value = "/delDemand")
    public Result<Demand> delDemand(@RequestParam(name = "demandId") String demandId){
        Result<Demand> result = new Result<>();
        Demand demand = demandService.getById(demandId);
        if (demand == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = demandService.removeById(demandId);
            if (ok) {
                result.success("删除成功!");
            }else {
                result.error500("删除失败！");
            }
        }
        return result;
    }

    @AutoLog("批量删除设备需求")
    @PostMapping(value = "/delDemands")
    public Result<Demand> delDemands(@RequestParam(name = "demandIds") String demandIds){
        Result<Demand> result = new Result<>();
        if (demandIds == null || "".equals(demandIds.trim())) {
            result.error500("参数不识别！");
        }else {
            Boolean resultOk = demandService.removeByIds(Arrays.asList(demandIds.split(",")));
            if(resultOk){
                result.success("批量删除成功");
            }else {
                result.error500("批量删除失败！");
            }
        }
        return result;
    }

    @RequestMapping(value = "/getSessionDemand", method = RequestMethod.POST)
    public Result<List<Demand>> getSessionDemand(@RequestBody JSONObject jsonObject) {
        Result<List<Demand>> result = new Result<>();
        try {
            List<Demand> demandList = new ArrayList<>();
            JSONArray demandArray = jsonObject.getJSONArray("demandList");
            if(demandArray != null){
                demandList = demandArray.toJavaList(Demand.class);
            }

            Demand demand = JSON.parseObject(jsonObject.toJSONString(), Demand.class);
            String demandId = demand.getDemandId();
            if(demandId != null && !"".equals(demandId)){
                for(int i = 0; i < demandList.size(); i++){
                    if(demandList.get(i).getDemandId().equals(demandId)){
                        demandList.set(i, demand);
                        break;
                    }
                }
                result.success("修改成功！");
            }else{
                demand.setDemandId(UUID.randomUUID().toString().replace("-",""));
                demandList.add(demand);
                result.success("添加成功！");
            }
            result.setResult(demandList);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }
}
