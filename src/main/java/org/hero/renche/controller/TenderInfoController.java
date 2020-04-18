package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.service.TenderService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

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

            if("2".equals(isBack)&&tenderInfo.getRecedeTime()!=null){
                result.error500("保证金未退回，请勿选择退保证金时间");
                return result;
            }
            if("1".equals(isBack)){
                isBack="是";
            }else if ("2".equals(isBack))
                isBack="否";
            tenderInfo.setIsBack(isBack);
            String tenderId= UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
            tenderInfo.setTenderId(tenderId);
            tenderInfo.setCreateTime(new Date());
            String payWay=tenderInfo.getPayWay();

            double deposit= Double.parseDouble(tenderInfo.getDeposit());
            double serviceMoney= Double.parseDouble(tenderInfo.getServiceMoney());

            String recedeDeposit="";

            if("1".equals(payWay)){
                recedeDeposit=deposit+"";
            }else if("2".equals(payWay)){
                double temp=deposit-serviceMoney;
                recedeDeposit=temp+"";
            }
            tenderInfo.setRecedeDeposit(recedeDeposit);
            Boolean bool=tenderService.addTender(tenderInfo);
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
            String isBack=tenderInfo.getIsBack();

            if("是".equals(isBack)){
                isBack="1";
            }else if ("否".equals(isBack)){
                isBack="2";
            }
            if("2".equals(isBack)||"否".equals(isBack)){
                if(tenderInfo.getRecedeTime()!=null){
                    result.error500("保证金未退回，请勿选择退保证金时间");
                    return result;
                }
            }
            tenderInfo.setIsBack(isBack);
            String payWay=tenderInfo.getPayWay();
            if("自缴".equals(payWay)){
                isBack="1";
            }else if ("保证金扣除".equals(payWay)){
                isBack="2";
            }
            double deposit= Double.parseDouble(tenderInfo.getDeposit());
            double serviceMoney= Double.parseDouble(tenderInfo.getServiceMoney());

            String recedeDeposit="";

            if("1".equals(payWay)){
                recedeDeposit=deposit+"";
            }else if("2".equals(payWay)){
                double temp=deposit-serviceMoney;
                recedeDeposit=temp+"";
            }
            tenderInfo.setRecedeDeposit(recedeDeposit);


            Boolean bool=tenderService.upTenderById(tenderInfo);
            if(bool==true){
                result.success("修改成功");
                result.setSuccess(true);
                result.setResult(tenderInfo);
            }else {
                result.error500("修改招标信息失败");
                return result;
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
     * @param
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
                return result;
            }
            result.success("删除成功！");
            result.setSuccess(true);

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
               return result;
           }
           result.success("批量删除成功！");
           result.setSuccess(true);

       }catch (Exception e){
           e.printStackTrace();
           log.info(e.getMessage());
           result.error500("批量删除失败");
       }
        return  result;
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
            TenderInfo tenderInfo=new TenderInfo();
            String prjName=map.get("prjName")==null?"":map.get("prjName");
            String tenderNo=map.get("tenderNo")==null?"":map.get("tenderNo");
            String tenderCompany=map.get("tenderCompany")==null?"":map.get("tenderCompany");
            tenderInfo.setPrjName(prjName);
            tenderInfo.setTenderNo(tenderNo);
            tenderInfo.setTenderCompany(tenderCompany);
            List<TenderInfo> qryList=tenderService.exportTenderInfoList(tenderInfo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            TenderInfo vv=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vv=qryList.get(i);
                Date date1= vv.getCreateTime();
                Date date2=vv.getPayTime();
                Date date3=vv.getRecedeTime();
                Date date4=vv.getPlanOutTime();
                Date date5=vv.getRealityOutTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
                list.add(i+1);
                list.add(vv.getPrjName());
                list.add(vv.getTenderNo());
                list.add(vv.getTenderCompany());
                list.add(vv.getTenderOffer());
                list.add(vv.getDeposit());
                list.add(vv.getRecedeDeposit());
                list.add(vv.getIsBack());
                if(date4!=null){
                    String planOutTime = formatter.format(date4);
                    list.add(planOutTime);
                }else {
                    list.add(date4);
                }
                if(date5!=null){
                    String realityOutTime = formatter.format(date5);
                    list.add(realityOutTime);
                }else {
                    list.add(date5);
                }
                list.add(vv.getAgency());
                list.add(vv.getPurchasePerson());
                list.add(vv.getServiceMoney());
                if(date2!=null){
                    String payTime = formatter.format(date2);
                    list.add(payTime);
                }else {
                    list.add(date2);
                }
                if(date3!=null){
                    String recedeTime=formatter.format(date3);
                    list.add(recedeTime);
                }else {
                    list.add(date3);
                }
                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("发票管理");
            List titlesList=new ArrayList();
            titlesList.add("序号");
            titlesList.add("项目名称");
            titlesList.add("招标编号");
            titlesList.add("投标单位");
            titlesList.add("报价（万元）");
            titlesList.add("保证金（万元");
            titlesList.add("应退保证金（万元");
            titlesList.add("保证金是否退回");
            titlesList.add("计划完成时间");
            titlesList.add("实际完成时间");
            titlesList.add("招标代理机构");
            titlesList.add("采购人");
            titlesList.add("服务费");
            titlesList.add("交保证金时间");
            titlesList.add("退保证时间");
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "招标管理.xlsx" , excelData);


        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }

    }


}
