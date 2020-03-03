package org.hero.renche.controller;

import lombok.extern.slf4j.Slf4j;
import org.hero.renche.service.IDictService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Title: Controller
 * @Description: 字典管理
 */
@RestController
@RequestMapping("/renche/dict")
@Slf4j
public class DictController {

    @Autowired
    private IDictService dictService;

    /**
     * 获取字典数据
     * @param dictCode
     * @return
     */
    @RequestMapping(value = "/getDictItems/{dictCode}", method = RequestMethod.GET)
    public Result<List<Map<String, String>>> getDictItems(@PathVariable String dictCode) {
        log.info(" dictCode : "+ dictCode);
        Result<List<Map<String,String>>> result = new Result<List<Map<String,String>>>();
        List<Map<String,String>> ls = null;
        try {
            ls = dictService.queryDictItemsByCode(dictCode);
            result.setSuccess(true);
            result.setResult(ls);
        } catch (Exception e) {
            log.info(e.getMessage());
            result.error500("操作失败");
            return result;
        }

        return result;
    }

    /**
     * 获取字典数据
     * @param dictCode
     * @return
     */
    @RequestMapping(value = "/getDictText/{dictCode}/{key}", method = RequestMethod.GET)
    public Result<String> getDictItems(@PathVariable("dictCode") String dictCode, @PathVariable("key") String key) {
        log.info(" dictCode : "+ dictCode);
        Result<String> result = new Result<String>();
        String text = null;
        try {
            text = dictService.queryDictTextByKey(dictCode, key);
            result.setSuccess(true);
            result.setResult(text);
        } catch (Exception e) {
            log.info(e.getMessage());
            result.error500("操作失败");
            return result;
        }
        return result;
    }

}
