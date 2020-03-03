package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.hero.renche.entity.Dict;

import java.util.List;
import java.util.Map;

public interface IDictService extends IService<Dict> {

    public List<Map<String,String>> queryDictItemsByCode(String code);

    public String queryDictTextByKey(String code,String key);

}
