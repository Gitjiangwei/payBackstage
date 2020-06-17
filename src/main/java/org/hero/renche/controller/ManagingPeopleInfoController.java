package org.hero.renche.controller;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ManagingPeopleInfo;
import org.hero.renche.entity.vo.ManagingPeopleInfoVo;
import org.hero.renche.service.IProjectItemInfoService;
import org.hero.renche.service.ManagingPeopleInfoService;
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
 * 人员管理签收单 前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/renche/managingPeople")
@Slf4j
public class ManagingPeopleInfoController {

    @Autowired
    private ManagingPeopleInfoService managingPeopleInfoService;

    @Autowired
    private IProjectItemInfoService projectItemInfoService;


    /**
     * 分页列表查询
     * @param company
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取人员管理签收单列表", notes = "获取人员管理签收单列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<ManagingPeopleInfoVo>> list(ManagingPeopleInfoVo managingPeopleInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<ManagingPeopleInfoVo>> result = new Result<>();

        PageInfo<ManagingPeopleInfoVo> managingPeoplePageInfo = managingPeopleInfoService.qryManagingPeopleInfo(managingPeopleInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(managingPeoplePageInfo);
        return result;
    }






    /**
     * 添加
     * @param managingPeopleInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加人员管理签收单信息")
    public Result<ManagingPeopleInfo> add(@RequestBody ManagingPeopleInfoVo managingPeopleInfoVo) {
        Result<ManagingPeopleInfo> result = new Result<ManagingPeopleInfo>();
        try {
        String prjItemName= managingPeopleInfoVo.getPrjItemName();
        String prjId = projectItemInfoService.qryPrjItemIdByName(prjItemName);
        if(prjId==null && "".equals(prjId)){
            result.error500("改工程点不存在，添加失败！");
            return result;
        }
        ManagingPeopleInfo managingPeopleInfo=new ManagingPeopleInfo();
        BeanUtils.copyProperties(managingPeopleInfoVo,managingPeopleInfo);
        managingPeopleInfo.setPrjItemId(prjId);
        managingPeopleInfo.setCreateTime(new Date());


            boolean ok = managingPeopleInfoService.save(managingPeopleInfo);
            result.success("添加成功！");
            result.setResult(managingPeopleInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }


    /**
     * 编辑
     * @param managingPeopleInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<ManagingPeopleInfo> eidt(@RequestBody ManagingPeopleInfo managingPeopleInfo) {
        Result<ManagingPeopleInfo> result = new Result<ManagingPeopleInfo>();
        ManagingPeopleInfo managingPeopleInfoEntity = managingPeopleInfoService.getById(managingPeopleInfo.getManagingPeopleId());
        if (managingPeopleInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = managingPeopleInfoService.updateById(managingPeopleInfo);
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

    @PostMapping(value = "/delete")
    public Result<ManagingPeopleInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ManagingPeopleInfo> result = new Result<ManagingPeopleInfo>();

        ManagingPeopleInfo managingPeopleInfo=managingPeopleInfoService.getById(id);
        if (managingPeopleInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = managingPeopleInfoService.removeById(id);
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
    public Result<ManagingPeopleInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ManagingPeopleInfo> result = new Result<ManagingPeopleInfo>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.managingPeopleInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }


}

