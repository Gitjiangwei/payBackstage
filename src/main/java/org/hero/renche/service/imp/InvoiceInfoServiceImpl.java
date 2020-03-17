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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class InvoiceInfoServiceImpl extends ServiceImpl<InvoiceInfoMapper, InvoiceInfo> implements IInvoiceInfoService {

    @Autowired
    private InvoiceInfoMapper invoiceInfoMapper;

    @Override
    public PageInfo<InvoiceInfo> qryInvoiceByContractId(InvoiceInfo invoiceInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<InvoiceInfo> invoiceList = invoiceInfoMapper.qryListIncoiceInfo(invoiceInfo);
        return new PageInfo<InvoiceInfo>(invoiceList);
    }

    @Override
    public boolean updateFileIds(InvoiceInfo invoiceInfo) {
        boolean flag = false;
        String chectFileIds = invoiceInfo.getFileRelId();
        InvoiceInfo info = new InvoiceInfo();
        String oldFileRelId = "";
        info.setInvoiceId(invoiceInfo.getInvoiceId());
        List<InvoiceInfo> invoiceInfoList = invoiceInfoMapper.qryListIncoiceInfo(info);
        for(InvoiceInfo item : invoiceInfoList){
            oldFileRelId = item.getFileRelId();
        }
        List<String> chectFileIdList = new ArrayList<>(Arrays.asList(chectFileIds.split(",")));
        for(String items : chectFileIdList){
            oldFileRelId =  oldFileRelId.replace(items+",","");
        }

        invoiceInfo.setFileRelId(oldFileRelId);
        int result = invoiceInfoMapper.updateFileIds(invoiceInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

}
