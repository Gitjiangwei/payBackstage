package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MoneyBackInfo;
import org.hero.renche.entity.vo.MoneyBackInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IMoneyBackInfoService extends IService<MoneyBackInfo> {

    PageInfo<MoneyBackInfoVo> qryBackInfoList(MoneyBackInfoVo moneyBackInfo, Integer page, Integer pageSize);

    boolean updateFileIds(MoneyBackInfo moneyBackInfo);

    void exportBackInfo(Map<String, String> map, HttpServletResponse response);

}
