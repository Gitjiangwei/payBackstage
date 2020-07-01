package org.hero.renche.service;

import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.ProjectReceipt;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hero.renche.entity.vo.ProjectReceiptVo;

/**
 * <p>
 * 工程验收单 服务类
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-19
 */
public interface ProjectReceiptService extends IService<ProjectReceipt> {

    PageInfo<ProjectReceiptVo> qryProjectReceiptVo(ProjectReceiptVo projectReceiptVo,Integer pageNo,Integer pageSize);

}
