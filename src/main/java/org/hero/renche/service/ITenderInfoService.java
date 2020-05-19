package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.TenderInfo;
import org.hero.renche.entity.vo.TenderInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ITenderInfoService extends IService<TenderInfo> {

    PageInfo<TenderInfoVo> qryTenderList(TenderInfo tenderInfo, Integer pageNo, Integer pageSize);

    void exportTenderInfo(Map<String, String> map, HttpServletResponse response);

    TenderInfo qryTenderById(String tenderId);

    boolean updateFileIds(TenderInfo tenderInfo);
}
