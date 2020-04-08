package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.service.InvociService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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



    /**
     * 导出发票信息列表
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出发票信息列表", notes = "导出发票信息列表", produces = "application/json")
    @GetMapping(value = "/exportInvoic" )
    public Result<PageInfo<VoInvoicInfo>> exportInvoic(VoInvoicInfo voInvoicInfo , HttpServletResponse response){
        Result<PageInfo<VoInvoicInfo>> result=new Result<>();

        try{
            List<VoInvoicInfo> qryList=invociService.exportVoInvoicInfoList(voInvoicInfo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            VoInvoicInfo vv=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vv=qryList.get(i);
                Date date1= vv.getCreateTime();
                Date date2=vv.getInvociTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
                String createTime = formatter.format(date1);
                String invociTime = formatter.format(date2);
                list.add(i+1);
                list.add(vv.getInvociName());
                list.add(invociTime);
                list.add(vv.getContent());
                list.add(vv.getShuihao());
                list.add(vv.getAddress());
                list.add(vv.getTel());
                list.add(vv.getBank());
                list.add(vv.getBankNo());
                list.add(createTime);
                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("发票管理");
            List titlesList=new ArrayList();
            titlesList.add("序号");
            titlesList.add("发票名称");
            titlesList.add("开票时间");
            titlesList.add("发票内容");
            titlesList.add("税号");
            titlesList.add("单位地址");
            titlesList.add("电话号码");
            titlesList.add("开户银行");
            titlesList.add("银行账户");
            titlesList.add("创建时间");
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "发票管理.xlsx" , excelData);
            result.setMessage("导出成功");

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("导出客户列表失败");
        }
        return result;
    }

}
