package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.WorkServiceInfo;
import org.hero.renche.entity.vo.WorkServiceInfoVo;
import org.hero.renche.mapper.WorkServiceInfoMapper;
import org.hero.renche.service.WorkServiceInfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class WorkServiceInfoServiceImpl implements WorkServiceInfoService {

    @Autowired
    private WorkServiceInfoMapper workServiceInfoMapper;
    @Override
    public boolean addWorkServiceInfo(WorkServiceInfo workServiceInfo) {
        int addnum=workServiceInfoMapper.addWorkServiceInfo(workServiceInfo);
        if(addnum>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public PageInfo<WorkServiceInfoVo> qryworkServiceInfo(WorkServiceInfoVo workServiceInfoVo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<WorkServiceInfoVo> list=workServiceInfoMapper.qryworkServiceInfo(workServiceInfoVo);
        return new PageInfo<WorkServiceInfoVo>(list);
    }

    @Override
    public boolean upWorkSeriviceInfo(WorkServiceInfo workServiceInfo) {
        int num =workServiceInfoMapper.updateById(workServiceInfo);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String exportWorkServiceInfo(Map<String, String> map, HttpServletResponse response) {
        try{
            WorkServiceInfoVo workServiceInfoVo=new WorkServiceInfoVo();
            String workName=map.get("workName")==null ?"":map.get("workName");
            String companyName=map.get("companyName")==null ?"":map.get("companyName");
            String  visitor= map.get("visitor")==null?"":map.get("visitor");
            workServiceInfoVo.setWorkName(workName);
            workServiceInfoVo.setCompanyName(companyName);
            workServiceInfoVo.setVisitor(visitor);
            List<WorkServiceInfoVo> wslist=workServiceInfoMapper.qryworkServiceInfoList(workServiceInfoVo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            WorkServiceInfoVo vv=null;
            for(int i=0;i<wslist.size();i++){
                list=new ArrayList();
                vv=wslist.get(i);
                Date planTime= vv.getPlanExecuTime();
                Date  realityTime= vv.getRealityExecuTime();
                Date planOut=vv.getPlanOutTime();
                Date reaLityOut=vv.getRealityOutTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
                list.add(i+1);
                list.add(vv.getWorkName());
                list.add(vv.getCompanyName());
                list.add(vv.getPhone());
                if(planTime!=null){
                    String planExecuTime = formatter.format(planTime);
                    list.add(planExecuTime);
                }else {
                    list.add(planTime);
                }
                if(realityTime!=null){
                    String realityExecuTime=formatter.format(realityTime);
                    list.add(realityExecuTime);
                }else {
                    list.add(realityTime);
                }
                if(planOut!=null){
                    String planOutTime = formatter.format(planOut);
                    list.add(planOutTime);
                }else {
                    list.add(planOut);
                }
                if(reaLityOut!=null){
                    String reaLityOutTime=formatter.format(reaLityOut);
                    list.add(reaLityOutTime);
                }else {
                    list.add(reaLityOut);
                }
                list.add(vv.getPlanPersonNum());
                list.add(vv.getRealityPersonNum());
                list.add(vv.getContent());
                list.add(vv.getResult());
                list.add(vv.getEvaluate());
                list.add(vv.getRemark());

                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("我的工单");
            List titlesList=new ArrayList();
            titlesList.add("序号");
            titlesList.add("工单名称");
            titlesList.add("客户名称");
            titlesList.add("客户电话");
            titlesList.add("计划执行时间");
            titlesList.add("实际执行时间");
            titlesList.add("计划完成时间");
            titlesList.add("实际完成时间");
            titlesList.add("计划参与人数");
            titlesList.add("实际参与人数");
            titlesList.add("任务内容");
            titlesList.add("执行情况");
            titlesList.add("用户评价");
            titlesList.add("备注");

            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "我的工单.xlsx" , excelData);

        }catch (Exception e){
            e.printStackTrace();
            return "导出失败";
        }

        return "导出成功";
    }

    @Override
    public String qryWorkIdByWorkServiceId(String workServiceId) {
        String ID=workServiceInfoMapper.qryWorkIdByWorkServiceId(workServiceId);
        return ID;
    }
}
