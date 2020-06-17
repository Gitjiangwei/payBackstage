package org.hero.renche.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ManagingPeopleInfo;
import org.hero.renche.entity.SoftwareDeploy;
import org.hero.renche.service.ManagingPeopleInfoService;
import org.hero.renche.service.SoftwareDeployService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 软件部署表 前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/renche/softwareDeploy")
@Slf4j
public class SoftwareDeployController {
    @Autowired
    private SoftwareDeployService softwareDeployService;
    @Autowired
    private ManagingPeopleInfoService managingPeopleInfoService;

    /**
     * 分页列表查询
     * @param company
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取软件部署表", notes = "获取软件部署列表", produces = "application/json")
    @GetMapping(value = "/softwareDeployList")
    public Result<SoftwareDeploy> softwareDeployList(@RequestParam(name = "managingPeopleId" ) String managingPeopleId) {
        Result<SoftwareDeploy> result = new Result<>();

        try{
            SoftwareDeploy softwareDeploy=softwareDeployService.getSoftwareDeployByManId(managingPeopleId);
           /* if(softwareDeploy!=null && !"".equals(softwareDeploy)){
                result.setSuccess(true);
                result.setResult(softwareDeploy);
            }*/
            result.setSuccess(true);
            result.setResult(softwareDeploy);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("查询软件部署表失败！");
        }

        return result;
    }


    /**
     * 添加
     * @param
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加软件部署表信息")
    public Result<SoftwareDeploy> add(@RequestBody JSONObject params) {
        Result<SoftwareDeploy> result = new Result<SoftwareDeploy>();
        try {
            String managingPeopleId=params.getString("managingPeopleId");
            String checkedList=params.getString("checkedList");
            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，添加失败！");
                return result;
            }
            SoftwareDeploy softwareDeploy1=softwareDeployService.getSoftwareDeployByManId(managingPeopleId);
            if(softwareDeploy1!=null){
                result.error500("软件部署表信息已存在，添加失败！");
                return result;
            }

            SoftwareDeploy softwareDeploy=new SoftwareDeploy();
            softwareDeploy.setManagingPeopleId(managingPeopleId);
            softwareDeploy.setSoftwareDeployName(checkedList);
            boolean ok = softwareDeployService.save(softwareDeploy);
            if(ok){
                result.success("添加成功！");
                result.setResult(softwareDeploy);
            }else {
                result.error500("操作失败");
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }



    /**
     * 编辑
     * @param
     * @return
     */
    @PutMapping(value = "/edit")
    @AutoLog(value = "修改软件部署表信息")
    public Result<SoftwareDeploy> edit(@RequestBody JSONObject params) {
        Result<SoftwareDeploy> result = new Result<SoftwareDeploy>();
        try {
            String managingPeopleId=params.getString("managingPeopleId");
            String softwareDeployId=params.getString("softwareDeployId");
            String checkedList=params.getString("checkedList");
            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，修改失败！");
                return result;
            }
            if(softwareDeployId!=null && !"".equals(softwareDeployId)){
                SoftwareDeploy softwareDeploy=new SoftwareDeploy();
                softwareDeploy.setManagingPeopleId(managingPeopleId);
                softwareDeploy.setSoftwareDeployName(checkedList);
                softwareDeploy.setSoftwareDeployId(softwareDeployId);
                boolean ok = softwareDeployService.updateById(softwareDeploy);
                if(ok){
                    result.success("修改成功！");
                    result.setResult(softwareDeploy);
                }else {
                    result.error500("修改失败");
                    return result;
                }

            }else {
                result.error500("修改失败");
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }






}

