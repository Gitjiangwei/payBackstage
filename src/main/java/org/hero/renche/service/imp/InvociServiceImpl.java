package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.FileRel;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.mapper.FileRelMapper;
import org.hero.renche.mapper.InvociInfoMapper;
import org.hero.renche.service.InvociService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvociServiceImpl implements InvociService {

    @Autowired
    private InvociInfoMapper invociInfoMapper;



    @Override
    public PageInfo<VoInvoicInfo> qryInvociInfo(VoInvoicInfo voInvoicInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<VoInvoicInfo> invociInfos=invociInfoMapper.qryVoInvoicInfoList(voInvoicInfo);


        return new PageInfo<VoInvoicInfo>(invociInfos);
    }

    @Override
    public boolean addInvoic(VoInvoicInfo voInvoicInfo) {

        InvociInfo invociInfo=new InvociInfo();
        BeanUtils.copyProperties(voInvoicInfo,invociInfo);
        int i=invociInfoMapper.addInvoic(invociInfo);

        if(i>0){
           return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean updateInvoic(VoInvoicInfo voInvoicInfo) {
        InvociInfo invociInfo=new InvociInfo();
        BeanUtils.copyProperties(voInvoicInfo,invociInfo);
        int i=invociInfoMapper.updateInvoicById(invociInfo);

        if(i>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean deleteById(String id) {
        int i =invociInfoMapper.deleteById(id);
        if(i>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean deleteBatInvoicInfo(List<String> idsList) {

        int i=invociInfoMapper.deleteBatchIds(idsList);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

}
