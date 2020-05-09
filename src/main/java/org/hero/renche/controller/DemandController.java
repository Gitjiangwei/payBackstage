package org.hero.renche.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.vo.DemandVo;
import org.hero.renche.service.IDemandService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/renche/demand")
public class DemandController {

    @Autowired
    private IDemandService demandService;


    @AutoLog("添加设备需求")
    @PostMapping(value = "/saveDemand")
    public Result<T>  saveDemand(@RequestBody DemandVo demandVo){
        Result<T> result = new Result<T>();
        Boolean resultOk = demandService.saveDemand(demandVo);
        if(resultOk){
            if(demandVo.getIsSend().equals("1")){
                result.success("添加并发送成功");
            }else {
                result.success("添加成功");
            }
        }else {
            result.error500("添加失败！");
        }
        return result;
    }


    @AutoLog("修改设备需求")
    @PostMapping(value = "/updateDemand")
    public Result<T>  updateDemand(@RequestBody DemandVo demandVo){
        Result<T> result = new Result<T>();
        if(demandVo.getDemandId()==null || demandVo.getDemandId().equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = demandService.updateDemand(demandVo);
            if(resultOk){
                if(demandVo.getIsSend().equals("1")){
                    result.success("修改并发送成功");
                }else {
                    result.success("修改成功");
                }
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
    @PostMapping(value = "/advice")
    public Result<T> adviceStatus(@RequestParam(name = "demandId") String demandId,
                                  @RequestParam(name = "AdviceStatus") String adviceStatus,
                                  @RequestParam(name = "status") String status){
        Result<T> result = new Result<T>();
        if(demandId==null || demandId.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = demandService.AdviceStatus(demandId,adviceStatus,status);
            if(resultOk){

                if(adviceStatus.equals("1")){
                    result.success("通知成功");
                }
            }else {
                result.error500("通知失败！");
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
    public Result<PageInfo<Demand>> queryDemand(Demand demand,@RequestParam(name = "pageNo") Integer pageNo,
                                                @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<Demand>> result = new Result<>();
        PageInfo<Demand> demandPageInfo = demandService.queryDemand(demand,pageNo,pageSize);
        result.setResult(demandPageInfo);
        result.setSuccess(true);
        return result;
    }

    @AutoLog("查询已处理和未处理的设备需求")
    @GetMapping(value = "/queryDemandStatus")
    public Result<PageInfo<Demand>> queryDemandStatus(Demand demand,@RequestParam(name = "pageNo") Integer pageNo,
                                                @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<Demand>> result = new Result<>();
        PageInfo<Demand> queryDemandStatus = demandService.queryDemandStatus(demand,pageNo,pageSize);
        result.setResult(queryDemandStatus);
        result.setSuccess(true);
        return result;
    }


    @AutoLog("删除设备需求")
    @PostMapping(value = "/delDemand")
    public Result<T> delDemand(@RequestParam(name = "demandId") String demandId){
        Result<T> result = new Result<T>();
        if(demandId==null || demandId.equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = demandService.delDemand(demandId);
            if(resultOk){
                result.success("删除成功");
            }else {
                result.error500("删除失败！");
            }
        }
        return result;
    }

    @AutoLog("批量删除设备需求")
    @PostMapping(value = "/delDemands")
    public Result<T> delDemands(@RequestParam(name = "demandIds") String demandIds){
        Result<T> result = new Result<T>();
        if(demandIds==null || demandIds.equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = demandService.delDemands(demandIds);
            if(resultOk){
                result.success("批量删除成功");
            }else {
                result.error500("批量删除失败！");
            }
        }
        return result;
    }
}
