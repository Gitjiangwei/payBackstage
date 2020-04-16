package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MoneyBackInfo;
import org.hero.renche.entity.vo.MoneyBackInfoVo;

public interface IMoneyBackInfoService extends IService<MoneyBackInfo> {

    PageInfo<MoneyBackInfoVo> qryBackInfoByContractId(MoneyBackInfo moneyBackInfo, Integer page, Integer pageSize);

    boolean updateFileIds(MoneyBackInfo moneyBackInfo);

}
