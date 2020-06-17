package org.hero.renche.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.HardwareDeployInfo;
import org.hero.renche.entity.ManagingPeopleInfo;
import org.hero.renche.service.HardwareDeployInfoService;
import org.hero.renche.service.ManagingPeopleInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 硬件部署清单表 前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/renche/hardwareDeployInfo")
@Slf4j
public class HardwareDeployInfoController {
    @Autowired
    private HardwareDeployInfoService hardwareDeployInfoService;
    @Autowired
    private ManagingPeopleInfoService managingPeopleInfoService;

    /**
     * 查询

     */
    @ApiOperation(value = "获取硬件部署表", notes = "获取硬件部署表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<HardwareDeployInfo> getHardwareDeployInfo(@RequestParam(name = "managingPeopleId" ) String managingPeopleId) {
        Result<HardwareDeployInfo> result = new Result<>();

        try{
            HardwareDeployInfo hardwareDeployInfo=hardwareDeployInfoService.getHardwareDeployInfo(managingPeopleId);
         /*   if(hardwareDeployInfo==null ){
                result.error500("获取硬件部署表失败！");
                return result;
            }*/
            result.setSuccess(true);
            result.setResult(hardwareDeployInfo);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("获取硬件部署表失败！");
        }

        return result;
    }


    /**
     * 添加
     * @param
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加硬件部署表信息")
    public Result<HardwareDeployInfo> add(@RequestBody JSONObject params) {
        Result<HardwareDeployInfo> result = new Result<HardwareDeployInfo>();
        try {
            String managingPeopleId=params.getString("managingPeopleId");
            String checkedList1=params.getString("checkedList1");
            String checkedList2=params.getString("checkedList2");
            String checkedList3=params.getString("checkedList3");
            String checkedList4=params.getString("checkedList4");

            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，添加失败！");
                return result;
            }
            HardwareDeployInfo hardwareDeployInfo=hardwareDeployInfoService.getHardwareDeployInfo(managingPeopleId);
            if(hardwareDeployInfo!=null){
                result.error500("硬件部署表信息已存在，添加失败！");
                return result;
            }
            HardwareDeployInfo hardwareDeployInfo1=new HardwareDeployInfo();
            hardwareDeployInfo1.setManagingPeopleId(managingPeopleId);
            hardwareDeployInfo1.setMadeCertificate(checkedList1);
            hardwareDeployInfo1.setPersonnelGate(checkedList2);
            hardwareDeployInfo1.setCarGate(checkedList3);
            hardwareDeployInfo1.setMoveEquipment(checkedList4);
            Boolean ok=hardwareDeployInfoService.save(hardwareDeployInfo1);

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
    public Result<HardwareDeployInfo> edit(@RequestBody JSONObject params) {
        Result<HardwareDeployInfo> result = new Result<HardwareDeployInfo>();
        try {
            String managingPeopleId=params.getString("managingPeopleId");
            String hardwareDeployId=params.getString("hardwareDeployId");
            String checkedList1=params.getString("checkedList1");
            String checkedList2=params.getString("checkedList2");
            String checkedList3=params.getString("checkedList3");
            String checkedList4=params.getString("checkedList4");

            String checkedList=params.getString("checkedList");
            ManagingPeopleInfo managingPeopleInfo= managingPeopleInfoService.getById(managingPeopleId);
            if(managingPeopleInfo==null ){
                result.error500("人员管理签收单不存在，修改失败！");
                return result;
            }
            if(hardwareDeployId!=null && !"".equals(hardwareDeployId)){
                HardwareDeployInfo hardwareDeployInfo1=new HardwareDeployInfo();
                hardwareDeployInfo1.setManagingPeopleId(managingPeopleId);
                hardwareDeployInfo1.setHardwareDeployId(hardwareDeployId);
                hardwareDeployInfo1.setMadeCertificate(checkedList1);
                hardwareDeployInfo1.setPersonnelGate(checkedList2);
                hardwareDeployInfo1.setCarGate(checkedList3);
                hardwareDeployInfo1.setMoveEquipment(checkedList4);
                Boolean ok=hardwareDeployInfoService.updateById(hardwareDeployInfo1);

                if(ok){
                    result.success("修改成功！");
                    result.setResult(hardwareDeployInfo1);
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

