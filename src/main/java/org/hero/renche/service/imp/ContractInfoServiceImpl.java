package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ContractInfoVo;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.mapper.ContractInfoMapper;
import org.hero.renche.mapper.InvoiceInfoMapper;
import org.hero.renche.mapper.ProContractRelMapper;
import org.hero.renche.service.IContractInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class ContractInfoServiceImpl extends ServiceImpl<ContractInfoMapper, ContractInfo> implements IContractInfoService {

    @Autowired
    private ContractInfoMapper contractInfoMapper;

    @Autowired
    private ProContractRelMapper proContractRelMapper;

    @Autowired
    private InvoiceInfoMapper invoiceInfoMapper;

    @Override
    public PageInfo<ContractInfoVo> qryContractInfo(ContractInfo contractInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<ContractInfoVo> contractInfoList = contractInfoMapper.qryListContractInfo(contractInfo);
        return new PageInfo<ContractInfoVo>(contractInfoList);
    }

    @Override
    public PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize, String contractId) {

        PageHelper.startPage(page,pageSize);
        List<ProjectItemVo> projectItemList = proContractRelMapper.qryListProjectItemInfo(projectItemInfo,contractId);
        return new PageInfo<ProjectItemVo>(projectItemList);
    }

    @Override
    public PageInfo<ProjectItemVo> qryProItemByContractId(Integer page, Integer pageSize, String contractId) {

        PageHelper.startPage(page,pageSize);
        List<ProjectItemVo> projectItemList = proContractRelMapper.qryProItemByContractId(contractId);
        return new PageInfo<ProjectItemVo>(projectItemList);
    }

    @Override
    public boolean addProjectItem(String contractId, String prjItemIds){
        String[] ids = prjItemIds.split(",");

        int count = 0;
        for (String id : ids){
            int num = proContractRelMapper.addProjectItem(contractId,id);
            if(num > 0){
                count++;
            }else{
                break;
            }
        }

        if(count == ids.length){
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public boolean delProjectItem(String contractId, String prjItemId){

        int count = proContractRelMapper.delProjectItem(contractId,prjItemId);
        if(count > 0){
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

}
