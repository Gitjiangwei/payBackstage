package org.hero.renche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.entity.WorkOrderInfo;
import org.hero.renche.entity.WorkServiceInfo;
import org.hero.renche.service.VisitService;
import org.hero.renche.service.WorkOrderService;
import org.hero.renche.service.WorkServiceInfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/renche/workOrder")
@Slf4j
@Api("工单")
public class WorkOrderInfoController {
    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private WorkServiceInfoService workServiceInfoService;


    /**
     * 获取工单信息列表
     * @param
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
     *
     * @return
     */

    @ApiOperation( value = "添加工单信息" , notes = "添加工单信息" , produces = "application/json")
    @PostMapping("/addWorkOrderInfo")
    public Result<VoWorkOrderInfo> addWorkOrderInfo(@RequestBody  VoWorkOrderInfo voWorkOrderInfo  ){

        SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
        String username="";
        if(sysUser!=null){
             username=sysUser.getUsername();;
        }
         Result<VoWorkOrderInfo>  result=new Result<>();
         try {
             if(voWorkOrderInfo==null){
                 result.setMessage("工单信息为null");
                 return result ;
             }
             String status=voWorkOrderInfo.getStatus();
             String workId= UUID.randomUUID().toString().replaceAll("-","").toUpperCase();

             voWorkOrderInfo.setWorkId(workId);
             voWorkOrderInfo.setCreateTime(new Date());
             String prjName= voWorkOrderInfo.getPrjItemName();
             String fileRelId=voWorkOrderInfo.getFileRelId();
             WorkOrderInfo workOrderInfo=new WorkOrderInfo();
             BeanUtils.copyProperties(voWorkOrderInfo,workOrderInfo);
             workOrderInfo.setCreateTime(new Date());
             workOrderInfo.setFileRelId(fileRelId);
             if(prjName==null ||"".equals(prjName)){
                 result.setMessage("工程点不存在");
                 return result ;
             }
             String prjItemId=workOrderService.qryPrjItemIdByPrjItemName(prjName);
             workOrderInfo.setPrjItemId(prjItemId);
             workOrderInfo.setCreatePerson(username);
             boolean addOk=workOrderService.addWorkOrderInfo(workOrderInfo);
             String visitor=voWorkOrderInfo.getChargePerson();
             WorkServiceInfo workServiceInfo=new WorkServiceInfo();
             String workServiceId= UUID.randomUUID().toString();
             workServiceId=workServiceId.replaceAll("-","").toUpperCase();
             workServiceInfo.setWorkServiceId(workServiceId);
             workServiceInfo.setWorkId(workId);
             workServiceInfo.setVisitor(visitor);
             boolean adok=   workServiceInfoService.addWorkServiceInfo(workServiceInfo);
             if(addOk==true&&adok==true){
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

    @ApiOperation(value ="修改工单信息" , produces = "application/json")
    @PutMapping(value = "up")
    public Result<VoWorkOrderInfo> upWorkOrderInfo(@RequestBody VoWorkOrderInfo voWorkOrderInfo){
        Result<VoWorkOrderInfo> result=new Result<>();
      try {
          SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
          String username="";
          if(sysUser!=null){
              username=sysUser.getUsername();;
          }
          String status=voWorkOrderInfo.getStatus();

          String prjName= voWorkOrderInfo.getPrjItemName();
          String prjItemId=workOrderService.qryPrjItemIdByPrjItemName(prjName);
          String fileRelId=voWorkOrderInfo.getFileRelId();
          voWorkOrderInfo.setPrjItemId(prjItemId);
          WorkOrderInfo workOrderInfo=new WorkOrderInfo();
          BeanUtils.copyProperties(voWorkOrderInfo,workOrderInfo);
          workOrderInfo.setPrjItemId(prjItemId);
          workOrderInfo.setFileRelId(fileRelId);
          int upNum = workOrderService.upWorkOrderInfo(workOrderInfo);
          if(upNum==0){
              result.error500("工单修改失败");
              return result;
          }
          result.setMessage("修改成功");
          result.setResult(voWorkOrderInfo);
      }catch (Exception e){
          e.printStackTrace();
          log.info(e.getMessage());
          result.error500("工单修改失败");
      }



        return result;

    }


    /**
     * 导出工单列表
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出工单列表", notes = "导出工单列表", produces = "application/json")
    @GetMapping(value = "/exportVisit" )
    public Result<PageInfo<VoViditInfo>> exportVisit(@RequestParam(value = "param") String params, HttpServletResponse response){
        Result<PageInfo<VoViditInfo>> result=new Result<>();

        try{

            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }
            VoWorkOrderInfo voWorkOrderInfo=new VoWorkOrderInfo();
            String workName=map.get("workName")==null?"":map.get("workName");
            String chargePerson=map.get("chargePerson")==null?"":map.get("chargePerson");
            String createPerson=map.get("createPerson")==null?"":map.get("createPerson");
            voWorkOrderInfo.setWorkName(workName);
            voWorkOrderInfo.setChargePerson(chargePerson);
            voWorkOrderInfo.setCreatePerson(createPerson);
            List<VoWorkOrderInfo> qryList=workOrderService.exportWorkOrderInfoList(voWorkOrderInfo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            VoWorkOrderInfo vv=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vv=qryList.get(i);
                Date date1= vv.getCreateTime();
                Date date2=vv.getCompleteTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
                String createTime = formatter.format(date1);
                String completeTime = formatter.format(date2);
                list.add(i+1);
                list.add(vv.getWorkName());
                list.add(vv.getCreatePerson());
                list.add(vv.getChargePerson());
                list.add(vv.getDescribe());
                list.add(completeTime);
                list.add(vv.getPrjItemName());
                list.add(vv.getStatus());
                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("工单预览");
            List titlesList=new ArrayList();
            titlesList.add("序号");
            titlesList.add("工单名称");
            titlesList.add("创建人");
            titlesList.add("负责人");
            titlesList.add("任务描述");
            titlesList.add("预计完成时间");
            titlesList.add("工程点");
            titlesList.add("状态");
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "工单预览.xlsx" , excelData);
            result.setMessage("导出成功");

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("导出客户列表失败");
        }
        return result;
    }




}
