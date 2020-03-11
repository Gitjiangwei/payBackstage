package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.modelData.EquipinfoModel;
import org.hero.renche.service.IEquipinfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/renche/equip")
public class EquipInfoController {

    @Autowired
    private IEquipinfoService equipinfoService;

    /**
     * 查询设备库存信息
     * @param equipInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取设备库存基本信息", notes = "获取所有采购设备信息数据列表", produces = "application/json")
    @RequestMapping(value = "/equipList")
    public Result<PageInfo<EquipinfoModel>> qryEquipList(EquipInfo equipInfo, @RequestParam(name = "pageNo") Integer pageNo,
                                                         @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<EquipinfoModel>> result = new Result<>();
        Map<String,String> map = new HashMap<String, String>();
        map.put("equipName",equipInfo.getEquipName());
        map.put("equipModel",equipInfo.getEquipModel());
        System.out.println(equipInfo);
        System.out.println(map);;
        PageInfo<EquipinfoModel> modelPageInfo = equipinfoService.qryEqipCountList(map,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(modelPageInfo);
        return result;
    }


    /**
     * 根据型号id查询设备使用情况
     * @param equipInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询设备使用情况", notes = "获取所有采购设备信息数据列表", produces = "application/json")
    @RequestMapping(value = "/equipKeyDetail")
    public Result<PageInfo<EquipInfo>> qryEquipListKeyDetail(EquipInfo equipInfo,@RequestParam(name = "pageNo") Integer pageNo,
                                                             @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<EquipInfo>> result = new Result<>();
        PageInfo<EquipInfo> equipInfoPageInfo = equipinfoService.qryEquipListKeyDetail(equipInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(equipInfoPageInfo);
        return result;
    }


    @AutoLog("修改设备库存数据")
    @PostMapping(value = "/equipKeyDetail")
    public Result<EquipInfo> updateDetailEquipInfo(@RequestBody EquipInfo equipInfo){
        Result<EquipInfo> result = new Result<>();
        if(equipInfo.getEquipId()==null || equipInfo.getEquipId().equals("")){
            result.error500("参数丢失！");
        }else {
            if(equipInfo.getEquipStatus()!=null && equipInfo.getEquipStatus().equals("INREPAIR")){
                equipInfo.setEquipStatus("0bd710ac593811eaab75b4a9fc4b5236");
            }
            Boolean resultOk = equipinfoService.updateDetailEquipInfo(equipInfo);
            if(resultOk){
                result.success("修改成功！");
            }else {
                result.error500("修改失败！");
            }
        }
        return result;
    }
}
