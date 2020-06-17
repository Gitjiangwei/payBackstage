package org.hero.renche.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.LeaseReturn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hero.renche.entity.vo.LeaseReturnVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-02
 */
public interface LeaseReturnMapper extends BaseMapper<LeaseReturn> {

    /**
     * 分页查询
     * @param leaseReturnVo
     * @return
     */
    List<LeaseReturnVo> qryleaseReturnVoList(@Param("LeaseReturnVo") LeaseReturnVo leaseReturnVo);

    int qryleaseReturnByPrjId(String prjItemId);

}
