package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.vo.ContractInfoTransformation;
import org.hero.renche.entity.vo.ContractInfoVo;
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
 * @Description: 客户信息管理
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
        if(contractInfo.getContractName() != null && !"".equals(contractInfo.getContractName())){
            contractInfo.setContractName("*%" + contractInfo.getContractName() + "%*");
        }
        if(contractInfo.getContractNoA() != null && !"".equals(contractInfo.getContractNoA())){
            contractInfo.setContractNoA("*%" + contractInfo.getContractNoA() + "%*");
        }
        if(contractInfo.getContractNoB() != null && !"".equals(contractInfo.getContractNoB())){
            contractInfo.setContractNoB("*%" + contractInfo.getContractNoB() + "%*");
        }
        if(contractInfo.getPartyA() != null && !"".equals(contractInfo.getPartyA())){
            contractInfo.setPartyA("*%" + contractInfo.getPartyA() + "%*");
        }
        if(contractInfo.getPartyB() != null && !"".equals(contractInfo.getPartyB())){
            contractInfo.setPartyB("*%" + contractInfo.getPartyB() + "%*");
        }
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
    @DeleteMapping(value = "/delete")
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
    @DeleteMapping(value = "/deleteBatch")
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

}
