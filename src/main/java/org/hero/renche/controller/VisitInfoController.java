package org.hero.renche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.controller.voentity.VoVidit;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.service.ICompanyInfoService;
import org.hero.renche.service.IPurchaseService;
import org.hero.renche.service.VisitService;
import org.hero.renche.service.WorkOrderService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/renche/visit")
public class VisitInfoController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;


    @Autowired
    private WorkOrderService workOrderService;



    /**
     * 分页列表查询
     * @param voViditInfo
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @ApiOperation(value = "获取客户拜访列表", notes = "获取所有客户拜访数据列表", produces = "application/json")
    @GetMapping(value = "/qryVisit")
    public Result<PageInfo<VoViditInfo>> qryVisit(VoViditInfo voViditInfo ,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10")Integer pageSize,
                                                  HttpServletRequest request){
        Result<PageInfo<VoViditInfo>> result=new Result<>();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String username="";
        if(sysUser!=null){
            username=sysUser.getUsername();
        }
        voViditInfo.setVisitor(username);

        try{
            PageInfo<VoViditInfo> visitInfoPageInfo=visitService.qryViditInfo(voViditInfo,pageNo,pageSize);
            result.setSuccess(true);
            result.setResult(visitInfoPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("获取列表失败");
        }
           return result;
    }

    /**
     * 添加客户拜访记录
     * @param voViditInfo
     *  @param request
     * @return
     */
    @ApiOperation(value ="增加客户拜访记录" , notes = "增加客户拜访数据列表" , produces = "application/json")
    @PostMapping(value = "/add")
    public Result<VisitInfo> addVisit(@RequestBody  VoViditInfo voViditInfo, HttpServletRequest request){

        Result<VisitInfo> result =new Result<>();

    try{
        String companyName=voViditInfo.getCompanyName();
        String companyId=iCompanyInfoService.qryCompanyIdByname(voViditInfo.getCompanyName());
        if(companyId==null||companyId==""){
            result.error500("添加失败,该公司不存在");
        }
        VisitInfo visitInfo=new VisitInfo();
        String fileRelId=voViditInfo.getFileRelId();
        BeanUtils.copyProperties(voViditInfo,visitInfo);
        visitInfo.setFileRelId(fileRelId);
        String visitId= UUID.randomUUID().toString();
        visitId=visitId.replaceAll("-","").toUpperCase();
        visitInfo.setVisitId(visitId);
        visitInfo.setCompanyId(companyId);
        boolean bool=visitService.addViditInfo(visitInfo);
        result.setResult(visitInfo);
        result.success("添加成功！");
        result.setSuccess(true);
    }catch (Exception e){
        e.printStackTrace();
        log.info(e.getMessage());
        result.error500("添加失败");
    }

        return result;


    }

    /**
     * 编辑客户拜访记录
     * @param voViditInfo
     * @param request
     * @return
     */
    @ApiOperation(value ="编辑客户拜访记录" , notes = "编辑客户拜访数据列表" , produces = "application/json")
    @PutMapping (value = "/up")
    public Result<VoViditInfo> upVisit(@RequestBody VoViditInfo voViditInfo,HttpServletRequest request){

        Result<VoViditInfo> result=new Result<>();
        try{
            String companyName=voViditInfo.getCompanyName();
            String companyId=iCompanyInfoService.qryCompanyIdByname(voViditInfo.getCompanyName());
            String  visitId=voViditInfo.getVisitId();
            if(companyId==null||companyId==""){
                result.error500("编辑失败,该公司不存在");
            }

            VisitInfo visitInfo=new VisitInfo();
            BeanUtils.copyProperties(voViditInfo,visitInfo);
            visitInfo.setCompanyId(companyId);
            visitInfo.setVisitId(visitId);

           boolean bo= visitService.upViditInfo(visitInfo);
            if(bo!=true){
                result.error500("修改失败");
            }
            result.setResult(voViditInfo);
            result.success("修改成功！");
            result.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("修改失败");
        }
        return result;

    }

    /**
     * 删除客户拜访记录
     * @param id
     *
     * @return
     */

    @ApiOperation(value ="删除客户拜访记录" , notes = "删除客户拜访数据列表" , produces = "application/json")
    @PostMapping(value = "/delete")
    public Result<String> deleteVisitInfo(@RequestBody   String id){
        Result<String> result=new Result<>();
       try{
           JSONObject jsonx = JSON.parseObject(id);
           String ids = jsonx.getString("id");
           if(id==null){
               result.error500("删除失败,公司id不存在");
               return result;
           }
           int visitnum=visitService.qryVisitInfoById(ids);
           if(visitnum==0){
               result.error500("删除失败,公司id不存在");
               return result;
           }

           //使用post请求，参数是放在JSON体中，需要使用java代码取出
           boolean bo=visitService.deleteVisitInfoById(ids);
           if(bo!=true){
               result.error500("删除失败，数据库删除不成功");
           }
           result.setSuccess(true);
           result.success("删除成功！");

       }catch (Exception e){
           e.printStackTrace();
           log.info(e.getMessage());
           result.error500("删除失败");
       }

        return result;

    }



     @ApiOperation(value ="获取客户名称" , notes = "获取客户名称" , produces = "application/json")
     @GetMapping(value = "getn")
    public Result<List<Map<String, String>>> getCompanyName(HttpServletRequest request){

        Result<List<Map<String, String>>> result=new Result<>();

        try{
            List<Map<String, String>> companyN=iCompanyInfoService.qryCompanyName();
            result.setSuccess(true);
            result.setResult(companyN);
        }catch (Exception e){
            e.printStackTrace();

            log.info(e.getMessage());
            result.error500("获取客户名称失败");
        }


        return result;


    }

