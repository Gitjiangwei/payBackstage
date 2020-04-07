package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoVidit;
import org.hero.renche.controller.voentity.VoViditInfo;
import org.hero.renche.entity.VisitInfo;

import java.util.List;

/**
 * 客户拜访信息 Mapper 接口
 *
 */
public interface VisitInfoMapper extends BaseMapper<VisitInfo> {

    List<VoViditInfo> qryListVisitInfo(@Param("VoViditInfo") VoViditInfo voViditInfo);

    Integer addVisitInfo(@Param("VisitInfo") VisitInfo visitInfo);
    int  removeByIds(List<String> stringList);

    int deleteVisitInfoById(@Param("visitId") String visitId);

    int selectVisitById(@Param("visitId") String visitId);
    int updateFileIds(@Param("ids") String ids, @Param("visitId") String visitId);

    String qryFileIdByVisitId(@Param("visitId") String visitId);
    List qryViditInfolist( @Param("VoViditInfo") VoViditInfo voViditInfo);

}
