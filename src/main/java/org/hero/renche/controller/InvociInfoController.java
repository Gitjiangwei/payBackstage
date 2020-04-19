package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.vo.InvociInfoVo;
import org.hero.renche.service.InvociService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/renche/invoci")
@Slf4j
public class InvociInfoController {

    @Autowired
    private InvociService invociService;

    /**
     * 获取发票列表
     * @param invociInfo
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @ApiOperation(value = "获取发票列表", notes = "获取所有发票列表", produces = "application/json")
    @GetMapping("/qryInvociList")
    public Result<PageInfo<InvociInfoVo>> qryInvoicList( InvociInfoVo invociInfo,
                                                        @RequestParam(value = "pageNo" ,defaultValue = "1")Integer pageNo,
                                                        @RequestParam(value = "pageSize" ,defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest request)  {
        Result<PageInfo<InvociInfoVo>> result=new Result<>();
        try{
            PageInfo<InvociInfoVo>  pageInfo=invociService.qryInvociInfo(invociInfo,pageNo,pageSize);
            result.setSuccess(true);
            result.setResult(pageInfo);

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("查找发票列表失败");
        }

        return result;
    }

    /**
     * 添加发票信息
     * @param invociInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加发票信息")
    public Result<InvociInfo> add(@RequestBody InvociInfo invociInfo) {
        Result<InvociInfo> result = new Result<>();
        invociInfo.setCreateTime(new Date());
        try {
            boolean ok = invociService.save(invociInfo);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 修改发票信息
     * @param invociInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<InvociInfo> eidt(@RequestBody InvociInfo invociInfo) {
        Result<InvociInfo> result = new Result<>();
        InvociInfo invociInfoEntity = invociService.getById(invociInfo.getInvociId());
        if (invociInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = invociService.updateById(invociInfo);
            if (ok) {
                result.success("修改成功!");
            }else{
                result.error500("修改失败");
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
    @AutoLog(value = "删除发票信息")
    @PostMapping(value = "/delete")
    public Result<InvociInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<InvociInfo> result = new Result<>();
        InvociInfo invociInfo = invociService.getById(id);
        if (invociInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = invociService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }else{
                result.error500("删除失败");
            }
        }

        return result;
    }

    /**
     * 批量删除发票信息
     * @param ids
     * @return
     */
    @PostMapping(value = "/deleteBatch")
    public Result<InvociInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<InvociInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.invociService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    @PostMapping(value = "updateFileIds")
    public Result<InvociInfo> updateInvoicFileIds(@RequestParam(name = "invociId") String invociId, @RequestParam(name = "ids") String ids){

        Result<InvociInfo> result=new Result<>();
        if(invociId == null || invociId.equals("")){
            result.error500("工单ID为空，请及时排除！");
        }else{
            InvociInfo invociInfo = new InvociInfo();
            invociInfo.setInvociId(invociId);
            invociInfo.setFileRelId(ids);
            Boolean resultOk = invociService.updateFileIds(invociInfo);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }



    /**
     * 导出发票信息列表
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出发票信息列表", notes = "导出发票信息列表", produces = "application/vnd.ms-excel")
    @GetMapping(value = "/exportInvoci" )
    public void exportInvoic(@RequestParam(value = "param") String params, HttpServletResponse response){

        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }

            invociService.exportInvociInfo(map, response);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

}
