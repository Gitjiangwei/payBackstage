package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.VisitInfo;

import java.util.List;

public interface VisitService {

    PageInfo<VoViditInfo> qryViditInfo(VoViditInfo voViditInfo, Integer page, Integer pageSize);

    boolean addViditInfo(VisitInfo viditInfo);


    boolean upViditInfo( VisitInfo viditInfo);

    boolean deleteVisitInfoById(String visitId);
    boolean removeByIds(List<String> stringList);
}
