package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hero.renche.entity.TaskInfo;
import org.hero.renche.entity.vo.TaskInfoVo;
import org.hero.renche.service.ITaskInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @Title: Controller
 * @Description: 任务信息管理
 */
@RestController
@RequestMapping("/renche/taskInfo")
@Slf4j
public class TaskInfoController {

    @Autowired
    private ITaskInfoService taskInfoService;

    /**
     * 分页列表查询
     * @param taskInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取任务信息列表", notes = "获取任务信息列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<TaskInfoVo>> list(TaskInfo taskInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<TaskInfoVo>> result = new Result<>();

        PageInfo<TaskInfoVo> taskInfoPageInfo = taskInfoService.qryTaskInfoList(taskInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(taskInfoPageInfo);
        return result;
    }

    /**
     * 添加
     * @param taskInfo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加客户信息")
    public Result<TaskInfo> add(@RequestBody TaskInfo taskInfo) {
        Result<TaskInfo> result = new Result<TaskInfo>();
        taskInfo.setStatus("1");//进行中
        taskInfo.setCreateTime(new Date());
        try {
            boolean ok = taskInfoService.save(taskInfo);
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
     * @param taskInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<TaskInfo> eidt(@RequestBody TaskInfo taskInfo) {
        Result<TaskInfo> result = new Result<TaskInfo>();
        TaskInfo taskInfoEntity = taskInfoService.getById(taskInfo.getTaskId());
        if (taskInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = taskInfoService.updateById(taskInfo);
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
    public Result<TaskInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<TaskInfo> result = new Result<TaskInfo>();
        TaskInfo taskInfo = taskInfoService.getById(id);
        if (taskInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = taskInfoService.removeById(id);
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
    public Result<TaskInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<TaskInfo> result = new Result<TaskInfo>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.taskInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

//    /**
//     * 导出数据
//     *
//     * @return
//     */
//    @ApiOperation(value = "导出数据", notes = "导出数据", produces = "application/vnd.ms-excel")
//    @RequestMapping(value = "/exportCompanyInfo" )
//    public void exportCompanyInfo (@RequestParam(value = "param") String params, HttpServletResponse response) {
//
//        try{
//            params = params.replace("\"","");
//            String[] paramStrs = params.split(",");
//            Map<String,String> map = new HashMap<>();
//            for (String str : paramStrs){
//                String[] content = str.split(":");
//                map.put(content[0],content[1]);
//            }
//
//            String message = taskInfoService.exportCompanyInfo(map, response);
//        }catch (Exception e){
//            e.printStackTrace();
//            log.info(e.getMessage());
//        }
//    }

    /**
     * 修改关联附件id
     *
     * @return
     */
    @PostMapping(value = "updateFileIds")
    public Result<TaskInfo> updateTaskFileIds(@RequestParam(name = "taskId") String taskId, @RequestParam(name = "ids") String ids){

        Result<TaskInfo> result=new Result<>();
        if(taskId == null || taskId.equals("")){
            result.error500("任务ID为空，请及时排除！");
        }else{
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskId(taskId);
            taskInfo.setFileRelId(ids);
            Boolean resultOk = taskInfoService.updateFileIds(taskInfo);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

}
