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

import java.util.*;

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

    @Override
    public boolean updateFileIds(String ids, String invociId) {
        String oldfileId=invociInfoMapper.qryFileIdByInvociId(invociId);
        String[] fileIds=oldfileId.split(",");
        List<String> oldFlieRelIdList= Arrays.asList(fileIds);
        List<String> delFlieRelIdList=Arrays.asList(ids.split(","));
        String newFileRelId = "";
        LinkedList<String> result = new LinkedList<>(oldFlieRelIdList);
        HashSet<String> set = new HashSet<>(delFlieRelIdList);
        Iterator<String> itor = result.iterator();
        while(itor.hasNext()){
            if(set.contains(itor.next())){
                itor.remove();
            }
        }
        newFileRelId = Arrays.toString(result.toArray());
        if(newFileRelId != null && !newFileRelId.equals("")) {
            newFileRelId = newFileRelId.substring(1);
            newFileRelId = newFileRelId.substring(0, newFileRelId.length() - 1);
            if(!newFileRelId.equals("")) {
                char a = newFileRelId.charAt(newFileRelId.length() - 1);
                if (a == ',') {
                    newFileRelId = newFileRelId.substring(0, newFileRelId.length() - 1);
                }
            }
        }else{
            newFileRelId = "";
        }        int i= invociInfoMapper.updateFileIds(newFileRelId,invociId);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List exportVoInvoicInfoList(VoInvoicInfo voInvoicInfo) {
        List list=invociInfoMapper.exportVoInvoicInfoList(voInvoicInfo);
        return list;
    }

}