/**
 * 批量删除客户拜访记录
 * @param ids
 *
 * @return
 */

    @ApiOperation(value ="批量删除客户拜访记录" , notes = "批量删除客户拜访数据列表" , produces = "application/json")
    @PostMapping(value = "/deleteBatch")
    public Result<String> deleteBatchVisitInfo(@RequestParam(name = "ids")  String ids){
        Result<String> result=new Result<>();
        try{

            if(ids==null||"".equals(ids.trim())){
                result.error500("参数丢失！");
            }else{
                boolean resultOk = visitService.removeByIds(Arrays.asList(ids.split(",")));
                if(resultOk){
                    result.setSuccess(true);
                    result.success("批量删除成功！");
                }
            }

            result.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除失败");
        }

        return result;

    }

    /**
     * 导出拜访列表
     *
     * @param voViditInfo
     * @param response
     * @return
     */
    @ApiOperation(value = "导出拜访列表", notes = "导出客户拜访数据列表", produces = "application/json")
    @GetMapping(value = "/exportVisit1" )
    public Result<PageInfo<VoViditInfo>> exportVisit(VoViditInfo voViditInfo , HttpServletResponse response){
        Result<PageInfo<VoViditInfo>> result=new Result<>();

        try{
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            String username="";
            if(sysUser!=null){
                username=sysUser.getUsername();
            }
            voViditInfo.setVisitor(username);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
            List<VoViditInfo> qryList=visitService.qryViditInfolist(voViditInfo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            VoViditInfo vv=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vv=qryList.get(i);
                Date date= vv.getVisitTime();
               String dateString = formatter.format(date);
               list.add(i+1);
               list.add(vv.getCompanyName());
               list.add(vv.getWorkName());
               list.add(dateString);
               list.add(vv.getWay());
               list.add(vv.getContent());
               list.add(vv.getResult());
               lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("客户拜访记录");
            List titlesList=new ArrayList();
            titlesList.add("序号");
            titlesList.add("工单名称");
            titlesList.add("客户名称");
            titlesList.add("拜访时间");
            titlesList.add("拜访方式");
            titlesList.add("拜访内容");
            titlesList.add("拜访结果");
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "客户拜访记录.xlsx" , excelData);
            result.setMessage("导出成功");

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("导出失败");
        }
        return result;
    }




}
