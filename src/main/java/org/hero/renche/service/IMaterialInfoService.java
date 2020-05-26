package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MaterialInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IMaterialInfoService extends IService<MaterialInfo> {

    PageInfo<MaterialInfo> qryMaterialInfoList(MaterialInfo materialInfo, Integer page, Integer pageSize);

    String getMaterialNo(String pyName);

}
