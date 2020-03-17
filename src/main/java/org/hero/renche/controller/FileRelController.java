package org.hero.renche.controller;

import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.*;
import org.hero.renche.mapper.VisitInfoMapper;
import org.hero.renche.service.*;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/renche/file")
public class FileRelController {

    @Autowired
    private IFileRelService fileRelService;


    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private InvociService invociService;



    @AutoLog("删除附件")
    @PostMapping(value = "/deleteIds")
    public Result<FileRel> deleteFileIds(@RequestParam(name = "ids") String ids){
        Result<FileRel> result = new Result<>();
        if(ids == null || ids.equals("")){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = fileRelService.deleteFile(ids);
            if(resultOk){
                result.success("删除成功");
            }else {
                result.error500("删除失败！");
            }
        }

        return result;
    }

    @PostMapping(value = "/updateIds")
    public Result<PurchaseInfo> updatePurchaseIds(@RequestParam(name = "purchaseId") String purchaseId,
                                                  @RequestParam(name = "ids") String ids){
        Result<PurchaseInfo> result = new Result<>();
        if(purchaseId == null || purchaseId.equals("")){
            result.error500("遇到未知异常，请及时排除！");
        }else{
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setPurchaseId(purchaseId);
            purchaseInfo.setFileRelId(ids);
            Boolean resultOk = purchaseService.updateFileIds(purchaseInfo);
            if(resultOk){
                result.success("成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }



    @PostMapping(value = "/updateVisitInfoIds")
    public Result<VisitInfo> updateVisitInfoIds(@RequestParam(name = "visitId") String visitId,
                                                  @RequestParam(name = "ids") String ids){
        Result<VisitInfo> result = new Result<>();
        if(visitId == null || visitId.equals("")){
            result.error500("遇到未知异常，请及时排除！");
        }else{
            Boolean resultOk = visitService.updateFileIds( ids,visitId);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

    @PostMapping(value = "updateWorkFileIds")
    public Result<WorkOrderInfo> updateWorkFileIds(@RequestParam(name = "workId") String workId,
                                                   @RequestParam(name = "ids") String ids){
        Result<WorkOrderInfo> result=new Result<>();
        if(workId == null || workId.equals("")){
            result.error500("工单ID为空，请及时排除！");
        }else{

            Boolean resultOk = workOrderService.updateFileIds( ids,workId);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;


    }



    @PostMapping(value = "updateInvoicFileIds")
    public Result<InvociInfo> updateInvoicFileIds(@RequestParam(name = "invociId") String invociId,
                                                  @RequestParam(name = "ids") String ids){

        Result<InvociInfo> result=new Result<>();
        if(invociId == null || invociId.equals("")){
            result.error500("工单ID为空，请及时排除！");
        }else{

            Boolean resultOk = invociService.updateFileIds( ids,invociId);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;

    }

}
