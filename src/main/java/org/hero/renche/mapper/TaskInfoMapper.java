package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.TaskInfo;
import org.hero.renche.entity.vo.TaskInfoVo;

import java.util.List;

/**
 * 消息 Mapper 接口
 *
 */
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

    List<TaskInfoVo> qryTaskInfoList(@Param("TaskInfo") TaskInfo taskInfo);

    int updateFileIds(@Param("TaskInfo") TaskInfo taskInfo);

    String qryFileIdByTaskId(@Param("taskId") String taskId);
}
