package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.vo.InvociInfoVo;
import org.hero.renche.service.InvociService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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



//    /**
//     * 导出发票信息列表
//     *
//     * @param
//     * @param response
//     * @return
//     */
//    @ApiOperation(value = "导出发票信息列表", notes = "导出发票信息列表", produces = "application/json")
//    @GetMapping(value = "/exportInvoci" )
//    public Result<PageInfo<VoInvoicInfo>> exportInvoic(VoInvoicInfo voInvoicInfo , HttpServletResponse response){
//        Result<PageInfo<VoInvoicInfo>> result=new Result<>();
//
//        try{
//            List<VoInvoicInfo> qryList=invociService.exportVoInvoicInfoList(voInvoicInfo);
//            List<List<Object>> lists=new ArrayList<>();
//            List<Object> list=null;
//            VoInvoicInfo vv=null;
//            for(int i=0;i<qryList.size();i++){
//                list=new ArrayList();
//                vv=qryList.get(i);
//                Date date1= vv.getCreateTime();
//                Date date2=vv.getInvociTime();
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
//                String createTime = formatter.format(date1);
//                String invociTime = formatter.format(date2);
//                list.add(i+1);
//                list.add(vv.getInvociName());
//                list.add(invociTime);
//                list.add(vv.getContent());
//                list.add(vv.getShuihao());
//                list.add(vv.getAddress());
//                list.add(vv.getTel());
//                list.add(vv.getBank());
//                list.add(vv.getBankNo());
//                list.add(createTime);
//                lists.add(list);
//            }
//            ExcelData excelData=new ExcelData();
//            excelData.setName("发票管理");
//            List titlesList=new ArrayList();
//            titlesList.add("序号");
//            titlesList.add("发票名称");
//            titlesList.add("开票时间");
//            titlesList.add("发票内容");
//            titlesList.add("税号");
//            titlesList.add("单位地址");
//            titlesList.add("电话号码");
//            titlesList.add("开户银行");
//            titlesList.add("银行账户");
//            titlesList.add("创建时间");
//            excelData.setTitles(titlesList);
//            excelData.setRows(lists);
//            ExcelUtils.exportExcel(response , "发票管理.xlsx" , excelData);
//            result.setMessage("导出成功");
//
//        }catch (Exception e){
//            e.printStackTrace();
//            log.info(e.getMessage());
//            result.error500("导出客户列表失败");
//        }
//        return result;
//    }

}
