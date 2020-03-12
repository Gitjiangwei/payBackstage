package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.service.TenderService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
            String isBack=tenderInfo.getIsBack();
            if("1".equals(isBack)){
                isBack="是";
            }else if ("2".equals(isBack))
                isBack="否";
            tenderInfo.setIsBack(isBack);
            String tenderId= UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
            tenderInfo.setTenderId(tenderId);
            tenderInfo.setCreateTime(new Date());
            Boolean bool=tenderService.addTender(tenderInfo);
            if(bool==true){
                result.success("添加成功！");
                result.setResult(tenderInfo);
            }else {
                result.error500("添加招标信息失败");
            }


        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("添加招标信息失败");
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
   @PutMapping(value = "/upTender")
    public Result<TenderInfo> upTender(@RequestBody TenderInfo tenderInfo,HttpServletRequest request){
        Result<TenderInfo> result=new Result<>();
        try{
            String isBack=tenderInfo.getIsBack();
            if("1".equals(isBack)){
                isBack="是";
            }else if ("2".equals(isBack))
                isBack="否";
            tenderInfo.setIsBack(isBack);

            Boolean bool=tenderService.upTenderById(tenderInfo);
            if(bool==true){
                result.success("修改成功");
                result.setResult(tenderInfo);
            }else {
                result.error500("添加招标信息失败");
            }


        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("添加招标信息失败");
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
    @PostMapping(value = "/delete")
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
            result.success("删除成功！");

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除失败");
        }

        return result;

    }

    /**
     * 批量删除招标信息
     * @param ids
     * @param request
     * @return
     */

    @ApiOperation(value ="批量删除招标信息" , notes = "批量删除招标信息" , produces = "application/json")
    @PostMapping(value = "/deleteBat")
    public Result<String> deleteBatch(@RequestParam (value = "ids") String ids ,HttpServletRequest request){
        Result<String> result=new Result<>();
       try{
           List<String> paramIds= Arrays.asList(ids.split(","));
           if(paramIds.size()<=0||paramIds==null){
               result.error500("删除失败,招标信息不存在");
           }
           boolean delBool=tenderService.deleteBatchTenderInfo(paramIds);
           if(delBool!=true){
               result.error500("批量删除失败");
           }
           result.success("批量删除成功！");

       }catch (Exception e){
           e.printStackTrace();
           log.info(e.getMessage());
           result.error500("批量删除失败");
       }
        return  result;
    }


}
