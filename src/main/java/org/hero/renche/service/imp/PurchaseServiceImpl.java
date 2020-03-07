package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseInfoMapper,PurchaseInfo> implements IPurchaseService {

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;


    @Transactional
    @Override
    public PageInfo<PurchaseInfo> qryPurchaseInfo(PurchaseInfo purchaseInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<PurchaseInfo> purchaseInfoList = purchaseInfoMapper.qryListPurchaseInfo(purchaseInfo);
        return new PageInfo<PurchaseInfo>(purchaseInfoList);
    }

    @Override
    public int qryPurchaseId(String purchaseId) {
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setPurchaseId(purchaseId);
        return purchaseInfoMapper.qryListPurchaseInfo(purchaseInfo).size();
    }

    @Override
    public boolean updatePurchaseIds(String ids) {
        boolean flag = false;
        if (ids==null||"".equals(ids.trim())){
            return false;
        }
        List<String> purchaseIds = Arrays.asList(ids.split(","));
        int updateCount = purchaseInfoMapper.updatePurchaseByIds(purchaseIds);
        if (updateCount > 0){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }
}
