package org.hero.renche.entity.vo;

import org.hero.renche.entity.ContractInfo;

public class ContractInfoTransformation {

    public ContractInfo toPo(ContractInfoVo vo){
        ContractInfo po = new ContractInfo();
        po.setContractId(vo.getContractId());
        po.setContractName(vo.getContractName());
        po.setContractMoney(vo.getContractMoney());
        po.setContractNoA(vo.getContractNoA());
        po.setContractNoB(vo.getContractNoB());
        po.setContractStatus(vo.getContractStatus());
        po.setContractType(vo.getContractType());
        po.setPartyA(vo.getPartyA());
        po.setPartyB(vo.getPartyB());
        po.setRemindPeriodType(vo.getRemindPeriodType());
        po.setRemindPeriod(vo.getRemindPeriod());
        po.setSignInTime(vo.getSignInTime());
        po.setRemark(vo.getRemark());
        po.setFileRelId(vo.getFileRelId());
        po.setElecFileRel(vo.getElecFileRel());
        po.setTenderId(vo.getTenderId());
        return  po;
    }

    public ContractInfoVo toVo(ContractInfo po){
        ContractInfoVo vo = new ContractInfoVo();
        vo.setContractId(po.getContractId());
        vo.setContractName(po.getContractName());
        vo.setContractMoney(po.getContractMoney());
        vo.setContractNoA(po.getContractNoA());
        vo.setContractNoB(po.getContractNoB());
        vo.setContractStatus(po.getContractStatus());
        vo.setContractType(po.getContractType());
        vo.setPartyA(po.getPartyA());
        vo.setPartyB(po.getPartyB());
        po.setRemindPeriodType(vo.getRemindPeriodType());
        vo.setRemindPeriod(po.getRemindPeriod());
        vo.setSignInTime(po.getSignInTime());
        vo.setRemark(po.getRemark());
        vo.setFileRelId(po.getFileRelId());
        vo.setElecFileRel(po.getElecFileRel());
        vo.setTenderId(po.getTenderId());
        return  vo;
    }

}
