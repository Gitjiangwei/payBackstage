package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.controller.voentity.projectStatus;
import org.hero.renche.entity.ProProgressRecord;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.modelData.ProjectItemModel;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    String exportPrjItem(Map<String, String> map, HttpServletResponse response);

    boolean updateFileIds(ProjectItemInfo projectItemInfo);

    PageInfo<ProjectItemInfo> queryItemList(String itemName,Integer pageNo,Integer pageSize);

    boolean updatePrjProgress(String prjItemId, String progress);

    boolean addProgressRecord(ProProgressRecord proProgressRecord);

    ProjectItemVo qryPrjItemById(String prjItemId);

    String qryPrjItemIdByName(String prjItemName);

}
