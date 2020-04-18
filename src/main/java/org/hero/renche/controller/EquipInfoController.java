package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.modelData.EquipinfoModel;
import org.hero.renche.service.IEquipinfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /**
     * 根据型号id查询设备使用情况只查询空闲和维修
     * @param equipInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询设备使用情况只查询空闲和维修", notes = "获取所有空闲和采购设备信息数据列表", produces = "application/json")
    @RequestMapping(value = "/equipKey")
    public Result<PageInfo<EquipInfo>> qryEquipListKey(EquipInfo equipInfo,@RequestParam(name = "pageNo") Integer pageNo,
                                                             @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<EquipInfo>> result = new Result<>();
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
        PageInfo<EquipInfo> equipInfoPageInfo = equipinfoService.qryEquipListKey(equipInfo,pageNo,pageSize);
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
    @GetMapping("/exportEquip")
    public Result<PageInfo<EquipInfo>> exportEquip(@RequestParam(value = "param") String params, HttpServletResponse response){
        Result<PageInfo<EquipInfo>> result=new Result<>();
        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }
            List<EquipinfoModel> qryList= equipinfoService.exportEquipInfoList(map);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            EquipinfoModel vv=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vv=qryList.get(i);
                Date date1= vv.getCreateTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
                String createTime = formatter.format(date1);
                list.add(i+1);
                list.add(vv.getEquipName());
                list.add(vv.getEquipModel());
                list.add(vv.getCount());
                list.add(vv.getInuseCount());
                list.add(vv.getFreeCount());
                list.add(vv.getScripCount());
                list.add(vv.getMaintenonceCount());
                list.add(createTime);
                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("设备库存");
            List titlesList=new ArrayList();
            titlesList.add("序号");
            titlesList.add("设备名称");
            titlesList.add("设备型号");
            titlesList.add("数量");
            titlesList.add("使用中");
            titlesList.add("空闲");
            titlesList.add("报废");
            titlesList.add("维修中");
            titlesList.add("入库时间");
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "设备库存.xlsx" , excelData);
            result.setMessage("导出成功");

        }catch (Exception e){
            e.printStackTrace();

            result.error500("导出失败");
        }
        return result;
    }
}
