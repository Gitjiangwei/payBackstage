package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.DictItem;

import java.util.List;

public interface IDictItemService extends IService<DictItem> {

    /**
     * 查询数据字典类型
     * @param dictItem
     * @return
     */
    PageInfo<DictItem> qryDictItem(DictItem dictItem,Integer pageNo,Integer pageSize);

    /**
     * 新增数据字典类型
     * @param dictItem
     * @return
     */
    Boolean  saveDictItem(DictItem dictItem);

    /**
     * 修改数据字典类型
     * @param dictItem
     * @return
     */
    Boolean updateDictItem(DictItem dictItem);

    /**
     * 删除数据字典类型
     * @param dictItemId
     * @return
     */
    String  delDictItem(String dictItemId);

    /**
     * 校验数据字典类型是否重复
     * @param dictItemCode
     * @return
     */
    Boolean checkOnlyDict(String dictItemCode);
}
