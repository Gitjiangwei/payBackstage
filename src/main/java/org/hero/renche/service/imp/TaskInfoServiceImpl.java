package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.entity.TaskInfo;
import org.hero.renche.entity.vo.TaskInfoVo;
import org.hero.renche.mapper.TaskInfoMapper;
import org.hero.renche.service.ITaskInfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoService {

    @Autowired
    private TaskInfoMapper taskInfoMapper;


    @Override
    public PageInfo<TaskInfoVo> qryTaskInfoList(TaskInfo taskInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<TaskInfoVo> taskInfoList = taskInfoMapper.qryTaskInfoList(taskInfo);
        return new PageInfo<TaskInfoVo>(taskInfoList);
    }

    @Override
    public PageInfo<TaskInfoVo> qryMyTaskInfoList(TaskInfo taskInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<TaskInfoVo> taskInfoList = taskInfoMapper.qryMyTaskInfoList(taskInfo);
        return new PageInfo<TaskInfoVo>(taskInfoList);
    }

    @Override
    public boolean updateFileIds(TaskInfo taskInfo) {

        boolean flag = false;
        String checkFileIds = taskInfo.getFileRelId();
        String oldFileRelId = taskInfoMapper.qryFileIdByTaskId(taskInfo.getTaskId());
        List<String> checkFileIdList = new ArrayList<>(Arrays.asList(checkFileIds.split(",")));
        for(String checkFile : checkFileIdList){
            if(oldFileRelId.contains(checkFile)){
                oldFileRelId = oldFileRelId.replace(checkFile+",","");
            }
        }

        taskInfo.setFileRelId(oldFileRelId);
        int result = taskInfoMapper.updateFileIds(taskInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean editTaskStatus(String taskId, String status){
        int result = taskInfoMapper.editTaskStatus(taskId, status);
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean makeSureToMakeDemand(String taskIds){
        Boolean isFlag = false;
        List<String> ids = new ArrayList<>();
        if(taskIds.contains(",")){
            ids = new ArrayList<String>(Arrays.asList(taskIds.split(",")));
        }else {
            ids.add(taskIds);
        }

        int result = taskInfoMapper.makeSureToMakeDemand(ids);
        if(result>0){
            isFlag = true;
        }
        return isFlag;
    }

    @Override
    public void exportTaskInfo (Map<String, String> map, HttpServletResponse response){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            String mark = map.get("mark");
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setPrjItemName(map.get("prjItemName"));
            taskInfo.setTaskName(map.get("taskName"));
            taskInfo.setStatus(map.get("status"));
            if(map.get("startTime") != null){
                taskInfo.setStartTime(sdf.parse(map.get("startTime")));
            }
            if(map.get("endTime") != null){
                taskInfo.setEndTime(sdf.parse(map.get("endTime")));
            }
            List<TaskInfoVo> taskInfoList = null;
            if(mark.equals("create")){
                taskInfo.setCreateUser(map.get("createUser"));
                taskInfo.setReceiveUserName(map.get("receiveUserName"));
                taskInfoList = taskInfoMapper.qryTaskInfoList(taskInfo);
            }else if(mark.equals("query")){
                taskInfo.setReceiveUser(map.get("receiveUser"));
                taskInfo.setCreateUserName(map.get("createUserName"));
                taskInfoList = taskInfoMapper.qryMyTaskInfoList(taskInfo);
            }

            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            TaskInfoVo vo = null;
            if(taskInfoList != null){
                for(int i = 0; i < taskInfoList.size(); i++){
                    list=new ArrayList();
                    vo=taskInfoList.get(i);

                    list.add(vo.getTaskName());
                    list.add(vo.getTaskContent());
                    list.add(vo.getPrjItemName());
                    list.add(vo.getContactPerson());
                    list.add(vo.getContactTel());
                    list.add(vo.getReceiveUserName());
                    if(vo.getPlanStartTime() != null){
                        list.add(sdf.format(vo.getPlanStartTime()));
                    }else{
                        list.add("");
                    }
                    if(vo.getPlanEndTime() != null){
                        list.add(sdf.format(vo.getPlanEndTime()));
                    }else{
                        list.add("");
                    }
                    if(vo.getStartTime() != null){
                        list.add(sdf.format(vo.getStartTime()));
                    }else{
                        list.add("");
                    }
                    if(vo.getEndTime() != null){
                        list.add(sdf.format(vo.getEndTime()));
                    }else{
                        list.add("");
                    }

                    list.add(vo.getProgressOfItem());
                    if("0".equals(vo.getStatus())){
                        list.add("新建");
                    }else if("1".equals(vo.getStatus())){
                        list.add("进行中");
                    }else if("2".equals(vo.getStatus())){
                        list.add("已结束");
                    }
                    list.add(vo.getCreateUserName());
                    lists.add(list);
                }
            }

            ExcelData excelData=new ExcelData();
            excelData.setName("任务信息");
            String[] titleColumn = {"任务名称","任务内容","工程点名称","联系人","联系电话","负责人","计划开始时间","计划结束时间","实际开始时间","实际结束时间","任务进度","任务状态","创建人"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "任务信息.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
