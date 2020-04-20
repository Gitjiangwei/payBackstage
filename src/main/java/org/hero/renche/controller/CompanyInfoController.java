package org.hero.renche.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.entity.vo.CompanyInfoVo;
import org.hero.renche.service.ICompanyInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public Result<PageInfo<CompanyInfoVo>> list(CompanyInfo company, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<CompanyInfoVo>> result = new Result<>();

        PageInfo<CompanyInfoVo> companyInfoPageInfo = companyInfoService.qryCompanyInfo(company,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(companyInfoPageInfo);
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
     * @param companyInfo
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
    @PostMapping(value = "/delete")
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
    @PostMapping(value = "/deleteBatch")
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

    /**
     * 根据公司名称模糊查询公司
     *
     * @param name
     * @return
     */
    @AutoLog(value = "根据公司名称模糊查询公司")
    @GetMapping(value = "/searchCompany")
    public Result<PageInfo<CompanyInfo>> searchCompanyList(@RequestParam(name = "name", required = true) String name,@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<PageInfo<CompanyInfo>> result = new Result<>();

        PageInfo<CompanyInfo> companyInfoPageInfo = companyInfoService.qryCompanyNameList(name, pageNo, pageSize);
        result.setSuccess(true);
        result.setResult(companyInfoPageInfo);
        return result;
    }

    /**
     * 导出数据
     *
     * @return
     */
    @ApiOperation(value = "导出数据", notes = "导出数据", produces = "application/vnd.ms-excel")
    @RequestMapping(value = "/exportCompanyInfo" )
    public void exportCompanyInfo (@RequestParam(value = "param") String params, HttpServletResponse response) {

        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }

            String message = companyInfoService.exportCompanyInfo(map, response);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

    /**
     * 修改关联附件id
     *
     * @return
     */
    @PostMapping(value = "updateFileIds")
    public Result<CompanyInfo> updateCompanyFileIds(@RequestParam(name = "companyId") String companyId, @RequestParam(name = "ids") String ids){

        Result<CompanyInfo> result=new Result<>();
        if(companyId == null || companyId.equals("")){
            result.error500("公司ID为空，请及时排除！");
        }else{
            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setCompanyId(companyId);
            companyInfo.setFileRelId(ids);
            Boolean resultOk = companyInfoService.updateFileIds(companyInfo);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

}
