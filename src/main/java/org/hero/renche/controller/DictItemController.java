package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.DictItem;
import org.hero.renche.service.IDictItemService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/renche/dictitem")
public class DictItemController {

    @Autowired
    private IDictItemService dictItemService;

    @AutoLog("查询数据字典类型")
    @RequestMapping(value = "/qryDictItem")
    public Result<PageInfo<DictItem>> qryDictItem(DictItem dictItem, @RequestParam(name = "pageNo") Integer pageNo,
                                                  @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<DictItem>> result = new Result<PageInfo<DictItem>>();
        PageInfo<DictItem> pageInfo = dictItemService.qryDictItem(dictItem,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageInfo);
        return result;

    }
}
