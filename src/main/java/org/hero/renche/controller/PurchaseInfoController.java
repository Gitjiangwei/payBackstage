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
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

}
