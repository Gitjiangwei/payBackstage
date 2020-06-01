package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.service.ITenderInfoService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController()
@Slf4j
@RequestMapping(value = "/renche/tender")
public class TenderInfoController {


    @Autowired
    private ITenderInfoService tenderInfoService;


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
            PageInfo<TenderInfo> pageInfo= tenderInfoService.qryTenderList(tenderInfo,pageNo,pageSize);

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
     * 根据id查询招标信息
     * @param tenderId
     * @return
     */
    @ApiOperation(value = "根据id查询招标信息", notes = "根据id查询招标信息", produces = "application/json")
    @GetMapping(value = "/getTenderById")
    public Result<TenderInfo> getTenderById(@RequestParam(name = "tenderId") String tenderId) {
        Result<TenderInfo> result = new Result<>();
        TenderInfo info = tenderInfoService.qryTenderById(tenderId);
        result.setSuccess(true);
        result.setResult(info);
        return result;
    }

    /**
     * 添加招标信息
     * @param tenderInfo
     * @return
     */
    @PostMapping(value = "/addTender")
    public Result<TenderInfo> addTender(@RequestBody TenderInfo tenderInfo){
        Result<TenderInfo> result=new Result<>();
        try{
            if(tenderInfo.getIsBack() == null || "".equals(tenderInfo.getIsBack())){
                tenderInfo.setIsBack("2");//默认设置为没有退回
            }
            tenderInfo.setCreateTime(new Date());
            Boolean bool= tenderInfoService.save(tenderInfo);
            if(bool==true){
                result.success("添加成功！");
                result.setSuccess(true);
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
            TenderInfo tenderInfoEntity = tenderInfoService.getById(tenderInfo.getTenderId());
            if (tenderInfoEntity == null) {
                result.error500("未找到对应实体");
            } else {
                boolean ok = tenderInfoService.updateById(tenderInfo);
                // TODO 返回false说明什么？
                if (ok) {
                    result.success("修改成功!");
                }else {
                    result.error500("修改招标信息失败");
                    return result;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("修改招标信息失败");
        }

        return result;
    }

    /**
     * 删除招标信息
     * @param id
     * @return
     */

    @ApiOperation(value ="删除招标信息" , notes = "删除招标信息" , produces = "application/json")
    @PostMapping(value = "/delete")
    public Result<TenderInfo> deleteTenderInfo(@RequestParam(name = "id", required = true) String id){
        Result<TenderInfo> result = new Result<>();
        try{
            TenderInfo tenderInfo = tenderInfoService.getById(id);
            if (tenderInfo == null) {
                result.error500("未找到对应实体");
            } else {
                boolean ok = tenderInfoService.removeById(id);
                if (ok) {
                    result.success("删除成功!");
                }else {
                    result.error500("删除失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除操作出现异常");
        }

        return result;
    }

    /**
     * 批量删除招标信息
     * @param ids
     * @return
     */

    @ApiOperation(value ="批量删除招标信息" , notes = "批量删除招标信息" , produces = "application/json")
    @PostMapping(value = "/deleteBat")
    public Result<TenderInfo> deleteBatch(@RequestParam (name = "ids", required = true) String ids){
        Result<TenderInfo> result = new Result<>();

       try{
           if (ids == null || "".equals(ids.trim())) {
               result.error500("参数不识别！");
           } else {
               this.tenderInfoService.removeByIds(Arrays.asList(ids.split(",")));
               result.success("删除成功!");
           }
       }catch (Exception e){
           e.printStackTrace();
           log.info(e.getMessage());
           result.error500("批量删除出现异常");
       }
        return result;
    }
    /**
     * 导出招标信息列表
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出招标信息列表", notes = "导出招标信息列表", produces = "application/json")
    @GetMapping(value = "/exportTender" )
    public void exportTender(@RequestParam(value = "param") String params, HttpServletResponse response){


        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }
            tenderInfoService.exportTenderInfo(map, response);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

    @PostMapping(value = "/updateFileIds")
    public Result<TenderInfo> updateFileIds(@RequestParam(name = "tenderId") String tenderId,
                                               @RequestParam(name = "ids") String ids){
        Result<TenderInfo> result = new Result<>();
        if(tenderId == null || tenderId.equals("")){
            result.error500("遇到未知异常，请及时排除！");
        }else{
            TenderInfo tenderInfo = new TenderInfo();
            tenderInfo.setTenderId(tenderId);
            tenderInfo.setFileRelId(ids);
            Boolean resultOk = tenderInfoService.updateFileIds(tenderInfo);
            if(resultOk){
                result.success("成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

}
