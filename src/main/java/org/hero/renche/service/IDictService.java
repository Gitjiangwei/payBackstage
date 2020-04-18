package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.Dict;

import java.util.List;
import java.util.Map;

public interface IDictService extends IService<Dict> {

     List<Map<String,String>> queryDictItemsByCode(String code);

     String queryDictTextByKey(String code,String key);

    PageInfo<Dict> queryDict(Dict dict,Integer pageNo,Integer pageSize);

    /**
     * 添加数据字典值
     * @param dict
     * @return
     */
    Boolean saveDict(Dict dict);

    /**
     * 修改数据字典值
     * @param dict
     * @return
     */
    Boolean updateDict(Dict dict);

    /**
     * 单删除数据字典
     * @param dictId
     * @return
     */
    Boolean delDict(String dictId);

    /**
     * 批量删除数据字典
     * @param dictIds
     * @return
     */
    Boolean delDicts(String dictIds);

    /**
     * 校验数据字典值是否重复
     * @param dictCodeId
     * @param dictItemId
     * @return
     */
    Boolean checkOnlyDictDetail(String dictCodeId,String dictItemId);
}
