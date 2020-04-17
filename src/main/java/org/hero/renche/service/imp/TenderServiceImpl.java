package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.Wrapper;
import org.apache.catalina.webresources.WarResource;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.mapper.TenderInfoMapper;
import org.hero.renche.service.TenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TenderServiceImpl implements TenderService {
    @Autowired
    private TenderInfoMapper tenderInfoMapper;


    @Override
    public PageInfo<TenderInfo> qryTenderList(TenderInfo tenderInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TenderInfo> tenderlist=tenderInfoMapper.qryTenderList(tenderInfo);
        PageInfo<TenderInfo> pageInfo=new PageInfo<TenderInfo>(tenderlist);

        return pageInfo;
    }

    @Override
    public Boolean addTender(TenderInfo tenderInfo) {
        int i=tenderInfoMapper.addTenser(tenderInfo);
        if(i>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public Boolean deleteTenderInfoById(String id) {

        int i=tenderInfoMapper.deleteById(id);
        if(i>0){
           return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean upTenderById(TenderInfo tenderInfo) {
        int i=tenderInfoMapper.updateTenderInfo(tenderInfo);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteBatchTenderInfo(List<String> paramIds) {
       int i= tenderInfoMapper.deleteBatchTenderInfo(paramIds);

        if(i==paramIds.size()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List exportTenderInfoList(TenderInfo tenderInfo) {
        List list=tenderInfoMapper.exportTenderInfoList(tenderInfo);
        return list;
    }
}
