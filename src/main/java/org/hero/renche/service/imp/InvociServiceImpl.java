package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.vo.CompanyInfoVo;
import org.hero.renche.entity.vo.InvociInfoVo;
import org.hero.renche.mapper.InvociInfoMapper;
import org.hero.renche.service.InvociService;
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
public class InvociServiceImpl extends ServiceImpl<InvociInfoMapper, InvociInfo> implements InvociService {

    @Autowired
    private InvociInfoMapper invociInfoMapper;

    @Override
    public PageInfo<InvociInfoVo> qryInvociInfo(InvociInfoVo invociInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<InvociInfoVo> invociInfos=invociInfoMapper.qryInvociInfoList(invociInfo);
        return new PageInfo<InvociInfoVo>(invociInfos);
    }

    @Override
    public boolean updateFileIds(InvociInfo invociInfo) {

        boolean flag = false;
        String checkFileIds = invociInfo.getFileRelId();
        String oldFileRelId = invociInfoMapper.qryFileIdByInvociId(invociInfo.getInvociId());
        List<String> checkFileIdList = new ArrayList<>(Arrays.asList(checkFileIds.split(",")));
        for(String checkFile : checkFileIdList){
            if(oldFileRelId.contains(checkFile)){
                oldFileRelId = oldFileRelId.replace(checkFile+",","");
            }
        }

        invociInfo.setFileRelId(oldFileRelId);
        int result = invociInfoMapper.updateFileIds(invociInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public String exportInvociInfo (Map<String, String> map, HttpServletResponse response){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            InvociInfoVo info = new InvociInfoVo();
            info.setContent(map.get("content"));
            info.setShuihao(map.get("shuihao"));
            info.setContractName(map.get("contractName"));
            info.setCompanyName(map.get("companyName"));

            List<InvociInfoVo> invociInfoList = invociInfoMapper.qryInvociInfoList(info);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            InvociInfoVo vo = null;
            for(int i = 0; i < invociInfoList.size(); i++){
                list=new ArrayList();
                vo=invociInfoList.get(i);
                list.add(vo.getContractName());
                list.add(vo.getInvociName());
                list.add(vo.getContent());
                list.add(vo.getInvociCode());
                list.add(vo.getInvociNumber());
                list.add(vo.getPrice());
                list.add(vo.getShuiMoney());
                list.add(vo.getShuiPercent());
                list.add(vo.getTotalMoney());
                list.add(vo.getCompanyName());
                list.add(vo.getShuihao());
                list.add(vo.getBank());
                list.add(vo.getBankNo());
                list.add(vo.getAddress());
                list.add(vo.getTel());
                if(vo.getInvociTime() != null){
                    list.add(sdf.format(vo.getInvociTime()));
                }else{
                    list.add("");
                }
                list.add(vo.getSignatory());
                if(vo.getSignForTime() != null){
                    list.add(sdf.format(vo.getSignForTime()));
                }else{
                    list.add("");
                }
                lists.add(list);
            }

            ExcelData excelData=new ExcelData();
            excelData.setName("发票信息");
            String[] titleColumn = {"合同名称","发票名称","发票内容","发票代码","发票号码","金额","税额","税率","开票金额","公司名称","公司税号","开户银行","银行账户","公司地址","联系电话","开票日期","签收人","签收日期"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "发票信息.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "导出成功";
    }

}
