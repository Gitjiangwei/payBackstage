package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.hero.renche.entity.DictItem;
import org.hero.renche.service.IDictItemService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @AutoLog("新增数据字典类型")
    @PostMapping(value = "/saveDictItem")
    public Result<DictItem> saveDictItem(@RequestBody DictItem dictItem){
        Result<DictItem> result = new Result<DictItem>();
        Boolean resultOk = dictItemService.saveDictItem(dictItem);
        if(resultOk){
            result.success("添加成功");
        }else {
            result.error500("添加失败！");
        }
        return result;
    }

    @AutoLog("修改数据字典类型")
    @PostMapping(value = "/updateDictItem")
    public Result<DictItem> updateDictItem(@RequestBody DictItem dictItem){
        Result<DictItem> result = new Result<DictItem>();
        if(dictItem.getDictItemId()==null || dictItem.getDictItemId().equals("")){
            result.error500("参数丢失！");
        }else {
            Boolean resultOk = dictItemService.updateDictItem(dictItem);
            if(resultOk){
                result.success("修改成功");
            }else {
                result.error500("修改失败！");
            }
        }
        return result;
    }

    @AutoLog("批量删除数据字典类型")
    @PostMapping(value = "/delDictItems")
    public Result<DictItem> delDictItems(@RequestParam(name = "ids") String ids){
        Result<DictItem> result = new Result<DictItem>();
        if(ids==null || ids.equals("")){
            result.error500("参数丢失！");
        }else {
            String resultOk = dictItemService.delDictItem(ids);
            if(resultOk.equals("OK")){
                result.success("删除成功");
            }else if (resultOk.equals("ERROR")){
                result.error500("删除失败！");
            }else if(resultOk.equals("NOTNULL")){
                result.error500("参数丢失！");
            }else if(resultOk.equals("CONTANS")){
                result.error500("您选择要删除的数据字典类型中包含正在使用的数据字典值，不能删除");
            }
        }
        return result;
    }

    @AutoLog("删除数据字典类型")
    @PostMapping(value = "/delDictItem")
    public Result<DictItem> delDictItem(@RequestParam(name = "id") String id){
        Result<DictItem> result = new Result<DictItem>();
        if(id==null || id.equals("")){
            result.error500("参数丢失！");
        }else {
            String resultOk = dictItemService.delDictItem(id);
            if(resultOk.equals("OK")){
                result.success("删除成功");
            }else if (resultOk.equals("ERROR")){
                result.error500("删除失败！");
            }else if(resultOk.equals("NOTNULL")){
                result.error500("参数丢失！");
            }else if(resultOk.equals("CONTANS")){
                result.error500("您要删除的数据字典类型中包含正在使用的数据字典值，不能删除");
            }
        }
        return result;
    }

    /**
     * 校验数据字典类型是否重复
     * @param dictItemCode
     * @return
     */
    @GetMapping(value = "/checkOnlyDict")
    public Result<T> checkOnlyDict(@RequestParam(name = "dictItemCode") String dictItemCode){
        Result<T> result = new Result<T>();
        Boolean resultOk = dictItemService.checkOnlyDict(dictItemCode.toUpperCase());
        if(resultOk) {
            result.setSuccess(true);
            result.setMessage("该数据字典类型编码已存在");
        }else{
            result.setSuccess(false);
            result.setMessage("");
        }
        return result;
    }
}
