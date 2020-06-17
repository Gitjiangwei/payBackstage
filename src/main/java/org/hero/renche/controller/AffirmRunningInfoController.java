package org.hero.renche.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.AffirmRunningInfo;
import org.hero.renche.entity.ManagingPeopleInfo;
import org.hero.renche.service.AffirmRunningInfoService;
import org.hero.renche.service.ManagingPeopleInfoService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 运行确认（施工项目部) 前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/renche/affirmRunningInfo")
@Slf4j
public class AffirmRunningInfoController {

    @Autowired
    private AffirmRunningInfoService affirmRunningInfoService;


    @Autowired
    private ManagingPeopleInfoService managingPeopleInfoService;



    /**
     * 查询

     */
    @ApiOperation(value = "获取运行确认表", notes = "获取运行确认表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<AffirmRunningInfo> getAffirmRunningInfo(@RequestParam(name = "managingPeopleId" ) String managingPeopleId) {
        Result<AffirmRunningInfo> result = new Result<>();

        try{
            AffirmRunningInfo affirmRunningInfo=affirmRunningInfoService.getAffirmRunningInfo(managingPeopleId);
            result.setSuccess(true);
            result.setResult(affirmRunningInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("获取运行确认失败！");
        }

        return result;
    }


    /**
     * 添加
     */
    @PostMapping(value = "/add")
    public Result addAffirmRunningInfo(@RequestBody JSONObject params){

        Result result=new Result();
        try{
            String managingPeopleId=params.getString("managingPeopleId");
            String checkedListC=params.getString("checkedListC");
            String checkedListD=params.getString("checkedListD");
            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，添加失败！");
                return result;
            }
            AffirmRunningInfo affirmRunningInfo=affirmRunningInfoService.getAffirmRunningInfo(managingPeopleId);
            if(affirmRunningInfo!=null){
                result.error500("运行确认表已存在，不可重复添加！");
                return result;

            }
            AffirmRunningInfo affirmRunningInfo1=new AffirmRunningInfo();
            affirmRunningInfo1.setManagingPeopleId(managingPeopleId);
            affirmRunningInfo1.setSysRun(checkedListC);
            affirmRunningInfo1.setHardwareRun(checkedListD);
            Boolean ok=affirmRunningInfoService.save(affirmRunningInfo1);
            if(ok) result.success("添加成功！");
            else {
                result.error500("操作失败");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败！");
        }


        return result;



    }


    /**
     * 编辑
     */
    @PutMapping (value = "/edit")
    public Result editAffirmRunningInfo(@RequestBody JSONObject params){

        Result result=new Result();
        try{
            String managingPeopleId=params.getString("managingPeopleId");
            String affirmRuningId=params.getString("affirmRuningId");
            String checkedListC=params.getString("checkedListC");
            String checkedListD=params.getString("checkedListD");
            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，修改失败！");
                return result;
            }

            if(affirmRuningId==null||"".equals(affirmRuningId)){
                result.error500("运行确认表ID不存在，修改失败！");
                return result;

            }
            AffirmRunningInfo affirmRunningInfo1=new AffirmRunningInfo();
            affirmRunningInfo1.setAffirmRuningId(affirmRuningId);
            affirmRunningInfo1.setManagingPeopleId(managingPeopleId);
            affirmRunningInfo1.setSysRun(checkedListC);
            affirmRunningInfo1.setHardwareRun(checkedListD);
            Boolean ok=affirmRunningInfoService.updateById(affirmRunningInfo1);
            if(ok) result.success("修改成功！");
            else {
                result.error500("操作失败");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败！");
        }


        return result;



    }

}

