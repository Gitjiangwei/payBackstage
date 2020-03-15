package org.hero.renche.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.PurchaseInfo;

import java.util.List;

/**
 * 采购设备接口
 */
public interface IPurchaseService extends IService<PurchaseInfo> {


    /**
     * 查询采购设备
     * @param purchaseInfo
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo<PurchaseInfo> qryPurchaseInfo(PurchaseInfo purchaseInfo, Integer page, Integer pageSize);

    /**
     * 根据设备id查询设备数量
     * @param purchaseId
     * @return
     */
    int qryPurchaseId(String purchaseId);

    /**
     * 批量修改采购设备
     * @param ids
     * @return
     */
    boolean updatePurchaseIds(String ids);

    /**
     * 设备入库
     * @param purchaseInfo
     * @return
     */
    boolean insertReceiving(PurchaseInfo purchaseInfo);

    /**
     * 监听设备入库情况
     * @param purchaseId
     * @return
     */
    Boolean qryPurchaseInfoKey(String purchaseId);

    /**
     * 采购设备信息修改
     * @param purchaseInfo
     * @return
     */
    boolean updatePurchaseKeys(PurchaseInfo purchaseInfo);


    String qryePurchaseId(String purchaseId);

    boolean updateFileIds(PurchaseInfo purchaseInfo);
}
