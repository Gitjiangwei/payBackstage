package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.PurchaseInfo;

import java.util.List;

/**
 * 采购信息 Mapper 接口
 *
 */
@Mapper
public interface PurchaseInfoMapper extends BaseMapper<PurchaseInfo> {


    List<PurchaseInfo> qryListPurchaseInfo(@Param("PurchaseInfo") PurchaseInfo purchaseInfo);
}
