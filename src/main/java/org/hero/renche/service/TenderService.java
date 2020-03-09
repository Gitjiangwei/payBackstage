package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.TenderInfo;

public interface TenderService {

    PageInfo<TenderInfo> qryTenderList(TenderInfo tenderInfo,Integer pageNo,Integer pageSize);


    Boolean addTender(TenderInfo TenderInfo);
    Boolean deleteTenderInfoById(String id);
    boolean upTenderById(TenderInfo tenderInfo);
}
