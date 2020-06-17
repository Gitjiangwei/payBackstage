package org.hero.renche.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Now;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.LeaseReturn;
import org.hero.renche.entity.vo.ArrivalListVo;
import org.hero.renche.entity.vo.LeaseReturnVo;
import org.hero.renche.service.IDemandService;
import org.hero.renche.service.IProjectItemInfoService;
import org.hero.renche.service.LeaseReturnService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/renche/leaseReturn")
@Slf4j
public class LeaseReturnController {


    @Autowired
    private LeaseReturnService leaseReturnService;
    @Autowired
    private IDemandService demandService;
    @Autowired
    private IProjectItemInfoService iProjectItemInfoService;




    /**
     * 分页列表查询
     */
    @ApiOperation(value = "获取设备租赁归还交接单列表", notes = "获取设备租赁归还交接单列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<LeaseReturnVo>> list(LeaseReturnVo leaseReturnVo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<LeaseReturnVo>> result = new Result<>();
        try{
            PageInfo<LeaseReturnVo> leaseReturnVoPageInfo = leaseReturnService.qryleaseReturnVoList(leaseReturnVo,pageNo,pageSize);
            result.setResult(leaseReturnVoPageInfo);
            result.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("获取列表失败");
        }

        return result;
    }

    @AutoLog("查询设备需求")
    @GetMapping(value = "/demand")
    public Result<List<Demand>> queryDemand(@RequestParam(name = "prjItemId") String prjItemId){
        Result<List<Demand>> result = new Result<>();
        if(prjItemId==null||"".equals(prjItemId)){
            result.error500("获取列表失败，参数为空！");

        }else {
            List<Demand> demandList=demandService.queryDemandList(prjItemId);

            result.setResult(demandList);
            result.setSuccess(true);
        }

        return result;
    }


    @PostMapping(value = "/addLeaseReturn")
    public Result<String> addLeaseReturn(@RequestBody  LeaseReturnVo leaseReturnVo){
        Result<String> result =new Result<>();
        try{
            String prjItemName=leaseReturnVo.getPrjItemName();
            String prjItemId=iProjectItemInfoService.qryPrjItemIdByName(prjItemName);
            List<Demand> demandList=demandService.queryDemandList(prjItemId);
            if(demandList==null||demandList.size()==0){
                result.error500("此工程点没有需要归还的租赁设备，不能添加！");
            }
            LeaseReturn leaseReturn=new LeaseReturn();
            BeanUtils.copyProperties(leaseReturnVo,leaseReturn);
            leaseReturn.setPrjItemId(prjItemId);
            leaseReturn.setCreateTime(new Date());
            Boolean isOk= leaseReturnService.save(leaseReturn);
            if(isOk){
                result.success("添加成功！");
            }else {
                result.error500("添加失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.error500("添加失败！");
        }

        return result;
    }

    @GetMapping(value = "/check")
    public Result check(@RequestParam(name = "prjItemId") String prjItemId){
        Result result =new Result();
        try{
            int num=leaseReturnService.qryleaseReturnByPrjId(prjItemId);
            if(num>0){
                result.error500("此工程点租赁归还交接单已存在，请重新选择!");
            }else {
                List<Demand> demandList=demandService.queryDemandList(prjItemId);
                if(demandList==null||demandList.size()==0){
                    result.error500("此工程点没有需要归还的租赁设备，请重新选择！");
                }else {
                    result.setSuccess(true);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("未知错误，请联系开发人员");
        }
        return result;



    }

    @PutMapping(value = "/edit")
    public  Result<String> editLaseReturn(@RequestBody  LeaseReturnVo leaseReturnVo){

        Result<String> result =new Result<>();
    try{
        LeaseReturn lr= leaseReturnService.getById(leaseReturnVo.getLeaseReturnId());
        if(lr==null){
            result.error500("未找到实体类，修改失败！");
        }else{
            LeaseReturn leaseReturn=new LeaseReturn();
            BeanUtils.copyProperties(leaseReturnVo,leaseReturn);

            Boolean isOk=leaseReturnService.updateById(leaseReturn);
            if(isOk){
                result.success("修改成功！");
            }else {
                result.error500("修改失败！");
            }

        }
    }catch (Exception e){
        e.printStackTrace();
        log.info(e.getMessage());
    }
    return result;

    }

    @PostMapping(value = "/delete")
    public Result deleteLeaseReturn(@RequestParam(name = "id", required = true)  String id){

        Result result =new Result();
        try{
            Boolean isOk= leaseReturnService.removeById(id);
            if(isOk){
                result.success("删除成功！");
            }else {
                result.error500("删除失败！");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除失败！");
        }

        return  result;


    }
    @PostMapping(value = "/deleteBatch")
    public Result deleteBatchLeaseReturn(@RequestParam(name="ids" ,required = true) String ids){
        Result result =new Result();
        try{
            Boolean isOk= leaseReturnService.removeByIds(Arrays.asList(ids.split(",")));
            if(isOk){
                result.success("删除成功！");
            }else {
                result.error500("删除失败！");
            }

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除失败！");
        }

        return  result;
    }





}

