package org.hero.renche.controller;

import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.FileRel;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.mapper.VisitInfoMapper;
import org.hero.renche.service.IFileRelService;
import org.hero.renche.service.IPurchaseService;
import org.hero.renche.service.VisitService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<PurchaseInfo> updateVisitInfoIds(@RequestParam(name = "visitId") String visitId,
                                                  @RequestParam(name = "ids") String ids){
        Result<PurchaseInfo> result = new Result<>();
        if(visitId == null || visitId.equals("")){
            result.error500("遇到未知异常，请及时排除！");
        }else{
            VisitInfo visitInfo = new VisitInfo();
            visitInfo.setFileRelId(ids);
            visitInfo.setVisitId(visitId);
            Boolean resultOk = visitService.updateFileIds( ids,visitId);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

}
