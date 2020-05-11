package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.ProProgressRecord;

import java.util.List;

/**
 * 工程进度记录表 Mapper 接口
 *
 */
public interface ProProgressRecordMapper  extends BaseMapper<ProProgressRecord> {

    List<ProProgressRecord> qryProgressRecord(@Param("prjItemId") String prjItemId);

    int addProgressRecord(ProProgressRecord proProgressRecord);

}
