package org.hero.renche.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.ArrivalList;
import org.hero.renche.entity.vo.ArrivalListVo;
import org.hero.renche.service.ArrivalListService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-05-29
 */
@RestController
@RequestMapping("/renche/arrivalList")
@Slf4j
public class ArrivalListController {

    @Autowired
    private ArrivalListService arrivalListService;

    /**
     * 分页列表查询
     */
    @ApiOperation(value = "获取设备反馈清单列表", notes = "获取设备反馈清单列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<ArrivalListVo>> list(ArrivalListVo arrivalListVo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<ArrivalListVo>> result = new Result<>();
            try{
                PageInfo<ArrivalListVo> arrivalListPageInfo = arrivalListService.qryArrivalList(arrivalListVo,pageNo,pageSize);
                result.setResult(arrivalListPageInfo);
                result.setSuccess(true);

            }catch (Exception e){
                e.printStackTrace();
                log.info(e.getMessage());
                result.error500("获取列表失败");
            }

        return result;
    }

    /**
     * 编辑设备到货清单
     * @param arrivalListVo
     * @return
     */
    @PutMapping(value = "edit")
    public Result<ArrivalListVo> edit(@RequestBody  ArrivalListVo arrivalListVo){
        Result<ArrivalListVo> result =new Result<>();

        try{
            ArrivalList arrivalList=new ArrivalList();
            BeanUtils.copyProperties(arrivalListVo,arrivalList);
            boolean  isOk=  arrivalListService.updateById(arrivalList);
            if(isOk){
                result.success("更新成功！");
            }else {
                result.error500("更新失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("更新失败！");
        }

        return result;
    }

    /**
     * 根据ID删除设备到货清单
     */
    @PostMapping(value = "delete")
    public Result<String> delArrival( @RequestParam(name = "id", required = true)  String id){
        Result<String> result=new Result<>();
        try{
            boolean delOk= arrivalListService.delelteById(id);

            if(delOk){
                result.success("删除成功！");
            }else {
                result.error500("删除失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("删除失败！");
        }

    return result;

    }

    /**
     * 根据ID批量删除设备到货清单
     */
    @PostMapping(value="deleteBatch")
    public Result<String> delBathArrval(@RequestParam(name = "ids" , required = true) String ids){

        Result<String> result=new Result<>();
       List idlist= Arrays.asList(ids.split(","));
        boolean delOk= arrivalListService.delelteByIds(idlist);

       return result;
    }


}

