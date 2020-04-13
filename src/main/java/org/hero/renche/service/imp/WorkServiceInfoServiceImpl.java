package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.WorkServiceInfo;
import org.hero.renche.entity.vo.WorkServiceInfoVo;
import org.hero.renche.mapper.WorkServiceInfoMapper;
import org.hero.renche.service.WorkServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class WorkServiceInfoServiceImpl implements WorkServiceInfoService {

    @Autowired
    private WorkServiceInfoMapper workServiceInfoMapper;
    @Override
    public boolean addWorkServiceInfo(WorkServiceInfo workServiceInfo) {
        int addnum=workServiceInfoMapper.addWorkServiceInfo(workServiceInfo);
        if(addnum>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public PageInfo<WorkServiceInfoVo> qryworkServiceInfo(WorkServiceInfoVo workServiceInfoVo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<WorkServiceInfoVo> list=workServiceInfoMapper.qryworkServiceInfo(workServiceInfoVo);
        return new PageInfo<WorkServiceInfoVo>(list);
    }

    @Override
    public boolean upWorkSeriviceInfo(WorkServiceInfo workServiceInfo) {
        int num =workServiceInfoMapper.updateById(workServiceInfo);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }
}
