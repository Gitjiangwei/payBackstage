package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.MoneyBackInfo;
import org.hero.renche.entity.vo.MoneyBackInfoVo;
import org.hero.renche.service.IMoneyBackInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: Controller
 * @Description: 回款信息管理
 */
@RestController
@RequestMapping("/renche/moneyBack")
@Slf4j
public class MoneyBackInfoController {

    @Autowired
    private IMoneyBackInfoService moneyBackInfoService;

    /**
     * 分页列表查询合同关联的回款信息
     * @param pageNo
     * @param pageSize
     * @returnp'r
     */
    @ApiOperation(value = "获取回款信息列表", notes = "获取回款信息列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<MoneyBackInfoVo>> list(MoneyBackInfoVo moneyBackInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<PageInfo<MoneyBackInfoVo>> result = new Result<>();
        PageInfo<MoneyBackInfoVo> pageList = moneyBackInfoService.qryBackInfoList(moneyBackInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     * @param moneyBackInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加合同信息")
    public Result<MoneyBackInfo> add(@RequestBody MoneyBackInfo moneyBackInfo) {
        Result<MoneyBackInfo> result = new Result<>();
        moneyBackInfo.setCreateTime(new Date());
        try {
            boolean ok = moneyBackInfoService.save(moneyBackInfo);
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
     * @param moneyBackInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<MoneyBackInfo> eidt(@RequestBody MoneyBackInfo moneyBackInfo) {
        Result<MoneyBackInfo> result = new Result<>();
        MoneyBackInfo moneyBackInfoEntity = moneyBackInfoService.getById(moneyBackInfo.getBackId());
        if (moneyBackInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = moneyBackInfoService.updateById(moneyBackInfo);
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
    @AutoLog(value = "删除合同信息")
    @PostMapping(value = "/delete")
    public Result<MoneyBackInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<MoneyBackInfo> result = new Result<>();
        MoneyBackInfo moneyBackInfo = moneyBackInfoService.getById(id);
        if (moneyBackInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = moneyBackInfoService.removeById(id);
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
    public Result<MoneyBackInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<MoneyBackInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.moneyBackInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    @PostMapping(value = "/updateFileIds")
    public Result<MoneyBackInfo> updateFileIds(@RequestParam(name = "backId") String backId,
                                              @RequestParam(name = "ids") String ids){
        Result<MoneyBackInfo> result = new Result<>();
        if(backId == null || backId.equals("")){
            result.error500("遇到未知异常，请及时排除！");
        }else{
            MoneyBackInfo moneyBackInfo = new MoneyBackInfo();
            moneyBackInfo.setBackId(backId);
            moneyBackInfo.setFileRelId(ids);
            Boolean resultOk = moneyBackInfoService.updateFileIds(moneyBackInfo);
            if(resultOk){
                result.success("成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

    /**
     * 导出回款信息列表
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出发票信息列表", notes = "导出发票信息列表", produces = "application/vnd.ms-excel")
    @GetMapping(value = "/exportBackInfo" )
    public void exportBackInfo(@RequestParam(value = "param") String params, HttpServletResponse response){
        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }

            moneyBackInfoService.exportBackInfo(map, response);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

}
