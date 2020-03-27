package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoVidit;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.mapper.CompanyInfoMapper;
import org.hero.renche.mapper.VisitInfoMapper;
import org.hero.renche.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitInfoMapper visitInfoMapper;
    @Autowired
    private CompanyInfoMapper companyInfoMapper;
    private String visitId;

    @Override
    public PageInfo<VoViditInfo> qryViditInfo(VoViditInfo voViditInfo, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VoViditInfo> visitInfosList= visitInfoMapper.qryListVisitInfo(voViditInfo);
        return new PageInfo<VoViditInfo>(visitInfosList);
    }

    @Override
    public boolean addViditInfo(VisitInfo viditInfo) {
        Integer falg=visitInfoMapper.addVisitInfo(viditInfo);
        return true;
    }

    @Override
    public boolean upViditInfo(VisitInfo viditInfo) {
        int i=visitInfoMapper.updateById(viditInfo);
        if(i>0){
            return true;
        }else {
            return false;
        }

        }

    @Override
    public boolean deleteVisitInfoById(String visitId) {
        this.visitId = visitId;
        int i=visitInfoMapper.deleteVisitInfoById(visitId);
        if(i>0){
            return true;
        }else {
           return false;
        }


    }

    @Override
    public boolean removeByIds(List<String> stringList) {
        int i=visitInfoMapper.removeByIds(stringList);

        if(i>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public int qryVisitInfoById(String visitId) {
        int i=visitInfoMapper.selectVisitById(visitId);
        return i;
    }

    @Override
    public boolean updateFileIds(String ids,String visitId) {
        String oldfileId=visitInfoMapper.qryFileIdByVisitId(visitId);
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
        }

       int i= visitInfoMapper.updateFileIds(newFileRelId,visitId);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List qryViditInfolist(VoViditInfo voVidit) {

        List exList=visitInfoMapper.qryViditInfolist(voVidit);
        return exList;
    }
}
