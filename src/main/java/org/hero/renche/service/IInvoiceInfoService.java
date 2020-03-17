package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.InvoiceInfo;

public interface IInvoiceInfoService extends IService<InvoiceInfo> {

    PageInfo<InvoiceInfo> qryInvoiceByContractId(InvoiceInfo invoiceInfo, Integer page, Integer pageSize);

    boolean updateFileIds(InvoiceInfo invoiceInfo);

}
