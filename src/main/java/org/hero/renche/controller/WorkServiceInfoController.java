package org.hero.renche.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.entity.WorkServiceInfo;
import org.hero.renche.entity.vo.WorkServiceInfoVo;
import org.hero.renche.service.ICompanyInfoService;
import org.hero.renche.service.WorkServiceInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("renche/WorkSerivice")
@Slf4j
@Api("工单")
public class WorkServiceInfoController {

    @Autowired
    private WorkServiceInfoService workServiceInfoService;
    @Autowired
    private ICompanyInfoService iCompanyInfoService;

    /**
     * 分页列表查询
     */
    @ApiOperation(value = "获取工单服务单列表", notes = "获取所有工单服务单列表", produces = "application/json")
    @GetMapping(value = "/qryWorkSerivice")
    public Result<PageInfo<WorkServiceInfoVo>> qryVisit(WorkServiceInfoVo workServiceInfoVo ,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10")Integer pageSize,
                                                  HttpServletRequest request){
        Result<PageInfo<WorkServiceInfoVo>> result=new Result<>();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String username="";
        if(sysUser!=null){
            username=sysUser.getUsername();
        }
        workServiceInfoVo.setVisitor(username);

        try{
            PageInfo<WorkServiceInfoVo> workServiceInfoPageInfo=workServiceInfoService.qryworkServiceInfo(workServiceInfoVo,pageNo,pageSize);
            result.setSuccess(true);
            result.setResult(workServiceInfoPageInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("获取列表失败");
        }
        return result;
    }




    /**
     * 编辑工单服务单
     * @param voViditInfo
     * @param request
     * @return
     */
    @ApiOperation(value ="编辑工单服务单" , notes = "编辑工单服务单列表" , produces = "application/json")
    @PutMapping (value = "/upWorkSerivice")
    public Result<WorkServiceInfoVo> upWorkSerivice(@RequestBody WorkServiceInfoVo workServiceInfoVo,HttpServletRequest request){

        Result<WorkServiceInfoVo> result=new Result<>();
        try{
            String companyName=workServiceInfoVo.getCompanyName();
            String companyId=iCompanyInfoService.qryCompanyIdByname(companyName);
            String  workServiceId=workServiceInfoVo.getWorkServiceId();
            if(companyId==null||companyId==""){
                result.error500("编辑失败,该公司不存在");
            }
            WorkServiceInfo workServiceInfo=new WorkServiceInfo();
            BeanUtils.copyProperties(workServiceInfoVo,workServiceInfo);
            workServiceInfo.setCompanyId(companyId);
            workServiceInfo.setWorkServiceId(workServiceId);

            boolean bo= workServiceInfoService.upWorkSeriviceInfo(workServiceInfo);
            if(bo!=true){
                result.error500("修改失败");
            }
            result.setResult(workServiceInfoVo);
            result.success("修改成功！");
            result.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("修改失败");
        }
        return result;

    }



}
