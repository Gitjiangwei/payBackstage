package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ContractInfoVo;
import org.hero.renche.entity.vo.InvociInfoVo;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.mapper.ContractInfoMapper;
import org.hero.renche.mapper.MoneyBackInfoMapper;
import org.hero.renche.mapper.ProContractRelMapper;
import org.hero.renche.mapper.ProjectItemInfoMapper;
import org.hero.renche.service.IContractInfoService;
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

@Transactional
@Service
public class ContractInfoServiceImpl extends ServiceImpl<ContractInfoMapper, ContractInfo> implements IContractInfoService {

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    private ProjectItemInfoMapper projectItemInfoMapper;

    @Autowired
    private ProContractRelMapper proContractRelMapper;

    @Autowired
    private MoneyBackInfoMapper moneyBackInfoMapper;

    @Override
    public PageInfo<ContractInfoVo> qryContractInfo(ContractInfo contractInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<ContractInfoVo> contractInfoList = contractInfoMapper.qryListContractInfo(contractInfo);
        return new PageInfo<ContractInfoVo>(contractInfoList);
    }

    @Override
    public PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<ProjectItemVo> projectItemList = projectItemInfoMapper.qryListProjectItemInfo(projectItemInfo);
        return new PageInfo<ProjectItemVo>(projectItemList);
    }

    @Override
    public PageInfo<ProjectItemVo> qryProItemByContractId(Integer page, Integer pageSize, String contractId) {

        PageHelper.startPage(page,pageSize);
        List<ProjectItemVo> projectItemList = proContractRelMapper.qryProItemByContractId(contractId);
        return new PageInfo<ProjectItemVo>(projectItemList);
    }

    @Override
    public ContractInfoVo qryContractById(String contractId) {
        return contractInfoMapper.qryContractById(contractId);
    }

    @Override
    public boolean addProjectItem(String contractId, String prjItemIds){
        try {
            String[] ids = prjItemIds.split(",");
            int count = 0;
            for (String id : ids){
                int num = proContractRelMapper.addProjectItem(contractId,id);
                if(num > 0){
                    count++;
                    projectItemInfoMapper.updateConnWithContract(id, "1");//1有关联
                }else{
                    break;
                }
            }

            if(count == ids.length){
                return true;
            }else{
                return  false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delProjectItem(String contractId, String prjItemId){

        int count = proContractRelMapper.delProjectItem(contractId,prjItemId);
        if(count > 0){
            projectItemInfoMapper.updateConnWithContract(prjItemId, "0");//0没关联
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public boolean updateFileIds(ContractInfo contractInfo) {
        boolean flag = false;
        String checkFileIds = contractInfo.getFileRelId();
        ContractInfo pur = new ContractInfo();
        String oldFileRelId = "";
        String oldElecFileRel = "";
        pur.setContractId(contractInfo.getContractId());
        List<ContractInfoVo> contractInfoList = contractInfoMapper.qryListContractInfo(pur);
        for(ContractInfoVo item : contractInfoList){
            oldFileRelId = item.getFileRelId();
            oldElecFileRel = item.getElecFileRel();
        }
        List<String> checkFileIdList = new ArrayList<>(Arrays.asList(checkFileIds.split(",")));
        for(String checkFile : checkFileIdList){
            if(oldFileRelId.contains(checkFile)){
                oldFileRelId = oldFileRelId.replace(checkFile+",","");
            }else{
                oldElecFileRel = oldElecFileRel.replace(checkFile+",","");
            }
        }

        contractInfo.setFileRelId(oldFileRelId);
        contractInfo.setElecFileRel(oldElecFileRel);
        int result = contractInfoMapper.updateFileIds(contractInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public void exportContractInfo (Map<String, String> map, HttpServletResponse response){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            ContractInfo info = new ContractInfo();
            info.setContractName(map.get("contractName"));
            info.setContractType(map.get("contractType"));
            info.setPartyA(map.get("partyA"));
            info.setPartyB(map.get("partyB"));
            info.setContractStatus(map.get("contractStatus"));
            info.setContractNoA(map.get("contractNoA"));
            info.setContractNoB(map.get("contractNoB"));

            List<ContractInfoVo> contractInfoList = contractInfoMapper.qryListContractInfo(info);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            ContractInfoVo vo = null;
            for(int i = 0; i < contractInfoList.size(); i++){
                list=new ArrayList();
                vo=contractInfoList.get(i);
                list.add(vo.getContractName());
                list.add(vo.getPrjName());
                list.add(vo.getCompanyNameA());
                list.add(vo.getContractNoA());
                list.add(vo.getCompanyNameB());
                list.add(vo.getContractNoB());
                list.add(vo.getContractTypeName());
                list.add(vo.getContractMoney());
                if(!"".equals(vo.getContractStatus())){
                    if("0".equals(vo.getContractStatus())){
                        list.add("已结束");
                    }else if("1".equals(vo.getContractStatus())){
                        list.add("已签订");
                    }else if("2".equals(vo.getContractStatus())){
                        list.add("未签订");
                    }
                }
                if(vo.getSigningTime() != null){
                    list.add(sdf.format(vo.getSigningTime()));
                }else{
                    list.add("");
                }
                if(vo.getOverTime() != null){
                    list.add(sdf.format(vo.getOverTime()));
                }else{
                    list.add("");
                }
                if(vo.getSignInTime() != null){
                    list.add(sdf.format(vo.getSignInTime()));
                }else{
                    list.add("");
                }
                list.add(vo.getOperatorA());
                list.add(vo.getOperatorB());
                list.add(vo.getAllInvociMoney());
                list.add(vo.getInvociMoneyPercent());
                list.add(vo.getAllReturnMoney());
                list.add(vo.getReturnMoneyPercent());
                list.add(vo.getRemark());
                lists.add(list);
            }

            ExcelData excelData=new ExcelData();
            excelData.setName("合同信息");
            String[] titleColumn = {"合同名称","招标项目","甲方公司","甲方合同编号","乙方公司","乙方合同编号","合同类型","合同金额","合同状态","签订日期","结束日期","签收日期","甲方经办人","乙方经办人","发票金额","发票占比","回款金额","回款占比","备注"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "合同信息.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
