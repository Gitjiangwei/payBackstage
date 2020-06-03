package org.hero.renche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ArrivalList;
import org.hero.renche.entity.vo.ArrivalListVo;
import org.hero.renche.mapper.ArrivalListMapper;
import org.hero.renche.mapper.DemandMapper;
import org.hero.renche.service.ArrivalListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-05-29
 */
@Service
@Transactional
public class ArrivalListServiceImpl extends ServiceImpl<ArrivalListMapper, ArrivalList> implements ArrivalListService {
    @Autowired
    private ArrivalListMapper arrivalListMapper;
    @Autowired
    private DemandMapper demandMapper;

    @Override
    public PageInfo<ArrivalListVo> qryArrivalList(ArrivalListVo arrivalListVo, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ArrivalListVo> arrivalInfoList = arrivalListMapper.qryListArrivalListVo(arrivalListVo);
        return new PageInfo<ArrivalListVo>(arrivalInfoList);
    }

    @Override
    public Boolean delelteById(String id) {


       int num1= arrivalListMapper.deleteByPrjItemId(id);
        int num2 = demandMapper.deleteByPriItemId(id);
        if(num1>0&&num2>0){
            return true;
        }else {
            return false;
        }


    }

    @Override
    public Boolean delelteByIds(List idlist) {
        int num1= arrivalListMapper.deleteByPrjItemIds(idlist);
        int num2 = demandMapper.deleteByPriItemIds(idlist);
        if(num1>0&&num2>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public int qryArrivalByPrjId(String prjItemId) {

        int num=arrivalListMapper.qryArrivalByPrjId(prjItemId);
        return num;
    }
}