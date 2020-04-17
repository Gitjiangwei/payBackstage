package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.Dict;
import org.hero.renche.mapper.DictItemMapper;
import org.hero.renche.mapper.DictMapper;
import org.hero.renche.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DictServiceImp extends ServiceImpl<DictMapper, Dict> implements IDictService{

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    public List<Map<String, String>> queryDictItemsByCode(String code) {
        return dictMapper.queryDictItemsByCode(code);
    }

    @Override
    public String queryDictTextByKey(String code, String key) {
        return dictMapper.queryDictTextByKey(code, key);
    }

    @Override
    public PageInfo<Dict> queryDict(Dict dict, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Dict> dictList = dictMapper.queryDictDetail(dict);
        return new PageInfo<Dict>(dictList);
    }

    /**
     * 添加数据字典值
     *
     * @param dict
     * @return
     */
    @Override
    public Boolean saveDict(Dict dict) {
        Boolean isflag = false;
        if(dict.getDictItemId()!=null && !"".equals(dict.getDictItemId())){
            //1、根据数据字典类型id查询数据字典类型编码
            String dictItemCode = dictItemMapper.qryDictItemCode(dict.getDictItemId());
            if(dictItemCode!=null && !dictItemCode.equals("")){
                dict.setDictCode(dictItemCode);
                dict.setDictId(UUID.randomUUID().toString().replace("-",""));
                int result = dictMapper.saveDict(dict);
                if(result>0){
                    isflag = true;
                }
            }
        }
        return isflag;
    }

    /**
     * 修改数据字典值
     *
     * @param dict
     * @return
     */
    @Override
    public Boolean updateDict(Dict dict) {
        Boolean isflag = false;
        if(dict.getDictId()!=null && !dict.getDictId().equals("")){
            int result = dictMapper.updateDict(dict);
            if(result>0){
                isflag = true;
            }
        }
        return isflag;
    }

    /**
     * 单删除数据字典
     *
     * @param dictId
     * @return
     */
    @Override
    public Boolean delDict(String dictId) {
        Boolean isflag = false;
        if(dictId!=null && !dictId.equals("")) {
            List<String> dictIds = new ArrayList<String>();
            dictIds.add(dictId);
            int result = dictMapper.delDict(dictIds);
            if(result>0){
                isflag = true;
            }
        }
        return isflag;
    }

    /**
     * 批量删除数据字典
     *
     * @param dictIds
     * @return
     */
    @Override
    public Boolean delDicts(String dictIds) {
        Boolean isFlag = false;
        if(dictIds != null && !"".equals(dictIds)){
            char a = dictIds.charAt(dictIds.length()-1);
            if(a == ','){
                dictIds = dictIds.substring(0,dictIds.length()-1);
            }
            List<String> dictList = new ArrayList<String>();
            if(dictIds.contains(",")){
                dictList = new ArrayList<>(Arrays.asList(dictIds.split(",")));
            }else {
                dictList.add(dictIds);
            }
            int result = dictMapper.delDict(dictList);
            if(result > 0){
                isFlag = true;
            }
        }
        return isFlag;
    }

    /**
     * 校验数据字典值是否重复
     *
     * @param dictCodeId
     * @param dictItemId
     * @return
     */
    @Override
    public Boolean checkOnlyDictDetail(String dictCodeId, String dictItemId) {
        Boolean isflag = false;
        int result = dictMapper.checkOnlyDictDetail(dictCodeId,dictItemId);
        if(result>0){
            isflag = true;
        }
        return isflag;
    }
}
