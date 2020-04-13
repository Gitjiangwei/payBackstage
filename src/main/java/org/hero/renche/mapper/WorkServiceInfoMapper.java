package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.hero.renche.entity.WorkOrderInfo;
import org.hero.renche.entity.WorkServiceInfo;
import org.hero.renche.entity.vo.WorkServiceInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 工单信息 Mapper 接口
 *
 */
public interface WorkServiceInfoMapper extends BaseMapper<WorkOrderInfo> {

    int addWorkServiceInfo(@Param("WorkServiceInfo") WorkServiceInfo workServiceInfo);
    List qryworkServiceInfo(@Param("WorkServiceInfoVo") WorkServiceInfoVo workServiceInfoVo);
    int updateById(@Param("WorkServiceInfo") WorkServiceInfo workServiceInfo);


}
