package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.DictItem;
import org.hero.renche.mapper.DictItemMapper;
import org.hero.renche.service.IDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements IDictItemService {


    @Autowired
    private DictItemMapper dictItemMapper;

    /**
     * 查询数据字典类型
     *
     * @param dictItem
     * @return
     */
    @Override
    public PageInfo<DictItem> qryDictItem(DictItem dictItem,Integer pageNo,Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<DictItem> dictItemList = dictItemMapper.qryDictItem(dictItem);
        return new PageInfo<DictItem>(dictItemList);
    }
}
