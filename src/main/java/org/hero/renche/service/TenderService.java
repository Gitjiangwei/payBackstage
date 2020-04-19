package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.TenderInfo;

import java.util.List;

public interface TenderService {

    PageInfo<TenderInfo> qryTenderList(TenderInfo tenderInfo,Integer pageNo,Integer pageSize);


    Boolean addTender(TenderInfo TenderInfo);

    Boolean deleteTenderInfoById(String id);

    boolean upTenderById(TenderInfo tenderInfo);

    boolean deleteBatchTenderInfo(List<String> paramIds);

    List exportTenderInfoList(TenderInfo tenderInfo);

    TenderInfo qryTenderById(String tenderId);
}
