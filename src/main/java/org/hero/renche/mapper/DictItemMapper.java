package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hero.renche.entity.DictItem;

import java.util.List;

public interface DictItemMapper extends BaseMapper<DictItem> {


    List<DictItem> qryDictItem(DictItem dictItem);
}
