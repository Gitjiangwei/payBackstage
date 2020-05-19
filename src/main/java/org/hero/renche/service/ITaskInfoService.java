package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.TaskInfo;
import org.hero.renche.entity.vo.TaskInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ITaskInfoService extends IService<TaskInfo> {

    PageInfo<TaskInfoVo> qryTaskInfoList(TaskInfoVo taskInfo, Integer page, Integer pageSize);

    PageInfo<TaskInfoVo> qryMyTaskInfoList(TaskInfoVo taskInfo, Integer page, Integer pageSize);

    boolean updateFileIds(TaskInfo taskInfo);

    void exportTaskInfo(Map<String, String> map, HttpServletResponse response);

    boolean editTaskStatus(String taskId, String status);

    boolean makeSureToMakeDemand(String taskIds);

}
