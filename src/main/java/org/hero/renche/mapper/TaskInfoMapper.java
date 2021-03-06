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

    List<TaskInfoVo> qryTaskInfoList(@Param("TaskInfoVo") TaskInfoVo taskInfoVo);

    List<TaskInfoVo> qryMyTaskInfoList(@Param("TaskInfoVo") TaskInfoVo taskInfoVo);

    int updateFileIds(@Param("TaskInfo") TaskInfo taskInfo);

    String qryFileIdByTaskId(@Param("taskId") String taskId);

    int editTaskStatus(@Param("taskId") String taskId, @Param("status") String status);

    int makeSureToMakeDemand(@Param("list") List<String> taskIds);
    TaskInfo getTaskById(@Param("taskId") String taskId);

    int updateEquipStatus(@Param("taskId") String taskId,@Param("EquStatus") Integer EquStatus);
}
