package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.LeaseReturn;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hero.renche.entity.vo.LeaseReturnVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-02
 */
public interface LeaseReturnService extends IService<LeaseReturn> {

    /**
     * 分页查询
     * @param arrivalListVo
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo<LeaseReturnVo> qryleaseReturnVoList(LeaseReturnVo leaseReturnVo, Integer page, Integer pageSize);

    int qryleaseReturnByPrjId(String prjItemId);

}
