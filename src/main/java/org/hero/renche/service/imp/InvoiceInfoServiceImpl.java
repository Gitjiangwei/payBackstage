package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.InvoiceInfo;
import org.hero.renche.mapper.InvoiceInfoMapper;
import org.hero.renche.service.IInvoiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class InvoiceInfoServiceImpl extends ServiceImpl<InvoiceInfoMapper, InvoiceInfo> implements IInvoiceInfoService {

    @Autowired
    private InvoiceInfoMapper invoiceInfoMapper;

    @Override
    public PageInfo<InvoiceInfo> qryInvoiceByContractId(Integer page, Integer pageSize, String contractId) {

        PageHelper.startPage(page,pageSize);
        List<InvoiceInfo> invoiceList = invoiceInfoMapper.qryListIncoiceInfo(contractId);
        return new PageInfo<InvoiceInfo>(invoiceList);
    }

}
