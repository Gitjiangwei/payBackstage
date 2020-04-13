package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoVidit;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.VisitInfo;

import java.util.List;

public interface VisitService {

    PageInfo<VoViditInfo> qryViditInfo(VoViditInfo voViditInfo, Integer page, Integer pageSize);
    PageInfo<VoViditInfo> qryViditInfo1(List<String>workIdlist,String username, Integer page, Integer pageSize);

    boolean addViditInfo(VisitInfo viditInfo);


    boolean upViditInfo( VisitInfo viditInfo);

    boolean deleteVisitInfoById(String visitId);
    boolean removeByIds(List<String> stringList);
    int qryVisitInfoById(String visitId);

    boolean updateFileIds(String ids,String visitId);

    List qryViditInfolist(VoViditInfo voVidit);
}
