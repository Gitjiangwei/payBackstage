package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.controller.voentity.VoViditInfo;

import java.util.List;

public interface InvociService {

    PageInfo<VoInvoicInfo> qryInvociInfo(VoInvoicInfo voInvoicInfo, Integer pageNo, Integer pageSize);
    boolean addInvoic(VoInvoicInfo voInvoicInfo);
    boolean updateInvoic(VoInvoicInfo voInvoicInfo);
    boolean deleteById(String id);
    boolean deleteBatInvoicInfo( List<String> idsList);
}
