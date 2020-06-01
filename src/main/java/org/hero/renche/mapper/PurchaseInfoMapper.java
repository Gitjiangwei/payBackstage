package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.entity.vo.PurchaseInfoVo;

import java.util.List;

/**
 * 采购信息 Mapper 接口
 *
 */
@Mapper
public interface PurchaseInfoMapper extends BaseMapper<PurchaseInfo> {


    List<PurchaseInfoVo> qryListPurchaseInfo(@Param("PurchaseInfoVo") PurchaseInfoVo purchaseInfo);


    int updatePurchaseByIds(List<String> purchaseByIds);


    int insertReceiving(List<EquipInfo> equipInfoList);


    String qryPurchaseInfoKey(@Param("purchaseId") String purchaseId);

    int updatePurchaseKey(@Param("purchaseById") String purchaseById);

    int updateFileIds(@Param("purchaseInfo") PurchaseInfo purchaseInfo);

}
