package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MoneyBackInfo;
import org.hero.renche.entity.TaskInfo;
import org.hero.renche.entity.vo.MoneyBackInfoVo;
import org.hero.renche.entity.vo.TaskInfoVo;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ITaskInfoService extends IService<TaskInfo> {

    PageInfo<TaskInfoVo> qryTaskInfoList(TaskInfo taskInfo, Integer page, Integer pageSize);

    boolean updateFileIds(TaskInfo taskInfo);

//    void exportBackInfo(Map<String, String> map, HttpServletResponse response);

}
