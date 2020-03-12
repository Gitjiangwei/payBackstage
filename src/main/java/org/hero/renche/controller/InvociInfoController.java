package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.service.InvociService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/renche/invoic")
@Slf4j
public class InvociInfoController {

    @Autowired
    private InvociService invociService;

    /**
     * 获取发票列表
     * @param voInvoicInfo
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @ApiOperation(value = "获取发票列表", notes = "获取所有发票列表", produces = "application/json")
    @GetMapping("/qryInvoicList")
    public Result<PageInfo<VoInvoicInfo>> qryInvoicList( VoInvoicInfo voInvoicInfo,
                                                        @RequestParam(value = "pageNo" ,defaultValue = "1")Integer pageNo,
                                                        @RequestParam(value = "pageSize" ,defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest request)  {
        Result<PageInfo<VoInvoicInfo>> result=new Result<>();

        try{
            PageInfo<VoInvoicInfo>  pageInfo=invociService.qryInvociInfo(voInvoicInfo,pageNo,pageSize);
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
     * @param voInvoicInfo
     * @return
     */
    @ApiOperation(value = "添加发票信息", notes = "添加发票信息", produces = "application/json")
    @PostMapping("/addInvoic")
    public Result<VoInvoicInfo> addInvoic(@RequestBody VoInvoicInfo voInvoicInfo){
        Result<VoInvoicInfo> result=new Result<>();

        try {
            String invociId= UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
            voInvoicInfo.setInvociId(invociId);
            voInvoicInfo.setCreateTime(new Date());
            boolean addbo=invociService.addInvoic(voInvoicInfo);
            if(addbo!=true){
                result.error500("添加发票信息失败");
            }else{
                result.setMessage("添加成功");
                result.setResult(voInvoicInfo);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("添加发票信息失败");
        }
        return result;
    }

    /**
     * 修改发票信息
     * @param voInvoicInfo
     * @return
     */
    @ApiOperation(value = "修改发票信息" ,notes = "修改发票信息" ,produces = "application/json")
    @PutMapping("/upInvoic")
    public Result<VoInvoicInfo> upInvoic(@RequestBody VoInvoicInfo voInvoicInfo){
        Result<VoInvoicInfo> result=new Result<>();


        try {

            boolean upbo=invociService.updateInvoic(voInvoicInfo);
            if(upbo!=true){
                result.error500("修改发票信息失败");
            }else{
                result.setMessage("修改成功");
                result.setResult(voInvoicInfo);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("修改发票信息失败");
        }
        return result;
    }


    @ApiOperation(value = "根据id删除发票信息" , notes = "根据id删除发票信息" , produces = "application/json")
    @PostMapping("/delete")
    public Result<String> delete( @RequestParam(name = "id") String id ){

        Result<String> result =new Result<>();
        try {
            if(id==null || "".equals(id)){
                result.error500("发票ID不存在");
            }
            Boolean debo=invociService.deleteById(id);
            if(debo!=true){
                result.error500("删除发票信息失败");
            }else{
                result.setMessage("删除成功");

            }

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除发票信息失败");

        }


        return result;


    }

    /**
     * 批量删除发票信息
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除发票信息" , notes = "批量删除发票信息" , produces = "application/json")
    @PostMapping ("/deleteBat")
    public Result<String> deleteBat(@RequestParam("ids") String ids){
        Result<String> result=new Result<>();

        try {
            if(ids ==null ||"".equals(ids) ){
                result.error500("发票ID不存在");
            }
            String[] is=ids.split(",");
           List<String> list= Arrays.asList(is);

            Boolean debo=invociService.deleteBatInvoicInfo(list);
            if(debo!=true){
                result.error500("批量删除发票信息失败");
            }else{
                result.setMessage("批量删除成功");

            }

        }catch (Exception e){

            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("批量删除发票信息失败");

        }

        return result;
    }
}
