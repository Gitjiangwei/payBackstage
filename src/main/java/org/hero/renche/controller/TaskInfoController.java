package org.hero.renche.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.entity.*;
import org.hero.renche.entity.vo.TaskInfoVo;
import org.hero.renche.service.*;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    @Autowired
    private IDemandService demandService;

    @Autowired
    private IEquipinfoService equipinfoService;
    @Autowired
    private IPurchaseService purchaseService;
    @Autowired
    private IOutEquipService outEquipService;

//    @Autowired
//    private IProjectItemInfoService projectItemInfoService;

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
    public Result<PageInfo<TaskInfoVo>> list(TaskInfoVo taskInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<TaskInfoVo>> result = new Result<>();
        if(taskInfo.getCreateUserName() == null || "".equals(taskInfo.getCreateUserName())){
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            taskInfo.setCreateUser(sysUser.getId());
        }
        PageInfo<TaskInfoVo> taskInfoPageInfo = taskInfoService.qryTaskInfoList(taskInfo,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(taskInfoPageInfo);
        return result;
    }

    /**
     * 分页列表查询
     * @param taskInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取我的任务列表", notes = "获取我的任务列表", produces = "application/json")
    @GetMapping(value = "/qryMyTaskInfoList")
    public Result<PageInfo<TaskInfoVo>> qryMyTaskInfoList(TaskInfoVo taskInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<TaskInfoVo>> result = new Result<>();
//        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
//        taskInfo.setReceiveUser(sysUser.getId());

        PageInfo<TaskInfoVo> taskInfoPageInfo = taskInfoService.qryMyTaskInfoList(taskInfo,pageNo,pageSize);
//        result.success(sysUser.getId());
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
    public Result<TaskInfo> add(@RequestBody TaskInfo taskInfo) {
        Result<TaskInfo> result = new Result<TaskInfo>();
        try {
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            //保存任务
            taskInfo.setCreateTime(new Date());
            taskInfo.setCreateUser(sysUser.getId());
            taskInfoService.save(taskInfo);
            result.setResult(taskInfo);
            result.success("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("出现异常，操作失败");
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
        try {
            String taskId = taskInfo.getTaskId();
            TaskInfo taskInfoEntity = taskInfoService.getById(taskId);
            if (taskInfoEntity == null) {
                result.error500("未找到对应实体");
            } else {
                taskInfo.setCreateTime(taskInfoEntity.getCreateTime());
                taskInfoService.updateById(taskInfo);
                result.success("修改成功!");
                result.setResult(taskInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
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
                //删除已添加的设备
                demandService.delDemandByTaskId(id);
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
            //删除已添加的设备
            demandService.delDemandByTaskId(ids);
            this.taskInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 导出数据
     *
     * @param
     * @param response
     * @return
     */
    @ApiOperation(value = "导出数据", notes = "导出数据", produces = "application/vnd.ms-excel")
    @GetMapping(value = "/exportTaskInfo" )
    public void exportTaskInfo(@RequestParam(value = "param") String params, HttpServletResponse response){
        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }

            taskInfoService.exportTaskInfo(map, response);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

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

    /**
     * 修改是否发起/结束任务
     *
     * @return
     */
    @PostMapping(value = "editTaskStatus")
    public Result<TaskInfo> editTaskStatus(@RequestParam(name = "taskId") String taskId, @RequestParam(name = "status") String status){

        Result<TaskInfo> result=new Result<>();
        if(taskId == null || taskId.equals("")){
            result.error500("任务ID为空，请及时排除！");
        }else{
            Boolean resultOk = taskInfoService.editTaskStatus(taskId, status);
            if(resultOk){
                if(status.equals("1")){
                    result.success("发起任务成功！");
                }else{
                    result.success("成功结束任务！");
                }
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

    /**
     * 根据ID查找任务
     */
    @GetMapping(value = "/getTaskById")
    public Result<TaskInfo> getTaskById(@RequestParam(name = "taskId") String taskId){

        Result<TaskInfo> result  =new Result<>();
        try{
            if(taskId == null || taskId.equals("")){
                result.error500("任务ID为空，请及时排除！");
            }else {
                TaskInfo taskInfo = taskInfoService.getTaskById(taskId);
                if (taskInfo != null) {
                    result.setResult(taskInfo);
                    result.setSuccess(true);
                } else {
                    result.error500("加载任务失败！");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("加载任务失败！");
        }
        return  result;

    }

    /**
     * 修改设备进度
     * @param taskId
     * @return
     */
    @GetMapping(value = "/handOk")
    public Result handOk(@RequestParam(name = "taskId") String taskId) {
        Result<String> result = new Result<>();
        try{
            if (taskId == null || "".equals(taskId)) {
                result.error500("任务ID为空，操作失败！");
                return result;
            }
            Boolean isOk=taskInfoService.updateEquipStatus(taskId,1);
            if(isOk){
                result.setSuccess(true);
                return result;
            }else {
                result.error500("修改设备进度失败！");
                return result;
            }

        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("修改设备进度失败！");
            return result;
        }

    }

    /**
     * 处理任务设备，库存充足直接全部出库，
     * 库存不足，出库库存中现有数量，不足的生成采购需求
     * @param taskId
     * @return
     */
    @GetMapping(value = "/chuli")
    public Result<String> chuli(@RequestParam(name = "taskId") String taskId){
        Result<String> result=new Result<>();
        try{

            if(taskId==null || "".equals(taskId)){
                result.error500("任务ID为空，操作失败！");
                return result;
            }
            /*需要的所有设备*/
            List<Demand> DemandList=demandService.getDemangNumByTaskId(taskId);
            TaskInfo taskInfo=taskInfoService.getTaskById(taskId);
            Integer haveNum=taskInfo.getHaveNum()==null?0:taskInfo.getHaveNum();


            int flag1=0;
            int flag2=0;
            if(DemandList.size()>0){
                Demand demand=null;
                for (int i=0;i<DemandList.size();i++){
                    demand= DemandList.get(i);
                    String prjItemId=demand.getPrjItemId();
                    int needZuNinNum= demand.getNeedNumber();//需要的数量
                    String materialId= demand.getMaterialId();//物料ID
                    Integer haveWay=demand.getHaveWay();//拥有方式
                    List<EquipInfo>  equipinfolist=equipinfoService.getEquipinfo(materialId ,haveWay );//设备库存
                    int realityNum=equipinfolist.size();
                    /*只要有库存就出库*/
                    if(realityNum>0){
                        if(needZuNinNum<=realityNum){
                            //设备出库,出库数量就是需要的数量
                            for(int j=0;j<needZuNinNum;j++){
                                EquipInfo equipInfo= equipinfolist.get(j);
                               Boolean isOk= outEquipService.equipInfoOut(equipInfo.getEquipId(),prjItemId);
                               if(isOk){
                                   haveNum+=1;
                               }
                            }

                        }else {
                            //设备出库,出库数量就是库存数量
                            for(int j=0;j<realityNum;j++){
                                EquipInfo equipInfo= equipinfolist.get(j);
                                /*equipInfo.setEquipStatus("INUSE");
                                equipinfoService.updateById(equipInfo);*/
                                Boolean isOk= outEquipService.equipInfoOut(equipInfo.getEquipId(),prjItemId);
                                if(isOk){
                                    haveNum+=1;
                                }
                            }
                            //生成采购需求，需求数量是需要数量减去出库数量
                            Integer quantity=needZuNinNum-realityNum;
                            PurchaseInfo purchaseInfo=new PurchaseInfo();
                            purchaseInfo.setMaterialId(materialId);
                            purchaseInfo.setTaskId(taskId);
                            purchaseInfo.setQuantity(quantity);
                            purchaseInfo.setCreateTime(new Date());
                            purchaseInfo.setHaveWay(haveWay);
                            purchaseInfo.setRemark(demand.getRemarks());
                            purchaseService.save(purchaseInfo);
                            flag1+=1;
                        }
                    }else {
                        //生成采购需求
                        PurchaseInfo purchaseInfo=new PurchaseInfo();
                        purchaseInfo.setMaterialId(materialId);
                        purchaseInfo.setTaskId(taskId);
                        purchaseInfo.setQuantity(needZuNinNum);
                        purchaseInfo.setCreateTime(new Date());
                        purchaseInfo.setHaveWay(haveWay);
                        purchaseInfo.setRemark(demand.getRemarks());
                        purchaseService.save(purchaseInfo);
                        flag2+=1;

                    }
                }

            }else {
                result.error500("该任务需求设备为空，操作失败！");
                return result;
            }
            taskInfo.setHaveNum(haveNum);
            taskInfoService.updateById(taskInfo);

            if(flag1==0 &&flag2==0){
                taskInfoService.updateEquipStatus(taskId,3);
                result.success("处理成功，设备已全部出库！");
                return result;
            }else {
                taskInfoService.updateEquipStatus(taskId,2);
                result.success("处理成功，注意此任务有设备需求生成，请尽快处理！");
                return result;
            }


        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            return result;
        }


    }

}
