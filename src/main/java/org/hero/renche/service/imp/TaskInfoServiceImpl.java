package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.TaskInfo;
import org.hero.renche.entity.vo.TaskInfoVo;
import org.hero.renche.mapper.TaskInfoMapper;
import org.hero.renche.service.ITaskInfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
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
            TaskInfo taskInfo = new TaskInfo();
//            taskInfo.setCompanyName(map.get("companyName"));
//            taskInfo.setShuihao(map.get("shuihao"));
//            taskInfo.setType(map.get("type"));
//
//            List<taskInfoVo> taskInfoList = taskInfoMapper.qryListtaskInfo(taskInfo);
            List<List<Object>> lists=new ArrayList<>();
//            List<Object> list=null;
//            taskInfoVo vo = null;
//            for(int i = 0; i < taskInfoList.size(); i++){
//                list=new ArrayList();
//                vo=taskInfoList.get(i);
//
//                list.add(vo.getCompanyName());
//                list.add(vo.getTypeName());
//                list.add(vo.getShuihao());
//                list.add(vo.getBank());
//                list.add(vo.getBankNo());
//                list.add(vo.getContacts());
//                list.add(vo.getPhone());
//                list.add(vo.getIdCard());
//                list.add(vo.getEmail());
//                list.add(vo.getIntroduc());
//                list.add(vo.getAddress());
//                lists.add(list);
//            }

            ExcelData excelData=new ExcelData();
            excelData.setName("客户信息");
            String[] titleColumn = {"公司名称","公司类型","税号","开户银行","银行账号","联系人","联系电话","身份证号","邮箱","公司简介","公司地址"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "客户信息.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
