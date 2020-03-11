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
        int i=visitInfoMapper.deleteById(visitId);
      return true;

    }

    @Override
    public boolean removeByIds(List<String> stringList) {
        int i=visitInfoMapper.removeByIds(stringList);
        System.out.println("==============iiiii=========="+i);
        if(i>0){
            return true;
        }else {
            return false;
        }

    }
}
