package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.mapper.TenderInfoMapper;
import org.hero.renche.service.ITenderInfoService;
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
public class TenderInfoServiceImpl extends ServiceImpl<TenderInfoMapper, TenderInfo> implements ITenderInfoService {
    @Autowired
    private TenderInfoMapper tenderInfoMapper;


    @Override
    public PageInfo<TenderInfo> qryTenderList(TenderInfo tenderInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TenderInfo> tenderlist=tenderInfoMapper.qryTenderList(tenderInfo);
        PageInfo<TenderInfo> pageInfo=new PageInfo<TenderInfo>(tenderlist);

        return pageInfo;
    }

    @Override
    public void exportTenderInfo (Map<String, String> map, HttpServletResponse response){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
            TenderInfo tenderInfo=new TenderInfo();
            String prjName=map.get("prjName")==null?"":map.get("prjName");
            String tenderNo=map.get("tenderNo")==null?"":map.get("tenderNo");
            String tenderCompany=map.get("tenderCompany")==null?"":map.get("tenderCompany");
            tenderInfo.setPrjName(prjName);
            tenderInfo.setTenderNo(tenderNo);
            tenderInfo.setTenderCompany(tenderCompany);
            List<TenderInfo> qryList= tenderInfoMapper.exportTenderInfoList(tenderInfo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            TenderInfo vv=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vv=qryList.get(i);
                list.add(vv.getPrjName());
                list.add(vv.getTenderNo());
                list.add(vv.getTenderCompany());
                list.add(vv.getTenderOffer());
                list.add(vv.getDeposit());
                list.add(vv.getRecedeDeposit());
                list.add(vv.getIsBack());
                if(vv.getPlanOutTime()!= null){
                    list.add(formatter.format(vv.getPlanOutTime()));
                }else{
                    list.add("");
                }
                if(vv.getRealityOutTime()!= null){
                    list.add(formatter.format(vv.getRealityOutTime()));
                }else{
                    list.add("");
                }
                list.add(vv.getAgency());
                list.add(vv.getPurchasePerson());
                list.add(vv.getServiceMoney());
                if(vv.getPayTime()!= null){
                    list.add(formatter.format(vv.getPayTime()));
                }else{
                    list.add("");
                }
                if(vv.getRecedeTime()!= null){
                    list.add(formatter.format(vv.getRecedeTime()));
                }else{
                    list.add("");
                }
                list.add(vv.getRemark());
                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("招标管理");
            String[] titleColumn = {"项目名称","招标编号","投标单位","报价","保证金","应退保证金","保证金是否退回","计划完成时间","实际完成时间","招标代理机构","采购人","服务费","交保证金时间","退保证时间","备注"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "招标管理.xlsx" , excelData);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public TenderInfo qryTenderById(String tenderId) {
        return tenderInfoMapper.qryTenderById(tenderId);
    }

    @Override
    public boolean updateFileIds(TenderInfo tenderInfo) {
        boolean flag = false;
        String chectFileIds = tenderInfo.getFileRelId();
        String oldFileRelId = tenderInfoMapper.qryFileIdsById(tenderInfo.getTenderId());
        List<String> chectFileIdList = new ArrayList<>(Arrays.asList(chectFileIds.split(",")));
        for(String items : chectFileIdList){
            oldFileRelId =  oldFileRelId.replace(items+",","");
        }

        tenderInfo.setFileRelId(oldFileRelId);
        int result = tenderInfoMapper.updateFileIds(tenderInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }
}
