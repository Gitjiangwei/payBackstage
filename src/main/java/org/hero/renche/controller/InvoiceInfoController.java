package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.InvoiceInfo;
import org.hero.renche.service.IInvoiceInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * @Title: Controller
 * @Description: 开票信息管理
 */
@RestController
@RequestMapping("/renche/invoiceInfo")
@Slf4j
public class InvoiceInfoController {

    @Autowired
    private IInvoiceInfoService invoiceInfoService;

    /**
     * 分页列表查询合同关联的开票信息
     * @param pageNo
     * @param pageSize
     * @param contractId
     * @returnp'r
     */
    @ApiOperation(value = "获取开票信息列表", notes = "获取开票信息列表", produces = "application/json")
    @GetMapping(value = "/contractInvoiceList")
    public Result<PageInfo<InvoiceInfo>> contractInvoiceList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             @RequestParam(name = "contractId") String contractId) {
        Result<PageInfo<InvoiceInfo>> result = new Result<>();
        PageInfo<InvoiceInfo> pageList = invoiceInfoService.qryInvoiceByContractId(pageNo,pageSize,contractId);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     * @param invoiceInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加合同信息")
    public Result<InvoiceInfo> add(@RequestBody InvoiceInfo invoiceInfo) {
        Result<InvoiceInfo> result = new Result<>();
        invoiceInfo.setCreateTime(new Date());
        try {
            boolean ok = invoiceInfoService.save(invoiceInfo);
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
     * @param invoiceInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<InvoiceInfo> eidt(@RequestBody InvoiceInfo invoiceInfo) {
        Result<InvoiceInfo> result = new Result<>();
        InvoiceInfo invoiceInfoEntity = invoiceInfoService.getById(invoiceInfo.getInvoiceId());
        if (invoiceInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = invoiceInfoService.updateById(invoiceInfo);
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
    @AutoLog(value = "删除合同信息")
    @PostMapping(value = "/delete")
    public Result<InvoiceInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<InvoiceInfo> result = new Result<>();
        InvoiceInfo invoiceInfo = invoiceInfoService.getById(id);
        if (invoiceInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = invoiceInfoService.removeById(id);
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
    public Result<InvoiceInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<InvoiceInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.invoiceInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

}
