package org.hero.renche.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.PurchaseInfo;

import java.util.List;

public interface PurchaseService extends IService<PurchaseInfo> {



    PageInfo<PurchaseInfo> qryPurchaseInfo(PurchaseInfo purchaseInfo, Integer page, Integer pageSize);
}
