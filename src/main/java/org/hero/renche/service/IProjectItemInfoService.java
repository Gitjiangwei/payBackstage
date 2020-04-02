package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hero.renche.controller.voentity.projectStatus;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.modelData.ProjectItemModel;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface IProjectItemInfoService extends IService<ProjectItemInfo> {

    PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize);


    /**
     * 查询工程点所使用的设备
     * @param projectItemId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<ProjectItemModel> qryProjectItemEquip(String projectItemId,Integer pageNo,Integer pageSize);

    /**
     * 查询工程点状态的数量
     * @return
     */
    List<projectStatus> qryStatusList();
    List<projectStatus> qryStatusList1();

    String importExcel(MultipartFile file);

    String exportPrjItem(ProjectItemInfo projectItemInfo, HttpServletResponse response);

}
