package org.hero.renche.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.service.ICompanyInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
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
@RequestMapping("/renche/companyInfo")
@Slf4j
public class CompanyInfoController {

    @Autowired
    private ICompanyInfoService companyInfoService;

    /**
     * 分页列表查询
     * @param company
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取客户信息列表", notes = "获取客户信息列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<IPage<CompanyInfo>> list(CompanyInfo company, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<CompanyInfo>> result = new Result<>();
        QueryWrapper<CompanyInfo> queryWrapper = null;

        // 手工转换实体驼峰字段为下划线分隔表字段
        queryWrapper = new QueryWrapper<CompanyInfo>(company);
        Page<CompanyInfo> page = new Page<CompanyInfo>(pageNo, pageSize);

        // 排序逻辑 处理
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        if (oConvertUtils.isNotEmpty(column) && oConvertUtils.isNotEmpty(order)) {
            if ("asc".equals(order)) {
                queryWrapper.orderByAsc(oConvertUtils.camelToUnderline(column));
            } else {
                queryWrapper.orderByDesc(oConvertUtils.camelToUnderline(column));
            }
        }
        IPage<CompanyInfo> pageList = companyInfoService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     * @param companyInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加客户信息")
    public Result<CompanyInfo> add(@RequestBody CompanyInfo companyInfo) {
        Result<CompanyInfo> result = new Result<CompanyInfo>();
        companyInfo.setCreateTime(new Date());
        try {
            boolean ok = companyInfoService.save(companyInfo);
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
     * @param jeecgDemo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<CompanyInfo> eidt(@RequestBody CompanyInfo companyInfo) {
        Result<CompanyInfo> result = new Result<CompanyInfo>();
        CompanyInfo companyInfoEntity = companyInfoService.getById(companyInfo.getCompanyId());
        if (companyInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = companyInfoService.updateById(companyInfo);
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
    public Result<CompanyInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<CompanyInfo> result = new Result<CompanyInfo>();
        CompanyInfo companyInfo = companyInfoService.getById(id);
        if (companyInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = companyInfoService.removeById(id);
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
    public Result<CompanyInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<CompanyInfo> result = new Result<CompanyInfo>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.companyInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

}
