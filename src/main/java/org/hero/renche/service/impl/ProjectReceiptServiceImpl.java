package org.hero.renche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ProjectReceipt;
import org.hero.renche.entity.vo.LeaseReturnVo;
import org.hero.renche.entity.vo.ProjectReceiptVo;
import org.hero.renche.mapper.ProjectReceiptMapper;
import org.hero.renche.service.ProjectReceiptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 工程验收单 服务实现类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-19
 */
@Service
public class ProjectReceiptServiceImpl extends ServiceImpl<ProjectReceiptMapper, ProjectReceipt> implements ProjectReceiptService {

    @Autowired
    private ProjectReceiptMapper projectReceiptMapper;

    @Override
    public PageInfo<ProjectReceiptVo> qryProjectReceiptVo(ProjectReceiptVo projectReceiptVo, Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo,pageSize);
        List<ProjectReceiptVo> list=projectReceiptMapper.qryProjectReceiptVo(projectReceiptVo);
        return new PageInfo<ProjectReceiptVo>(list);
    }




}
