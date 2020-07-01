package org.hero.renche.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ProjectReceipt;
import org.hero.renche.entity.vo.ProjectReceiptVo;
import org.hero.renche.service.IProjectItemInfoService;
import org.hero.renche.service.ProjectReceiptService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 工程验收单 前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-19
 */

@RestController
@RequestMapping("/renche/projectReceipt")
@Slf4j
public class ProjectReceiptController {


    @Autowired
    private ProjectReceiptService projectReceiptService;
    @Autowired
    private IProjectItemInfoService projectItemInfoService;



    /**
     * 分页列表查询
     * @param projectItem
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取工程验收单列表", notes = "获取工程验收单列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<ProjectReceiptVo>> list(ProjectReceiptVo projectReceiptVo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<ProjectReceiptVo>> result = new Result<>();
        PageInfo<ProjectReceiptVo> pageList = projectReceiptService.qryProjectReceiptVo(projectReceiptVo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }



    /**
     * 添加
     * @param
     * @return
     */
    @ApiOperation(value = "添加工程验收单", notes = "添加工程验收单")
    @PostMapping(value = "/add")
    public Result<ProjectReceiptVo> add(@RequestBody ProjectReceiptVo projectReceiptVo) {
        Result<ProjectReceiptVo> result = new Result<ProjectReceiptVo>();
        String prjItemName = projectReceiptVo.getPrjItemName();
        String prjId = projectItemInfoService.qryPrjItemIdByName(prjItemName);
        if(prjId==null||"".equals(prjId)){
            result.error500("工程点ID为空，操作失败");
            return result;
        }
        projectReceiptVo.setPrjItemId(prjId);

        try {
           /* SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();*/
            ProjectReceipt projectReceipt=new ProjectReceipt();

            BeanUtils.copyProperties(projectReceiptVo,projectReceipt);

           Boolean ok=projectReceiptService.save(projectReceipt);
           if(!ok){
               result.error500("操作失败");
               return result;
           }
            result.setResult(projectReceiptVo);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("出现异常，操作失败");
            return  result;
        }
        return result;
    }


    /**
     * 修改工程验收单
     * @param
     * @return
     */
    @ApiOperation(value = "修改工程验收单", notes = "修改工程验收单", produces = "application/json")
    @PutMapping(value = "/edit")
    public Result<ProjectReceiptVo> edit(@RequestBody ProjectReceiptVo projectReceiptVo) {
        Result<ProjectReceiptVo> result = new Result<ProjectReceiptVo>();
        String prjItemName = projectReceiptVo.getPrjItemName();
        String prjId = projectItemInfoService.qryPrjItemIdByName(prjItemName);
        if(prjId==null||"".equals(prjId)){
            result.error500("工程点ID为空，操作失败");
            return result;
        }
        projectReceiptVo.setPrjItemId(prjId);

        try {
            /* SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();*/
            ProjectReceipt projectReceipt=new ProjectReceipt();

            BeanUtils.copyProperties(projectReceiptVo,projectReceipt);

            Boolean ok=projectReceiptService.updateById(projectReceipt);
            if(!ok){
                result.error500("操作失败");
                return result;
            }
            result.setResult(projectReceiptVo);
            result.success("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("出现异常，操作失败");
            return  result;
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
    @PostMapping(value = "/delete")
    public Result<ProjectReceipt> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ProjectReceipt> result = new Result<ProjectReceipt>();
        ProjectReceipt projectReceipt =projectReceiptService.getById(id);
        if (projectReceipt == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = projectReceiptService.removeById(id);
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
    @PostMapping(value = "/deleteBatch")
    public Result<ProjectReceipt> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ProjectReceipt> result = new Result<ProjectReceipt>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.projectReceiptService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }



}

