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
}
