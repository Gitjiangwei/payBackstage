package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.InvoiceInfo;

import java.util.List;

/**
 *  开票信息 Mapper 接口
 *
 */
public interface InvoiceInfoMapper extends BaseMapper<InvoiceInfo> {

    List<InvoiceInfo> qryListIncoiceInfo(@Param("InvoiceInfo") InvoiceInfo invoiceInfo);

    int updateFileIds(@Param("InvoiceInfo") InvoiceInfo invoiceInfo);
}
