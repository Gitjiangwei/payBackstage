package org.hero.renche.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ManagingPeopleInfo;
import org.hero.renche.entity.UserTrainInfo;
import org.hero.renche.service.ManagingPeopleInfoService;
import org.hero.renche.service.UserTrainInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户培训（验收各方） 前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/renche/userTrainInfo")
@Slf4j
public class UserTrainInfoController {

    @Autowired
    private UserTrainInfoService userTrainInfoService;

    @Autowired
    private ManagingPeopleInfoService managingPeopleInfoService;



    /**
     * 查询

     */
    @ApiOperation(value = "获取用户培训表", notes = "获取用户培训表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<UserTrainInfo> getHardwareDeployInfo(@RequestParam(name = "managingPeopleId" ) String managingPeopleId) {
        Result<UserTrainInfo> result = new Result<>();

        try{
            UserTrainInfo userTrainInfo=userTrainInfoService.getUserTrainInfo(managingPeopleId);
            result.setSuccess(true);
            result.setResult(userTrainInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("获取用户培训表失败！");
        }

        return result;
    }


    /**
     * 添加
     * @param
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加用户培训表信息")
    public Result<UserTrainInfo> add(@RequestBody JSONObject params) {
        Result<UserTrainInfo> result = new Result<UserTrainInfo>();
        try {
            String managingPeopleId=params.getString("managingPeopleId");
            String checkedListA=params.getString("checkedListA");
            String checkedListB=params.getString("checkedListB");

            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，添加失败！");
                return result;
            }
            UserTrainInfo userTrainInfo=userTrainInfoService.getUserTrainInfo(managingPeopleId);
            if(userTrainInfo!=null){
                result.error500("用户培训表信息已存在，添加失败！");
                return result;
            }
            UserTrainInfo userTrainInfo1=new UserTrainInfo();
            userTrainInfo1.setDataTransfer(checkedListA);
            userTrainInfo1.setTrainContent(checkedListB);
            userTrainInfo1.setManagingPeopleId(managingPeopleId);
            Boolean ok=userTrainInfoService.save(userTrainInfo1);

            if(ok){
                result.success("添加成功！");
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
    @AutoLog(value = "修改硬件部署表信息")
    public Result<UserTrainInfo> edit(@RequestBody JSONObject params) {
        Result<UserTrainInfo> result = new Result<UserTrainInfo>();
        try {
            String managingPeopleId=params.getString("managingPeopleId");
            String userTrainId=params.getString("userTrainId");
            String checkedListA=params.getString("checkedListA");
            String checkedListB=params.getString("checkedListB");
            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，修改失败！");
                return result;
            }
            if(userTrainId!=null && !"".equals(userTrainId)){
                UserTrainInfo userTrainInfo =new UserTrainInfo();
                userTrainInfo.setUserTrainId(userTrainId);
                userTrainInfo.setManagingPeopleId(managingPeopleId);
                userTrainInfo.setDataTransfer(checkedListA);
                userTrainInfo.setTrainContent(checkedListB);
                Boolean ok=userTrainInfoService.updateById(userTrainInfo);
                if(ok){
                    result.success("修改成功！");
                    result.setResult(userTrainInfo);
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

