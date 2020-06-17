package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ManagingPeopleInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hero.renche.entity.vo.ManagingPeopleInfoVo;

/**
 * <p>
 * 人员管理签收单 服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
public interface ManagingPeopleInfoService extends IService<ManagingPeopleInfo> {


    PageInfo<ManagingPeopleInfoVo> qryManagingPeopleInfo(ManagingPeopleInfoVo managingPeopleInfo, Integer pageNo, Integer pageSize);

}
