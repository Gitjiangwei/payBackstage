package org.hero.renche.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.service.PurchaseService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping(value = "/renche/purchase")
public class PurchaseInfoController {


    @Autowired
    private PurchaseService purchaseService;

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
    public Result<PageInfo<PurchaseInfo>> qryPurchase(PurchaseInfo purchaseInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request){

       Result<PageInfo<PurchaseInfo>> result = new Result<>();
        PageInfo<PurchaseInfo> purchaseInfoPageInfo = purchaseService.qryPurchaseInfo(purchaseInfo,pageNo,pageSize);
        // 手工转换实体驼峰字段为下划线分隔表字段
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
            String price = purchaseInfo.getPrice();
            String num = purchaseInfo.getQuantity();
            BigDecimal bigDecimal = new BigDecimal(price);
            BigDecimal bigDecimal1 = new BigDecimal(num);
            BigDecimal total = bigDecimal.multiply(bigDecimal1);
            purchaseInfo.setTotalPrice(total.toString());
            purchaseInfo.setCreateTime(new Date());
            purchaseService.save(purchaseInfo);
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
            String price = purchaseInfo.getPrice();
            String num = purchaseInfo.getQuantity();
            BigDecimal bigDecimal = new BigDecimal(price);
            BigDecimal bigDecimal1 = new BigDecimal(num);
            purchaseInfo.setTotalPrice(bigDecimal.multiply(bigDecimal1).toString());
            int purchaseInfoCount = purchaseService.qryPurchaseId(purchaseInfo.getPurchaseId());
            //int purchaseInfoCount = 1;
            if(purchaseInfoCount == 0){
                result.error500("未找到对应实体");
            }else {
                boolean results = purchaseService.updateById(purchaseInfo);
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
            boolean resultOk = purchaseService.removeByIds(Arrays.asList(ids.split(",")));
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
        int purchaseInfoCount = purchaseService.qryPurchaseId(id);
        if(id==null||"".equals(id.trim())){
            result.error500("参数丢失！");
        }else if (purchaseInfoCount == 0){
            result.error500("未找到对应实体！");
        }else{
            boolean resultOk = purchaseService.removeById(id);
            if (resultOk){
                result.success("删除成功！");
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
            boolean resultOk = purchaseService.updatePurchaseIds(ids);
            if (!resultOk){
                result.error500("收货失败！");
            }else {
                result.success("收货成功！");
            }
        }
        return result;
    }
}
