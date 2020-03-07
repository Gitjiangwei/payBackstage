package org.hero.renche.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.service.ICompanyInfoService;
import org.hero.renche.service.VisitService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/renche/visit")
public class VisitInfoController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private ICompanyInfoService iCompanyInfoService;


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
        PageInfo<VoViditInfo> visitInfoPageInfo=visitService.qryViditInfo(voViditInfo,pageNo,pageSize);
        String suc="true";
        result.setSuccess(true);
        result.setResult(visitInfoPageInfo);
           return result;
    }

    /**
     * 添加数据拜访记录
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
        BeanUtils.copyProperties(voViditInfo,visitInfo);
        String visitId= UUID.randomUUID().toString();
        visitId=visitId.replaceAll("-","").toUpperCase();
        visitInfo.setVisitId(visitId);
        visitInfo.setCompanyId(companyId);
        boolean bool=visitService.addViditInfo(visitInfo);
        result.setResult(visitInfo);
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

           boolean bo= visitService.upViditInfo(companyName,visitInfo);
            if(bo!=true){
                result.error500("添加失败");
            }
            result.setResult(voViditInfo);
            result.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("添加失败");
        }
        return result;

    }

    /**
     * 删除客户拜访记录
     * @param voViditInfo
     * @param request
     * @return
     */

    @ApiOperation(value ="删除客户拜访记录" , notes = "删除客户拜访数据列表" , produces = "application/json")
    @DeleteMapping(value = "/delete")
    public Result<String> deleteVisitInfo(@RequestParam(value = "id") String id,HttpServletRequest request){
        Result<String> result=new Result<>();
       try{

           if(id==null){
               result.error500("删除失败,公司id不存在");
           }
           boolean bo=visitService.deleteVisitInfoById(id);
           if(bo!=true){
               result.error500("删除失败，数据库删除不成功");
           }
           result.setSuccess(true);

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




}
