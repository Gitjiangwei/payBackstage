package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.VisitInfo;
import org.hero.renche.mapper.CompanyInfoMapper;
import org.hero.renche.mapper.VisitInfoMapper;
import org.hero.renche.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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
        List<String> oldidss= Arrays.asList(fileIds);
        List<String> newidss=Arrays.asList(ids.split(","));
        String id="";

        for(String oFileid : oldidss){
            for(String nfileId :newidss){
                if(!nfileId.equals(oFileid)){
                    id += oFileid+",";
                }
            }
        }

        char a = id.charAt(id.length() - 1);
        if(a == ','){
            id = id.substring(0,id.length() - 1);
        }

        System.out.println("///////////////id====="+id);

       int i= visitInfoMapper.updateFileIds(id,visitId);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }
}
