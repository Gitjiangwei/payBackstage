package org.hero.renche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.WorkOrderInfo;
import org.hero.renche.service.WorkOrderService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/renche/workOrder")
@Slf4j
@Api("工单")
public class WorkOrderInfoController {
    @Autowired
    private WorkOrderService workOrderService;

    /**
     * 获取工单信息列表
     * @param workOrderInfo
     * @param pageNo
     * @param pageSize
     * @param request
     *
     * @return
     */

    @ApiOperation( value = "获取工单信息列表" , notes = "获取工单信息列表" , produces = "application/json")
    @GetMapping("/qryWorkOrderInfo")
    public Result<PageInfo<VoWorkOrderInfo>> qryWorkOrderInfo(VoWorkOrderInfo voWorkOrderInfo ,
                                                              @RequestParam(name = "pageNo" , defaultValue = "1") Integer pageNo ,
                                                              @RequestParam(name = "pageSize" ,defaultValue = "10") Integer pageSize,
                                                              HttpServletRequest request){
        Result<PageInfo<VoWorkOrderInfo>> result=new Result<>();
       try {
           PageInfo<VoWorkOrderInfo> pageInfo=workOrderService.qryWorkOrderInfoList(voWorkOrderInfo,pageNo,pageSize);

               result.setSuccess(true);
               result.setResult(pageInfo);
       }catch (Exception e){
           e.printStackTrace();
           log.info(e.getMessage());
           result.error500("获取工单信息失败");
       }

        return  result;
    }

    /**添加工单信息
     *
     * @param workOrderInfo
     *
     * @return
     */

    @ApiOperation( value = "添加工单信息" , notes = "添加工单信息" , produces = "application/json")
    @PostMapping("/addWorkOrderInfo")
    public Result<VoWorkOrderInfo> addWorkOrderInfo(@RequestBody  VoWorkOrderInfo voWorkOrderInfo  ){
         Result<VoWorkOrderInfo>  result=new Result<>();
         try {
             if(voWorkOrderInfo==null){
                 result.setMessage("工单信息为null");
                 return result ;
             }

             String workId= UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
             voWorkOrderInfo.setWorkId(workId);
             voWorkOrderInfo.setCreateTime(new Date());
             String prjName= voWorkOrderInfo.getPrjItemName();
             WorkOrderInfo workOrderInfo=new WorkOrderInfo();
             BeanUtils.copyProperties(voWorkOrderInfo,workOrderInfo);
             workOrderInfo.setCreateTime(new Date());
             if(prjName==null ||"".equals(prjName)){
                 result.setMessage("工程点不存在");
                 return result ;
             }
             String prjItemId=workOrderService.qryPrjItemIdByPrjItemName(prjName);
             System.out.println("/////////id="+prjItemId);

             workOrderInfo.setPrjItemId(prjItemId);
             boolean addOk=workOrderService.addWorkOrderInfo(workOrderInfo);
             if(addOk==true){
                 result.setSuccess(true);
                 result.setMessage("添加成功");
                 result.setResult(voWorkOrderInfo);
             }else{
                 result.setMessage("添加工单失败");
             }
         }catch (Exception e){
             e.printStackTrace();
             log.info(e.getMessage());
             result.setMessage("添加工单失败");
         }
        return result;
    }

    /**
     * 根据id删除工单
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除工单")
    @PostMapping("/removeWorkOrder")
    public Result<String> removeWorkOrder(@RequestBody String id){
        Result<String> result=new Result<>();

       try{

           JSONObject jsonObject= JSON.parseObject(id);
           String is=jsonObject.getString("id");
           if(is==null || "".equals(is)){
               result.error500("id为空");
               return result;
           }
           boolean removeOk=workOrderService.removeWorkOrderById(is);
           if(removeOk==true){
               result.setSuccess(true);
               result.setMessage("删除成功");
           }else{
               result.setMessage("删除失败");
           }
       }catch (Exception e){
           e.printStackTrace();
           log.info(e.getMessage());
           result.setMessage("删除失败");
       }

        return result;

    }

    /**
     * 根据id批量删除工单
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id批量删除工单")
    @PostMapping("/removeWorkOrders")
    public Result<String> removeWorkOrders(@RequestBody String ids){
        Result<String> result=new Result<>();

        try{
            if(ids==null || "".equals(ids)){
                result.error500("id为空");
                return result;
            }
            JSONObject jsonObject= JSON.parseObject(ids);
            String is=jsonObject.getString("ids");
            List workIds= Arrays.asList(is.split(","));
            System.out.println("===================sssssss=========="+workIds.size());
        /*    int removenum=workOrderService.qryWorkOrderInfoListById(workIds);
            System.out.println("//////////removenum///"+removenum);
            if(removenum==0){
                result.error500("删除失败,工单id不存在");
                return result;
            }*/

            boolean removeOk=workOrderService.removeWorkOrderByIds(workIds);
            if(removeOk==true){
                result.setSuccess(true);
                result.setMessage("批量删除成功");
            }else{
                result.setMessage("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.setMessage("删除失败");
        }

        return result;

    }

    /**
     * 获取工程点名称
     * @param request
     * @return
     */
    @ApiOperation(value ="获取工程点名称" , produces = "application/json")
    @GetMapping(value = "getn")
    public Result<List<Map<String, String>>> getCompanyName(HttpServletRequest request) {

        Result<List<Map<String, String>>> result = new Result<>();

        try {
            List<Map<String, String>> companyN = workOrderService.prjItemName();
            result.setSuccess(true);
            result.setResult(companyN);
        } catch (Exception e) {
            e.printStackTrace();

            log.info(e.getMessage());
            result.error500("工程点名称失败");
        }

        return result;

    }



}
