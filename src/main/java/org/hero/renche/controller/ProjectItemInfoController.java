package org.hero.renche.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.controller.voentity.projectStatus;
import org.hero.renche.entity.ProProgressRecord;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.modelData.ProjectItemModel;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.service.IProjectItemInfoService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 客户信息管理
 */
@RestController
@RequestMapping("/renche/projectItem")
@Slf4j
public class ProjectItemInfoController {

    @Autowired
    private IProjectItemInfoService projectItemInfoService;

    /**
     * 分页列表查询
     * @param projectItem
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取工程点信息列表", notes = "获取工程点信息列表", produces = "application/json")
    @GetMapping(value = "/list")
    public Result<PageInfo<ProjectItemVo>> list(ProjectItemInfo projectItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<ProjectItemVo>> result = new Result<>();
        PageInfo<ProjectItemVo> pageList = projectItemInfoService.qryProjectItemInfo(projectItem,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    /**
     * 分页列表查询
     * @param projectItem
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "获取工程点信息列表", notes = "获取工程点信息列表", produces = "application/json")
    @GetMapping(value = "/list1")
    public Result<PageInfo<ProjectItemVo>> list1(ProjectItemInfo projectItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfo<ProjectItemVo>> result = new Result<>();
        PageInfo<ProjectItemVo> pageList = projectItemInfoService.qryProjectItemInfo1(projectItem,pageNo,pageSize);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    /**
     * 根据id查询工程点信息
     * @param prjItemId
     * @return
     */
    @ApiOperation(value = "根据id查询招标信息", notes = "根据id查询招标信息", produces = "application/json")
    @GetMapping(value = "/qryPrjItemById")
    public Result<ProjectItemVo> qryPrjItemById(@RequestParam(name = "prjItemId") String prjItemId) {
        Result<ProjectItemVo> result = new Result<>();
        ProjectItemVo info = projectItemInfoService.qryPrjItemById(prjItemId);
        result.setSuccess(true);
        result.setResult(info);
        return result;
    }
    /**
     * 添加
     * @param projectItemVo
     * @return
     */
    @PostMapping(value = "/add")
    @AutoLog(value = "添加工程点信息")
    public Result<ProjectItemInfo> add(@RequestBody ProjectItemVo projectItemVo) {
        Result<ProjectItemInfo> result = new Result<>();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        ProjectItemInfo projectItemInfo = new ProjectItemInfo();
        BeanUtils.copyProperties(projectItemVo, projectItemInfo);
        projectItemInfo.setHasConnection("0");
        projectItemInfo.setCreateTime(new Date());
        projectItemInfo.setCreateUser(sysUser.getId());
        projectItemInfo.setCreateUserName(sysUser.getRealname());
        try {
            boolean ok = projectItemInfoService.save(projectItemInfo);
            String progressOfItem = projectItemInfo.getProgressOfItem();
            if(progressOfItem != null && !"".equals(progressOfItem)){
                ProProgressRecord proProgressRecord = new ProProgressRecord();
                proProgressRecord.setPrjItemId(projectItemInfo.getPrjItemId());
                proProgressRecord.setProgressOfItem(progressOfItem);
                proProgressRecord.setIsUse("1");//默认使用
                proProgressRecord.setCreateUser(sysUser.getId());
                proProgressRecord.setCreateUserName(sysUser.getRealname());
                projectItemInfoService.addProgressRecord(proProgressRecord);
            }
            result.success("添加成功！");
            result.setResult(projectItemInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     * @param projectItemVo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<ProjectItemInfo> eidt(@RequestBody ProjectItemVo projectItemVo) {
        Result<ProjectItemInfo> result = new Result<>();
        ProjectItemInfo projectItemInfo = new ProjectItemInfo();
        BeanUtils.copyProperties(projectItemVo, projectItemInfo);
        ProjectItemInfo projectItemInfoEntity = projectItemInfoService.getById(projectItemInfo.getPrjItemId());
        if (projectItemInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = projectItemInfoService.updateById(projectItemInfo);

            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            String progressOfItem = projectItemInfo.getProgressOfItem();
            if(progressOfItem != null && !"".equals(progressOfItem)){
                ProProgressRecord proProgressRecord = new ProProgressRecord();
                proProgressRecord.setPrjItemId(projectItemInfo.getPrjItemId());
                proProgressRecord.setProgressOfItem(progressOfItem);
                proProgressRecord.setIsUse("1");//默认使用
                proProgressRecord.setCreateUser(sysUser.getId());
                proProgressRecord.setCreateUserName(sysUser.getRealname());
                projectItemInfoService.addProgressRecord(proProgressRecord);
            }
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
    @AutoLog(value = "删除工程点信息")
    @PostMapping(value = "/delete")
    public Result<ProjectItemInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ProjectItemInfo> result = new Result<>();
        ProjectItemInfo projectItemInfo = projectItemInfoService.getById(id);
        if (projectItemInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = projectItemInfoService.removeById(id);
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
    public Result<ProjectItemInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ProjectItemInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.projectItemInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }



    @AutoLog("查询工程点使用的设备")
    @GetMapping (value = "/projectIdList")
    public Result<PageInfo<ProjectItemModel>> qryProjectItemEquip(@RequestParam(name = "projectId") String projectId,
                                                                  @RequestParam(name = "pageNo") Integer pageNo,
                                                                  @RequestParam(name = "pageSize") Integer pageSize){
        Result<PageInfo<ProjectItemModel>> result = new Result<>();
        String projectItemId = projectId==null?"":projectId;
        PageInfo<ProjectItemModel> projectItemModelPageInfo = projectItemInfoService.qryProjectItemEquip(projectItemId,pageNo,pageSize);
        result.setResult(projectItemModelPageInfo);
        result.setSuccess(true);
        return result;
    }

    @AutoLog("查询人员车辆工程点状态数量")
    @GetMapping(value = "/qryStatus")
    public Result<List<projectStatus>> qryStatus(){
        Result<List<projectStatus>> result=new Result<>();

        try{
            List<projectStatus> statusList=projectItemInfoService.qryStatusList();
            result.setResult(statusList);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.setMessage("查询失败");
        }

        return result;

    }
    @AutoLog("查询4G视频工程点状态数量")
    @GetMapping(value = "/qryStatus1")
    public Result<List<projectStatus>> qryStatus1(){
        Result<List<projectStatus>> result=new Result<>();

        try{
            List<projectStatus> statusList=projectItemInfoService.qryStatusList1();
            result.setResult(statusList);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.setMessage("查询失败");
        }

        return result;

    }

    /**
     * 下载导入模板
     *
     * @param response
     * @return
     */
    @ApiOperation(value = "下载导入模板", notes = "下载导入模板", produces = "application/json")
    @GetMapping(value = "/exportPrjItemModel" )
    public void exportPrjItemModel (HttpServletResponse response,HttpServletRequest request){
        try{
            String fileName = "prjItemExecl.xls";
            // 设置要下载的文件的名称
            response.setHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            //获取文件的路径
//            String filePath = getClass().getResource("/execl/" + fileName).getPath();
            String path = this.getClass().getResource("").getPath();
            path = path.substring(1,path.length()-1);
            path = path.substring(0,path.lastIndexOf('/'))+"/util/execl/"+fileName;

            FileInputStream input = new FileInputStream(new File(path.replace("/","\\")));
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            //修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
            response.setHeader("Content-Length", String.valueOf(input.getChannel().size()));
            input.close();
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
    }

    /**
     * 导入数据
     *
     * @param response
     * @return
     */
    @ApiOperation(value = "导入数据", notes = "导入数据", produces = "application/json")
    @PostMapping(value = "/importPrjItem" )
    public Result<ProjectItemInfo> importPrjItem (@RequestParam MultipartFile file, HttpServletResponse response, HttpServletRequest request){
        Result<ProjectItemInfo> result=new Result<>();
        try{
            String message = projectItemInfoService.importExcel(file);
            result.setMessage(message);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            result.error500("导入数据失败");
        }
        return result;
    }

    /**
     * 导出数据
     *
     * @return
     */
    @ApiOperation(value = "导出数据", notes = "导出数据", produces = "application/vnd.ms-excel")
    @RequestMapping(value = "/exportPrjItem" )
    public void exportPrjItem (@RequestParam(value = "param") String params, HttpServletResponse response)  {
        try{
            params = params.replace("\"","");
            String[] paramStrs = params.split(",");
            Map<String,String> map = new HashMap<>();
            for (String str : paramStrs){
                String[] content = str.split(":");
                map.put(content[0],content[1]);
            }

            projectItemInfoService.exportPrjItem(map, response);
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
    public Result<ProjectItemInfo> updatePrjItemFileIds(@RequestParam(name = "prjItemId") String prjItemId, @RequestParam(name = "ids") String ids){

        Result<ProjectItemInfo> result=new Result<>();
        if(prjItemId == null || prjItemId.equals("")){
            result.error500("工程点ID为空，请及时排除！");
        }else{
            ProjectItemInfo projectItemInfo = new ProjectItemInfo();
            projectItemInfo.setPrjItemId(prjItemId);
            projectItemInfo.setFileRelId(ids);
            Boolean resultOk = projectItemInfoService.updateFileIds(projectItemInfo);
            if(resultOk){
                result.success("删除成功！");
            }else {
                result.error500("遇到未知异常，请及时排除！");
            }
        }
        return result;
    }

    /**
     * 根据工程点名称模糊查询工程点
     */
    @RequestMapping(value = "/queryItemList")
    public Result<PageInfo<ProjectItemInfo>> queryItemList(@RequestParam(name = "prjItemName") String prjItemName, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "30") Integer pageSize) {
        Result<PageInfo<ProjectItemInfo>> result = new Result<>();
        PageInfo<ProjectItemInfo> naList = projectItemInfoService.queryItemList(prjItemName,pageNo,pageSize);
        result.setResult(naList);
        result.setSuccess(true);
        return result;
    }

}
