package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ArrivalList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hero.renche.entity.vo.ArrivalListVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-05-29
 */
public interface ArrivalListService extends IService<ArrivalList> {
    /**
     * 分页查询
     * @param arrivalListVo
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo<ArrivalListVo> qryArrivalList(ArrivalListVo arrivalListVo, Integer page, Integer pageSize);

    /**
     * 根据id删除设备反馈清单
     * @param id
     * @return
     */
    Boolean delelteById(String id);

    /**
     * 根据id批量删除设备反馈清单
     * @param idlist
     * @return
     */
    Boolean delelteByIds(List idlist);

    int qryArrivalByPrjId(String prjItemId);


}
