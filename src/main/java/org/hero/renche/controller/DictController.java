package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.hero.renche.entity.Dict;
import org.hero.renche.service.IDictService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 数据字典分页查询
     * @param dict
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog("分页查询数据字典")
    @RequestMapping(value = "/dictDateil")
    public Result<PageInfo<Dict>> queryDict(Dict dict, @RequestParam(name = "pageNo",defaultValue = "1")Integer pageNo,
                                  @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize){
        Result<PageInfo<Dict>> result = new Result<PageInfo<Dict>>();
        PageInfo<Dict> dictPageInfo = dictService.queryDict(dict,pageNo,pageSize);
        result.setResult(dictPageInfo);
        result.setSuccess(true);
        return result;
    }

    @AutoLog("添加数据字典值")
    @PostMapping(value = "/saveDict")
    public Result<T> saveDict(@RequestBody Dict dict){
        Result<T> result = new Result<T>();
        if(dict.getDictItemId()==null && "".equals(dict.getDictItemId())){
            result.error500("参数丢失！");
        }else{
            Boolean resultOk = dictService.saveDict(dict);
            if(resultOk){
                result.success("添加成功");
            }else {
                result.error500("添加失败！");
            }
        }
        return result;
    }

    @AutoLog("修改数据字典值")
    @PostMapping(value = "/updateDict")
    public Result<T> updateDict(@RequestBody Dict dict){
        Result<T> result = new Result<T>();
        if(dict.getDictId()==null || dict.getDictId().equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = dictService.updateDict(dict);
            if(resultOk){
                result.success("修改成功");
            }else {
                result.error500("修改失败！");
            }
        }
        return result;
    }


    @AutoLog("删除数据字典值")
    @PostMapping(value = "/delDict")
    public Result<T> delDict(@RequestParam(name = "id") String id){
        Result<T> result = new Result<T>();
        if(id == null || id.equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = dictService.delDict(id);
            if(resultOk){
                result.success("删除成功");
            }else {
                result.error500("删除失败！");
            }
        }
        return result;
    }


    @AutoLog("批量删除数据字典值")
    @PostMapping(value = "/delDicts")
    public Result<T> delDicts(@RequestParam(name = "ids") String ids){
        Result<T> result = new Result<T>();
        if(ids == null && ids.equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = dictService.delDicts(ids);
            if(resultOk){
                result.success("删除成功");
            }else {
                result.error500("删除失败！");
            }
        }
        return result;
    }

    /**
     * 校验数据字典值是否重复
     * @return
     */
    @GetMapping(value = "/checkOnlyDictDetail")
    public Result<T> checkOnlyDict(@RequestParam(name = "dictCodeId") String dictCodeId,
                                   @RequestParam(name = "dictItemId") String dictItemId) {
        Result<T> result = new Result<T>();
        Boolean resultOk = dictService.checkOnlyDictDetail(dictCodeId, dictItemId);
        if (resultOk) {
            result.setSuccess(true);
            result.setMessage("该数据字典值已存在");
        } else {
            result.setSuccess(false);
            result.setMessage("");
        }
        return result;
    }
}
