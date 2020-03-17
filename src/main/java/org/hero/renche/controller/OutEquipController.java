package org.hero.renche.controller;

import org.hero.renche.entity.OutEquipInfo;
import org.hero.renche.service.IOutEquipService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/renche/outEquip")
public class OutEquipController {

    @Autowired
    private IOutEquipService outEquipService;


    @AutoLog("设备出库")
    @PostMapping(value = "/inserOutEquip")
    public Result<OutEquipInfo> insertOutEquip(@RequestParam(name = "equipIds") String equipIds,
                                               @RequestParam(name = "prjItemId") String prjItemId){
        Result<OutEquipInfo> result = new Result<OutEquipInfo>();
        if((equipIds==null||equipIds.equals(""))||(prjItemId==null||prjItemId.equals(""))){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = outEquipService.equipInfoOut(equipIds,prjItemId);
            if(resultOk){
                result.success("添加设备成功！");
            }else {
                result.error500("添加设备失败！");
            }
        }
        return result;
    }


    @AutoLog("工程点去除设备")
    @PostMapping(value = "/delOutEquip")
    public Result<OutEquipInfo> delOutEquip(@RequestParam(name = "outId") String outId){
        Result<OutEquipInfo> result = new Result<OutEquipInfo>();
        if(outId == null || outId.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = outEquipService.delOutEquip(outId);
            if(resultOk){
                result.success("删除设备成功！");
            }else{
                result.error500("删除设备失败！");
            }
        }
        return result;
    }

}
