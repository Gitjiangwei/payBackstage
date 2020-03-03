package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * 字典 Mapper 接口
 *
 */
public interface DictMapper extends BaseMapper<Dict> {

    public List<Map<String,String>> queryDictItemsByCode(@Param("code") String code);

    public String queryDictTextByKey(@Param("code") String code,@Param("key") String key);

}
