package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ProjectItemTransformation;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.service.ICompanyInfoService;
import org.hero.renche.service.IProjectItemInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @Title: Controller
 * @Description: 客户信息管理
 */
@RestController
@RequestMapping("/renche/projectItem")
@Slf4j
public class ProjectItemInfoController {

    @Autowired
    private IProjectItemInfoService projectItemInfoService;

    @Autowired
    private ICompanyInfoService companyInfoService;

    /**
     * 分页列表查询
     * @param projectItem
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取工程点信息列表", notes = "获取工程点信息列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<ProjectItemVo>> list(ProjectItemInfo projectItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<ProjectItemVo>> result = new Result<>();
        if(projectItem.getPrjItemName() != null && !"".equals(projectItem.getPrjItemName())){
            projectItem.setPrjItemName("*%" + projectItem.getPrjItemName() + "%*");
        }
        if(projectItem.getPrjName() != null && !"".equals(projectItem.getPrjName())){
            projectItem.setPrjName("*%" + projectItem.getPrjName() + "%*");
        }
        if(projectItem.getBelongCompany() != null && !"".equals(projectItem.getBelongCompany())){
            projectItem.setBelongCompany("*%" + projectItem.getBelongCompany() + "%*");
        }
        if(projectItem.getPrjItemNum() != null && !"".equals(projectItem.getPrjItemNum())){
            projectItem.setPrjItemNum("*%" + projectItem.getPrjItemNum() + "%*");
        }
        if(projectItem.getPersonInCharge() != null && !"".equals(projectItem.getPersonInCharge())){
            projectItem.setPersonInCharge("*%" + projectItem.getPersonInCharge() + "%*");
        }
        if(projectItem.getProgressOfItem() != null && !"".equals(projectItem.getProgressOfItem())){
            projectItem.setProgressOfItem("*%" + projectItem.getProgressOfItem() + "%*");
        }
        PageInfo<ProjectItemVo> pageList = projectItemInfoService.qryProjectItemInfo(projectItem,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     * @param projectItemInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加客户信息")
    public Result<ProjectItemInfo> add(@RequestBody ProjectItemVo projectItemVo) {
        Result<ProjectItemInfo> result = new Result<>();
        ProjectItemTransformation transformation = new ProjectItemTransformation();
        ProjectItemInfo projectItemInfo = transformation.toPo(projectItemVo);
        projectItemInfo.setCreateTime(new Date());
        try {
            boolean ok = projectItemInfoService.save(projectItemInfo);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     * @param projectItemInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<ProjectItemInfo> eidt(@RequestBody ProjectItemInfo projectItemInfo) {
        Result<ProjectItemInfo> result = new Result<>();
        ProjectItemInfo projectItemInfoEntity = projectItemInfoService.getById(projectItemInfo.getPrjItemId());
        if (projectItemInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = projectItemInfoService.updateById(projectItemInfo);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "删除客户信息")
    @DeleteMapping(value = "/delete")
    public Result<ProjectItemInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ProjectItemInfo> result = new Result<>();
        ProjectItemInfo projectItemInfo = projectItemInfoService.getById(id);
        if (projectItemInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = projectItemInfoService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<ProjectItemInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ProjectItemInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.projectItemInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

}
