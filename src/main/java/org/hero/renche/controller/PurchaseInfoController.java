package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.entity.FileRel;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.entity.vo.PurchaseInfoVo;
import org.hero.renche.service.IFileRelService;
import org.hero.renche.service.IPurchaseService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@Slf4j
@RequestMapping(value = "/renche/purchase")
public class PurchaseInfoController {


    @Autowired
    private IPurchaseService IPurchaseService;

    @Autowired
    private IFileRelService fileRelService;

    /**
     * 查询采购设备信息
     * @param purchaseInfo
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @ApiOperation(value = "获取采购设备数据列表", notes = "获取所有采购设备信息数据列表", produces = "application/json")
    @RequestMapping(value = "/qryPurchase")
    public Result<PageInfo<PurchaseInfoVo>> qryPurchase(PurchaseInfoVo purchaseInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request){

       Result<PageInfo<PurchaseInfoVo>> result = new Result<>();
        PageInfo<PurchaseInfoVo> purchaseInfoPageInfo = IPurchaseService.qryPurchaseInfo(purchaseInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(purchaseInfoPageInfo);
        return result;
    }

    /**
     * 添加需要采购的设备
     * @param purchaseInfo
     * @return
     */
    @PostMapping(value = "/savePurchase")
    @AutoLog(value = "添加需要采购的设备")
    public Result<PurchaseInfo> savePurchase(@RequestBody PurchaseInfo purchaseInfo){
        Result<PurchaseInfo> result = new Result<PurchaseInfo>();
        try {
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            purchaseInfo.setCreateUser(sysUser.getId());
            if(purchaseInfo.getArrivalTime()!=null && !purchaseInfo.getArrivalTime().equals("")){
                purchaseInfo.setIsarrival("1");
            }
            purchaseInfo.setCreateTime(new Date());
            IPurchaseService.save(purchaseInfo);
            result.success("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("添加失败");
        }
        return result;
    }


    /**
     * 修改需要采购的设备
     * @param purchaseInfo
     * @return
     */
    @PutMapping(value = "/editPurchase")
    public Result<PurchaseInfo> editPurchase(@RequestBody PurchaseInfo purchaseInfo){
        Result<PurchaseInfo> result = new Result<PurchaseInfo>();
        try {
            PurchaseInfo purchaseInfoEntity = IPurchaseService.getById(purchaseInfo.getPurchaseId());
            if(purchaseInfo.getArrivalTime()!=null && !purchaseInfo.getArrivalTime().equals("")){
                purchaseInfo.setIsarrival("1");
            }else{
                purchaseInfo.setIsarrival("2");
            }
            if(purchaseInfoEntity == null){
                result.error500("未找到对应实体");
            }else {
                boolean results = IPurchaseService.updateById(purchaseInfo);
                if (results){
                    result.success("修改成功");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("修改失败");
        }
        return result;
    }

    @AutoLog("批量删除采购的设备")
    @PostMapping(value = "/deleteBatch")
    public Result<PurchaseInfo> deleteBatch(@RequestParam(name = "ids") String ids){
        Result<PurchaseInfo> result = new Result<PurchaseInfo>();
        if(ids==null||"".equals(ids.trim())){
            result.error500("参数丢失！");
        }else{
            boolean resultOk = IPurchaseService.removeByIds(Arrays.asList(ids.split(",")));
            if(resultOk){
                result.success("删除成功！");
            }
        }
        return result;
    }

    @AutoLog("删除采购的设备")
    @PostMapping(value = "/delete")
    public Result<PurchaseInfo> deleteById(@RequestParam(name = "id") String id){
        Result<PurchaseInfo> result = new Result<PurchaseInfo>();
        PurchaseInfo purchaseInfoEntity = IPurchaseService.getById(id);
        if(purchaseInfoEntity == null){
            result.error500("未找到对应实体");
        }else {
            boolean resultOk = IPurchaseService.removeById(id);
            if (resultOk){
                result.success("删除成功！");
            }else {
                result.error500("删除失败！");
            }
        }
        return result;
    }


    @AutoLog("批量收货")
    @PostMapping(value = "/updateIsArrival")
    public Result<PurchaseInfo> updateIsArrivalByIds(@RequestParam(name = "ids") String ids){
        Result<PurchaseInfo> result = new Result<PurchaseInfo>();
        if (ids==null || "".equals(ids.trim())){
            result.success("参数丢失！");
        }else {
            boolean resultOk = IPurchaseService.updatePurchaseIds(ids);
            if (!resultOk){
                result.error500("收货失败！");
            }else {
                result.success("收货成功！");
            }
        }
        return result;
    }

//    @AutoLog("设备入库")
//    @PostMapping(value = "/insertReceiving")
//    public Result<PurchaseInfo> insertReceiving(@RequestBody JSONObject jsonParam){
//        Result<PurchaseInfo> result = new Result<PurchaseInfo>();
//        PurchaseInfo purchaseInfo = new PurchaseInfo();
//        purchaseInfo.setMaterialId(jsonParam.get("materialId").toString());
//        purchaseInfo.setPrice(jsonParam.get("price").toString());
//        purchaseInfo.setQuantity(jsonParam.get("quantity").toString());
//        purchaseInfo.setPurchaseId(jsonParam.get("purchaseId").toString());
//        Boolean resultCount =IPurchaseService.insertReceiving(purchaseInfo);
//        if(resultCount){
//            result.success("程序自动将设备入库，请等待！");
//        }else{
//            result.error500("入库失败");
//        }
//        return result;
//    }

    @AutoLog("设备入库")
    @PostMapping(value = "/insertReceiving")
    public Result<PurchaseInfoVo> insertReceiving(@RequestBody PurchaseInfoVo purchaseInfo){
        Result<PurchaseInfoVo> result = new Result<>();
        Boolean resultCount =IPurchaseService.insertReceiving(purchaseInfo);
        if(resultCount){
            result.success("程序自动将设备入库，请等待！");
        }else{
            result.error500("入库失败");
        }
        return result;
    }

    @GetMapping(value = "/qryPurchaseKey")
    public Result<PurchaseInfo> qryPurchaseKey(@RequestParam(name = "purchaseId") String purchaseId){
        Result<PurchaseInfo> result = new Result<>();
        Boolean resultOk = IPurchaseService.qryPurchaseInfoKey(purchaseId);
        if(resultOk){
            result.success("1");
        }else {
            result.error500("2");
        }
        return result;
    }

    @RequestMapping(value = "/fileList")
    public Result<PageInfo<FileRel>> qryFileList(FileRel rel,@RequestParam(name = "fileRelId",required = false) String fileRelId,
                                       @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
        Result<PageInfo<FileRel>> result = new Result<PageInfo<FileRel>>();
        PageInfo<FileRel> fileRelPageInfo = fileRelService.qryFileRel(rel,fileRelId,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(fileRelPageInfo);
        return result;
    }

    /**
     * 导出设备采购列表
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出数据", notes = "导出数据", produces = "application/vnd.ms-excel")
    @GetMapping("/exportPurchase")
    public Result<PageInfo<PurchaseInfo>> exportPurchase(@RequestParam(value = "param") String params, HttpServletResponse response){
        Result<PageInfo<PurchaseInfo>> result=new Result<>();

        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }

            IPurchaseService.exportPurchaseInfo(map, response);
            result.setMessage("导出成功");

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("导出失败");
        }
        return result;
    }

}
