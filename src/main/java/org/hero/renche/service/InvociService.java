package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.InvociInfo;
import org.hero.renche.entity.vo.InvociInfoVo;

public interface InvociService  extends IService<InvociInfo>  {

    PageInfo<InvociInfoVo> qryInvociInfo(InvociInfoVo invociInfo, Integer pageNo, Integer pageSize);
    boolean updateFileIds(InvociInfo invociInfo);
}
