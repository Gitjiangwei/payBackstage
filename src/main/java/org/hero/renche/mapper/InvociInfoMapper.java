package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.entity.InvociInfo;

import java.util.List;

/**
 * 发票信息 Mapper 接口
 *
 */
public interface InvociInfoMapper extends BaseMapper<InvociInfo> {
    List<VoInvoicInfo> qryVoInvoicInfoList( @Param("VoInvoicInfo") VoInvoicInfo voInvoicInfo);
    int addInvoic(@Param("InvociInfo") InvociInfo invoicInfo);
    int updateInvoicById(@Param("InvociInfo") InvociInfo invociInfo);
//
}
