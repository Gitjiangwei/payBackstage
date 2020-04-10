package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.DictItem;
import org.hero.renche.mapper.DictItemMapper;
import org.hero.renche.mapper.DictMapper;
import org.hero.renche.service.IDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements IDictItemService {


    @Autowired
    private DictItemMapper dictItemMapper;

    @Autowired
    private DictMapper dictMapper;

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

    /**
     * 新增数据字典类型
     * @param dictItem
     * @return
     */
    @Override
    public Boolean saveDictItem(DictItem dictItem) {
        Boolean isFlag = false;
        dictItem.setDictItemId(UUID.randomUUID().toString().replace("-",""));
        dictItem.setDictItemCode(dictItem.getDictItemCode().toUpperCase());
        int result = dictItemMapper.saveDictItem(dictItem);
        if(result>0){
            isFlag = true;
        }
        return isFlag;
    }

    /**
     * 修改数据字典类型
     * @param dictItem
     * @return
     */
    @Override
    public Boolean updateDictItem(DictItem dictItem) {
        Boolean isFlag = false;
        if(dictItem.getDictItemId() == null || dictItem.getDictItemId().equals("")){
            return false;
        }
        int result = dictItemMapper.updateDictItem(dictItem);
        if(result>0){
            isFlag = true;
        }
        return isFlag;
    }

    /**
     * 删除数据字典类型
     * @param dictItemId
     * @return
     */
    @Override
    public String delDictItem(String dictItemId) {
        String result = "ERROR";
        if(dictItemId==null || dictItemId.equals("")){
            return "NOTNULL";
        }else {
            char a = dictItemId.charAt(dictItemId.length() - 1);
            if (a == ',') {
                dictItemId = dictItemId.substring(0, dictItemId.length() - 1);
            }
            List<String> ids = new ArrayList<String>();
            if(dictItemId.contains(",")){
                ids = new ArrayList<String>(Arrays.asList(dictItemId.split(",")));
            }else{
                ids.add(dictItemId);
            }
            //检索要删除的字典类型下面有没有数据字典内容，如果没有的话才可以删除数据字典类型
            int countContains = dictMapper.queryDict(ids);
            if(countContains>0){
                result = "CONTANS";
            }else{
                int resultOk = dictItemMapper.delDictItem(ids);
                if(resultOk>0){
                    result = "OK";
                }
            }
        }

        return result;
    }

    /**
     * 校验数据字典类型是否重复
     * @param dictItemCode
     * @return
     */
    @Override
    public Boolean checkOnlyDict(String dictItemCode) {
        Boolean isflag = false;
        int result = dictItemMapper.checkOnlyDictCode(dictItemCode);
        if(result>0){
            isflag = true;
        }
        return isflag;
    }
}
