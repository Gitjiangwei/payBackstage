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

     List<Map<String,String>> queryDictItemsByCode(@Param("code") String code);

     String queryDictTextByKey(@Param("code") String code,@Param("key") String key);

     int queryDict(@Param("list") List<String> dictItemIds);

     List<Dict> queryDictDetail(Dict dict);

     int saveDict(Dict dict);

     int updateDict(Dict dict);

     int delDict(@Param("dictIds") List<String> dictIds);

     int checkOnlyDictDetail(@Param("dictCodeId") String dictCodeId,@Param("dictItemId") String dictItemId);

}
