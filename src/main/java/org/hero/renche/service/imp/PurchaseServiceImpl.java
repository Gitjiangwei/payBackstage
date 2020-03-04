package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseInfoMapper,PurchaseInfo> implements PurchaseService {

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;


    @Transactional
    @Override
    public PageInfo<PurchaseInfo> qryPurchaseInfo(PurchaseInfo purchaseInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<PurchaseInfo> purchaseInfoList = purchaseInfoMapper.qryListPurchaseInfo(purchaseInfo);
        return new PageInfo<PurchaseInfo>(purchaseInfoList);
    }
}
