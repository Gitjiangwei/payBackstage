package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.ProjectReceipt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hero.renche.entity.vo.ProjectReceiptVo;


import java.util.List;

/**
 * <p>
 * 工程验收单 Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-19
 */
public interface ProjectReceiptMapper extends BaseMapper<ProjectReceipt> {

    List<ProjectReceiptVo> qryProjectReceiptVo(@Param("ProjectReceiptVo") ProjectReceiptVo projectReceiptVo);

}
