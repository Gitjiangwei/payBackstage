package org.hero.renche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.LeaseReturn;
import org.hero.renche.entity.vo.LeaseReturnVo;
import org.hero.renche.mapper.LeaseReturnMapper;
import org.hero.renche.service.LeaseReturnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-02
 */
@Service
public class LeaseReturnServiceImpl extends ServiceImpl<LeaseReturnMapper, LeaseReturn> implements LeaseReturnService {


    @Autowired
    private LeaseReturnMapper leaseReturnMapper;
    @Override
    public PageInfo<LeaseReturnVo> qryleaseReturnVoList(LeaseReturnVo leaseReturnVo, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<LeaseReturnVo> leaseReturnVoList=leaseReturnMapper.qryleaseReturnVoList(leaseReturnVo);
        return new PageInfo<LeaseReturnVo>(leaseReturnVoList);

    }

    @Override
    public int qryleaseReturnByPrjId(String prjItemId) {

        int num=leaseReturnMapper.qryleaseReturnByPrjId(prjItemId);
        return num;
    }
}
