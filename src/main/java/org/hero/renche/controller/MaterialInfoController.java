package org.hero.renche.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.MaterialInfo;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.service.IMaterialInfoService;
import org.hero.renche.util.PinyinUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Title: Controller
 * @Description: 物料库信息管理
 */
@RestController
@RequestMapping("/renche/materialInfo")
@Slf4j
public class MaterialInfoController {

    @Autowired
    private IMaterialInfoService materialInfoService;

    /**
     * 分页列表查询物料库信息列表
     * @param pageNo
     * @param pageSize
     * @returnp'r
     */
    @ApiOperation(value = "获取物料库信息列表", notes = "获取物料库信息列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<MaterialInfo>> list(MaterialInfo materialInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<PageInfo<MaterialInfo>> result = new Result<>();
        PageInfo<MaterialInfo> pageList = materialInfoService.qryMaterialInfoList(materialInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     * @param materialInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加物料")
    public Result<MaterialInfo> add(@RequestBody MaterialInfo materialInfo) {
        Result<MaterialInfo> result = new Result<>();
        materialInfo.setCreateTime(new Date());
        String materialName = materialInfo.getMaterialName();
        String pyName = "";
        int length = materialName.length();
        if(length > 3){
            String quanPin = PinyinUtil.getPinYin(materialName.substring(0, 3),true);
            String shouZiMu = PinyinUtil.getPinYinHeadChar(materialName.substring(3, length),true);
            pyName = quanPin + shouZiMu;
        }else{
            pyName = PinyinUtil.getPinYin(materialName,true);
        }
        materialInfo.setPyName(pyName);
        //编号
        //每个设备最大数为6位数
        String numberCount = "";
        String maxMaterialNo = materialInfoService.getMaterialNo(pyName);//物料库同类型物料已有数量
        for(int i = 0;i< 6 - maxMaterialNo.length(); i++){
            numberCount = "0" + numberCount;
        }
        maxMaterialNo = String.valueOf(Integer.valueOf(maxMaterialNo)+1);
        String number = "WL-"+pyName+"-"+numberCount+maxMaterialNo;
        materialInfo.setMaterialNo(number);

        try {
            boolean ok = materialInfoService.save(materialInfo);
            result.setResult(materialInfo);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     * @param materialInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<MaterialInfo> eidt(@RequestBody MaterialInfo materialInfo) {
        Result<MaterialInfo> result = new Result<>();
        MaterialInfo materialInfoEntity = materialInfoService.getById(materialInfo.getMaterialId());
        if (materialInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = materialInfoService.updateById(materialInfo);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "删除物料信息")
    @PostMapping(value = "/delete")
    public Result<MaterialInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<MaterialInfo> result = new Result<>();
        MaterialInfo materialInfo = materialInfoService.getById(id);
        if (materialInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = materialInfoService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }
        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteBatch")
    public Result<MaterialInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<MaterialInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.materialInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

//    /**
//     * 导出物料库信息列表
//     *
//     * @param
//     * @param response
//     * @return
//     */
//    @ApiOperation(value = "导出物料库信息列表", notes = "导出物料库信息列表", produces = "application/vnd.ms-excel")
//    @GetMapping(value = "/exportBackInfo" )
//    public void exportBackInfo(@RequestParam(value = "param") String params, HttpServletResponse response){
//        try{
//            params = params.replace("\"","");
//            String[] paramStrs = params.split(",");
//            Map<String,String> map = new HashMap<>();
//            for (String str : paramStrs){
//                String[] content = str.split(":");
//                map.put(content[0],content[1]);
//            }
//
//            materialInfoService.exportBackInfo(map, response);
//        }catch (Exception e){
//            e.printStackTrace();
//            log.info(e.getMessage());
//        }
//    }

}

