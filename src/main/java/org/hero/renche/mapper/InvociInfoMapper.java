package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoInvoicInfo;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.vo.InvociInfoVo;

import java.util.List;

/**
 * 发票信息 Mapper 接口
 *
 */
public interface InvociInfoMapper extends BaseMapper<InvociInfo> {
    List<InvociInfoVo> qryInvociInfoList(@Param("InvociInfoVo") InvociInfoVo invociInfo);

    int updateFileIds(@Param("InvociInfo") InvociInfo invociInfo);

    String qryFileIdByInvociId(@Param("invociId") String invociId);
}
