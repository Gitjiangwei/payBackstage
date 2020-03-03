package org.hero.renche.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

}
