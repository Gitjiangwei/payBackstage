package org.hero.renche.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MaterialInfo;
import org.hero.renche.mapper.MaterialInfoMapper;
import org.hero.renche.service.IMaterialInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MaterialInfoServiceImpl extends ServiceImpl<MaterialInfoMapper, MaterialInfo> implements IMaterialInfoService {
    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Override
    public PageInfo<MaterialInfo> qryMaterialInfoList(MaterialInfo materialInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<MaterialInfo> materialInfoList=materialInfoMapper.qryMaterialInfoList(materialInfo);
        return new PageInfo<MaterialInfo>(materialInfoList);
    }

    @Override
    public String getMaterialNo(String pyName) { return  materialInfoMapper.getMaterialNo(pyName); };
}
