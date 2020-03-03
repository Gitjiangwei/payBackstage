package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hero.renche.entity.Dict;
import org.hero.renche.mapper.DictMapper;
import org.hero.renche.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictServiceImp extends ServiceImpl<DictMapper, Dict> implements IDictService{

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<Map<String, String>> queryDictItemsByCode(String code) {
        return dictMapper.queryDictItemsByCode(code);
    }

    @Override
    public String queryDictTextByKey(String code, String key) {
        return dictMapper.queryDictTextByKey(code, key);
    }
}
