package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.entity.vo.PurchaseInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    PageInfo<PurchaseInfoVo> qryPurchaseInfo(PurchaseInfoVo purchaseInfo, Integer page, Integer pageSize);

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
    boolean insertReceiving(PurchaseInfoVo purchaseInfo);

    /**
     * 设备入库
     * @param purchaseInfo
     * @return
     */
    List<String>  insertReceiving1(PurchaseInfoVo purchaseInfo);


    /**
     * 监听设备入库情况
     * @param purchaseId
     * @return
     */
    Boolean qryPurchaseInfoKey(String purchaseId);

    boolean updateFileIds(PurchaseInfo purchaseInfo);

    void exportPurchaseInfo(Map<String, String> map, HttpServletResponse response);
}
