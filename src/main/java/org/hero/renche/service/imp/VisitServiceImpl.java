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

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitInfoMapper visitInfoMapper;
    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    public PageInfo<VoViditInfo> qryViditInfo(VoViditInfo voViditInfo, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VoViditInfo> visitInfosList= visitInfoMapper.qryListVisitInfo(voViditInfo);
        return new PageInfo<VoViditInfo>(visitInfosList);
    }

    @Override
    public boolean addViditInfo(VisitInfo viditInfo) {
        Integer falg=visitInfoMapper.addVisitInfo(viditInfo);
        System.out.println("======falg======"+falg);
        return true;
    }

    @Override
    public boolean upViditInfo(String companyName,VisitInfo viditInfo) {
        String companyId=viditInfo.getCompanyId();
        int b=companyInfoMapper.upCompanyNameById(companyName,companyId);
        int i=visitInfoMapper.updateById(viditInfo);
        if(i>0&&i>0){
            return true;
        }else {
            return false;
        }


    }

    @Override
    public boolean deleteVisitInfoById(String visitId) {
        int i=visitInfoMapper.deleteById(visitId);
      return true;

    }
}
