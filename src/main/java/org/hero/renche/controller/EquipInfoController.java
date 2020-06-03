package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.vo.EquipInfoVo;
import org.hero.renche.service.IEquipinfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(value = "/renche/equip")
@Slf4j
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
    public Result<PageInfo<EquipInfoVo>> qryEquipList(EquipInfoVo equipInfo, @RequestParam(name = "pageNo") Integer pageNo,
                                                         @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<EquipInfoVo>> result = new Result<>();
        Map<String,String> map = new HashMap<String, String>();
        PageInfo<EquipInfoVo> modelPageInfo = equipinfoService.qryEqipCountList(equipInfo,pageNo,pageSize);
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
    public Result<PageInfo<EquipInfoVo>> qryEquipListKeyDetail(EquipInfo equipInfo,@RequestParam(name = "pageNo") Integer pageNo,
                                                             @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<EquipInfoVo>> result = new Result<>();
        PageInfo<EquipInfoVo> equipInfoPageInfo = equipinfoService.qryEquipListKeyDetail(equipInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(equipInfoPageInfo);
        return result;
    }

    /**
     * 根据型号id查询设备使用情况只查询空闲和维修
     * @param equipInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询设备使用情况只查询空闲和维修", notes = "获取所有空闲和采购设备信息数据列表", produces = "application/json")
    @RequestMapping(value = "/equipKey")
    public Result<PageInfo<EquipInfoVo>> qryEquipListKey(EquipInfo equipInfo,@RequestParam(name = "pageNo") Integer pageNo,
                                                             @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<EquipInfoVo>> result = new Result<>();
        if(equipInfo.getEquipStatus()!=null && !equipInfo.getEquipStatus().equals("")){
            if(equipInfo.getEquipStatus().equals("1")){
                equipInfo.setEquipStatus("FREE");
            }else if(equipInfo.getEquipStatus().equals("2")){
                equipInfo.setEquipStatus("INREPAIR");
            }else{
                result.error500("参数被篡改！");
                result.setSuccess(false);
                return result;
            }
        }
        PageInfo<EquipInfoVo> equipInfoPageInfo = equipinfoService.qryEquipListKey(equipInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(equipInfoPageInfo);
        return result;
    }


    @AutoLog("修改设备库存数据")
    @PostMapping(value = "/updateDetailEquipInfo")
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

    @AutoLog("设备开始维修")
    @PostMapping(value = "/updateStatusweix")
    public Result<EquipInfo> updateStatusweix(@RequestParam(name = "equipId") String equipId){
        Result<EquipInfo> result = new Result<>();
        if(equipId == null || equipId.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = equipinfoService.updateEquipStatusweix(equipId);
            if(resultOk){
                result.success("设备开始维修！");
            }else{
                result.error500("修改失败！");
            }
        }
        return result;
    }

    @AutoLog("设备维修完成")
    @PostMapping(value = "/updateStatus")
    public Result<EquipInfo> updateStatus(@RequestParam(name = "equipId") String equipId){
        Result<EquipInfo> result = new Result<>();
        if(equipId == null || equipId.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = equipinfoService.updateEquipStatus(equipId);
            if(resultOk){
                result.success("设备维修完成,可以投入使用！");
            }else{
                result.error500("修改失败！");
            }
        }
        return result;
    }

    @AutoLog("设备报废")
    @PostMapping(value = "/updateEquipbaof")
    public Result<T> updateEuipStatusbaof(@RequestParam(name = "equipId") String equipId){
        Result<T> result = new Result<T>();
        if(equipId == null || equipId.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = equipinfoService.updateEuipStatusbaof(equipId);
            if(resultOk){
                result.success("修改成功");
            }else{
                result.error500("修改失败！");
            }
        }
        return result;
    }

    /**
     * 导出设备库存列表
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出数据", notes = "导出数据", produces = "application/vnd.ms-excel")
    @GetMapping("/exportEquip")
    public void exportEquip(@RequestParam(value = "param") String params, HttpServletResponse response){
        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }
            equipinfoService.exportEquipInfoList(map, response);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }
}
