package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.service.TenderService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController()
@Slf4j
@RequestMapping(value = "/renche/tender")
public class TenderInfoController {


    @Autowired
    private TenderService tenderService;


    /**
     *获取招标信息列表
     * @param tenderInfo
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @ApiOperation(value = "获取招标信息列表", notes = "获取所有客户招标信息列表", produces = "application/json")
    @GetMapping(value = "/qrytenderList")
    public Result<PageInfo<TenderInfo>> qrytenderList(TenderInfo tenderInfo ,
                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10")Integer pageSize,
                                          HttpServletRequest request){

        Result<PageInfo<TenderInfo>> result=new Result<>();
        try{
            PageInfo<TenderInfo> pageInfo=tenderService.qryTenderList(tenderInfo,pageNo,pageSize);
            result.setSuccess(true);
            result.setResult(pageInfo);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("获取列表失败");

        }
            return result;

    }

    /**
     * 添加招标信息
     * @param tenderInfo
     * @param request
     * @return
     */
    @ApiOperation(value = "添加招标信息", notes = "添加招标信息", produces = "application/json")
    @PostMapping(value = "/addTender")
    public Result<TenderInfo> addTender(@RequestBody TenderInfo tenderInfo,HttpServletRequest request){
        Result<TenderInfo> result=new Result<>();
        try{
            String tenderId= UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
            tenderInfo.setTenderId(tenderId);
            tenderInfo.setCreateTime(new Date());
            Boolean bool=tenderService.addTender(tenderInfo);
            if(bool==true){
                result.setSuccess(true);
                result.setResult(tenderInfo);
            }else {
                result.error500("添加招标信息失败111");
            }


        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("添加招标信息失败222");
        }

      return result;
    }
    /**
     * 修改招标信息
     * @param tenderInfo
     * @param request
     * @return
     */
    @ApiOperation(value = "修改招标信息", notes = "修改招标信息", produces = "application/json")
    @PostMapping(value = "/upTender")
    public Result<TenderInfo> upTender(@RequestBody TenderInfo tenderInfo,HttpServletRequest request){
        Result<TenderInfo> result=new Result<>();
        try{


            Boolean bool=tenderService.upTenderById(tenderInfo);
            if(bool==true){
                result.setSuccess(true);
                result.setResult(tenderInfo);
            }else {
                result.error500("添加招标信息失败111");
            }


        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("添加招标信息失败222");
        }

        return result;
    }

    /**
     * 删除招标信息
     * @param voViditInfo
     * @param request
     * @return
     */

    @ApiOperation(value ="删除招标信息" , notes = "删除招标信息" , produces = "application/json")
    @DeleteMapping(value = "/delete")
    public Result<String> deleteTenderInfo(@RequestParam(value = "id") String id,HttpServletRequest request){
        Result<String> result=new Result<>();
        try{

            if(id==null){
                result.error500("删除失败,招标信息不存在");
            }
            boolean bo=tenderService.deleteTenderInfoById(id);
            if(bo!=true){
                result.error500("删除失败，数据库删除不成功");
            }
            result.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除失败");
        }

        return result;

    }


}
