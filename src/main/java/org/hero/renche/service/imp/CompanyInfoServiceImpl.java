package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.CompanyInfoVo;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.mapper.CompanyInfoMapper;
import org.hero.renche.service.ICompanyInfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements ICompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;


    @Override
    public String qryCompanyIdByname(String CompanyName) {
        String companyId=companyInfoMapper.qryCompanyIdByname(CompanyName);
        return companyId;
    }

    @Override
    public PageInfo<CompanyInfoVo> qryCompanyInfo(CompanyInfo companyInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<CompanyInfoVo> companyInfoList = companyInfoMapper.qryListCompanyInfo(companyInfo);
        return new PageInfo<CompanyInfoVo>(companyInfoList);
    }

    @Override
    public List<String> getIds(String companyName){
        return companyInfoMapper.getIds(companyName);
    }

    @Override
    public List<Map<String, String>> qryCompanyName() {
        return companyInfoMapper.qryCompanyName();
    }

    @Override
    public PageInfo<CompanyInfo> qryCompanyNameList(String companyName, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<CompanyInfo> companyNameList = companyInfoMapper.qryCompanyNameList(companyName);
        return new PageInfo<CompanyInfo>(companyNameList);
    }

    @Override
    public int qryCompanynameById(String companyId) {
        int num=companyInfoMapper.qryCompanyById(companyId);
        return  num;
    }

    @Override
    public String exportCompanyInfo (Map<String, String> map, HttpServletResponse response){
        try {
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setCompanyName(map.get("companyName"));
            companyInfo.setShuihao(map.get("shuihao"));
            companyInfo.setType(map.get("type"));

            List<CompanyInfoVo> companyInfoList = companyInfoMapper.qryListCompanyInfo(companyInfo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            CompanyInfoVo vo = null;
            for(int i = 0; i < companyInfoList.size(); i++){
                list=new ArrayList();
                vo=companyInfoList.get(i);

                list.add(vo.getCompanyName());
                list.add(vo.getTypeName());
                list.add(vo.getShuihao());
                list.add(vo.getBank());
                list.add(vo.getBankNo());
                list.add(vo.getContacts());
                list.add(vo.getPhone());
                list.add(vo.getIdCard());
                list.add(vo.getEmail());
                list.add(vo.getIntroduc());
                list.add(vo.getAddress());
                lists.add(list);
            }

            ExcelData excelData=new ExcelData();
            excelData.setName("客户信息");
            String[] titleColumn = {"公司名称","公司类型","税号","开户银行","银行账号","联系人","联系电话","身份证号","邮箱","公司简介","公司地址"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "客户信息.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "导出成功";
    }

    @Override
    public boolean updateFileIds(CompanyInfo companyInfo) {

        boolean flag = false;
        String checkFileIds = companyInfo.getFileRelId();
        String oldFileRelId = companyInfoMapper.qryFileIdById(companyInfo.getCompanyId());
        List<String> checkFileIdList = new ArrayList<>(Arrays.asList(checkFileIds.split(",")));
        for(String checkFile : checkFileIdList){
            if(oldFileRelId.contains(checkFile)){
                oldFileRelId = oldFileRelId.replace(checkFile+",","");
            }
        }

        companyInfo.setFileRelId(oldFileRelId);
        int result = companyInfoMapper.updateFileIds(companyInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

}
