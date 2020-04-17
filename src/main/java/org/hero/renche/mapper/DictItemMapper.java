package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.DictItem;

import java.util.List;

public interface DictItemMapper extends BaseMapper<DictItem> {


    List<DictItem> qryDictItem(DictItem dictItem);

    int saveDictItem(DictItem dictItem);

    int updateDictItem(DictItem dictItem);

    int delDictItem(@Param("list") List<String> dictItemIds);

    int checkOnlyDictCode(@Param("dictItemCode") String dictItemCode);

    String qryDictItemCode(@Param("dictItemId")String dictItemId);
}
