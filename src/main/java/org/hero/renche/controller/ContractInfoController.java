package org.hero.renche.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.ContractInfoTransformation;
import org.hero.renche.entity.vo.ContractInfoVo;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.service.IContractInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @Title: Controller
 * @Description: 合同信息管理
 */
@RestController
@RequestMapping("/renche/contractInfo")
@Slf4j
public class ContractInfoController {

    @Autowired
    private IContractInfoService contractInfoService;

    /**
     * 分页列表查询
     * @param contractInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取合同信息列表", notes = "获取合同信息列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<ContractInfoVo>> list(ContractInfo contractInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<ContractInfoVo>> result = new Result<>();
        PageInfo<ContractInfoVo> pageList = contractInfoService.qryContractInfo(contractInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     * @param contractInfoVo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加合同信息")
    public Result<ContractInfo> add(@RequestBody ContractInfoVo contractInfoVo) {
        Result<ContractInfo> result = new Result<>();
        ContractInfoTransformation transformation = new ContractInfoTransformation();
        ContractInfo contractInfo = transformation.toPo(contractInfoVo);
        contractInfo.setCreateTime(new Date());
        try {
            boolean ok = contractInfoService.save(contractInfo);
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
     * @param contractInfoVo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<ContractInfo> eidt(@RequestBody ContractInfoVo contractInfoVo) {
        Result<ContractInfo> result = new Result<>();
        ContractInfoTransformation transformation = new ContractInfoTransformation();
        ContractInfo contractInfo = transformation.toPo(contractInfoVo);
        ContractInfo contractInfoEntity = contractInfoService.getById(contractInfo.getContractId());
        if (contractInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = contractInfoService.updateById(contractInfo);
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
    public Result<ContractInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ContractInfo> result = new Result<>();
        ContractInfo contractInfo = contractInfoService.getById(id);
        if (contractInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = contractInfoService.removeById(id);
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
    public Result<ContractInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ContractInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.contractInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 分页列表查询除已选择工程点外的所有工程点
     * @param projectItem
     * @param pageNo
     * @param pageSize
     * @param contractId
     * @returnp'r
     */
    @ApiOperation(value = "获取工程点信息列表", notes = "获取工程点信息列表", produces = "application/json")
    @GetMapping(value = "/allPrjItemWithoutContractList")
    public Result<PageInfo<ProjectItemVo>> allPrjItemWithoutContractList(ProjectItemInfo projectItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(name = "contractId") String contractId) {
        Result<PageInfo<ProjectItemVo>> result = new Result<>();
        PageInfo<ProjectItemVo> pageList = contractInfoService.qryProjectItemInfo(projectItem,pageNo,pageSize,contractId);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 分页列表查询合同关联的工程点
     * @param pageNo
     * @param pageSize
     * @param contractId
     * @returnp'r
     */
    @ApiOperation(value = "获取工程点信息列表", notes = "获取工程点信息列表", produces = "application/json")
    @GetMapping(value = "/contractPrjItemList")
    public Result<PageInfo<ProjectItemVo>> contractPrjItemList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                         @RequestParam(name = "contractId") String contractId) {
        Result<PageInfo<ProjectItemVo>> result = new Result<>();
        PageInfo<ProjectItemVo> pageList = contractInfoService.qryProItemByContractId(pageNo,pageSize,contractId);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加合同关联工程点
     *
     * @param data
     * @return
     */
    @PostMapping(value = "/addProjectItem")
    public Result<ContractInfo> addProjectItem(@RequestBody String data) {
        Result<ContractInfo> result = new Result<>();

        JSONObject jsonObject = JSONObject.parseObject(data);
        String contractId = jsonObject.getString("contractId");
        String prjItemIds = jsonObject.getString("prjItemIds");
        prjItemIds = prjItemIds.substring(0,prjItemIds.length()-1);

        boolean flag = contractInfoService.addProjectItem(contractId,prjItemIds);
        if(flag){
            result.success("添加成功!");
        }else{
            result.success("添加失败!");
        }
        return result;
    }

    /**
     * 通过id删除
     *
     * @param prjItemId
     * @param contractId
     * @return
     */
    @AutoLog(value = "删除合同关联工程点")
    @PostMapping(value = "/delPrjItem")
    public Result<ContractInfo> delPrjItem(@RequestParam(name = "prjItemId", required = true) String prjItemId,
                                           @RequestParam(name = "contractId", required = true) String contractId) {
        Result<ContractInfo> result = new Result<>();
        boolean flag = contractInfoService.delProjectItem(contractId,prjItemId);
        if(flag){
            result.success("删除成功!");
        }else{
            result.success("删除失败!");
        }
        return result;
    }

    @PostMapping(value = "/updateFileIds")
    public Result<ContractInfo> updateFileIds(@RequestParam(name = "contractId") String contractId,
                                                  @RequestParam(name = "ids") String ids){
        Result<ContractInfo> result = new Result<>();
        if(contractId == null || contractId.equals("")){
            result.error500("遇到未知异常，请及时排除！");
        }else{
            ContractInfo contractInfo = new ContractInfo();
            contractInfo.setContractId(contractId);
            contractInfo.setFileRelId(ids);
            Boolean resultOk = contractInfoService.updateFileIds(contractInfo);
            if(resultOk){
                result.success("成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

}
