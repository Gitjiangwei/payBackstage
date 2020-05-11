package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hero.renche.controller.voentity.projectStatus;
import org.hero.renche.entity.CompanyInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.modelData.ProjectItemModel;
import org.hero.renche.entity.vo.ProjectItemVo;
import org.hero.renche.mapper.CompanyInfoMapper;
import org.hero.renche.mapper.DictMapper;
import org.hero.renche.mapper.ProjectItemInfoMapper;
import org.hero.renche.service.IProjectItemInfoService;
import org.hero.renche.util.CMPOIDao;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.hero.renche.utils.ContactUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Service
@Transactional
public class ProjectItemInfoServiceImpl extends ServiceImpl<ProjectItemInfoMapper, ProjectItemInfo> implements IProjectItemInfoService {

    @Autowired
    private ProjectItemInfoMapper projectItemInfoMapper;
    @Autowired
    private CompanyInfoMapper companyInfoMapper;
    @Autowired
    private DictMapper dictMapper;

    @Override
    public PageInfo<ProjectItemVo> qryProjectItemInfo(ProjectItemInfo projectItemInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<ProjectItemVo> projectItemInfoList = projectItemInfoMapper.qryListProjectItemInfo(projectItemInfo);
        return new PageInfo<ProjectItemVo>(projectItemInfoList);
    }

    @Override
    public PageInfo<ProjectItemModel> qryProjectItemEquip(String projectItemId, Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo,pageSize);
        List<ProjectItemModel> projectItemModelList = projectItemInfoMapper.qryProjectEquip(projectItemId);

        return new PageInfo<ProjectItemModel>(projectItemModelList);
    }

    @Override
    public List<projectStatus> qryStatusList() {
        List<projectStatus> list= projectItemInfoMapper.qryStatusList();

        return list;
    }

    @Override
    public List<projectStatus> qryStatusList1() {
        List<projectStatus> list= projectItemInfoMapper.qryStatusList1();

        return list;
    }

    // 验证模板
    private boolean checkxlExlTitle(HSSFSheet sheet) {
        String[] titleColumn = {"工程编号","工程点名称","项目名称","工程类型","所属公司","负责人","联系电话","工程状态","进场时间","要求部署时间","完成时间","工程进度","工程地址"};
        String line;
        for (int i = 0; i < titleColumn.length; i++) {
            line = CMPOIDao.getCellValue(sheet, 0, i) == null ? "" : CMPOIDao
                    .getCellValue(sheet, 0, i).toString();
            if (!titleColumn[i].equals(line)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String importExcel(MultipartFile file){
        String message = "";
        try {
            POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum() + 1;
            //验证模板
            boolean check = checkxlExlTitle(sheet);
            if(check){
                Object obj = null;
                StringBuffer sb = new StringBuffer();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                Map<String, String> map = null;
                //查询字典
                List<Map<String, String>> itemTypeDict = dictMapper.queryDictItemsByCode("PROJECTITEMTYPE");
                //读取数据
                ProjectItemInfo info;
                for (int rowNum = 1; rowNum < rowCount; rowNum++) {
                    int index=0;
                    info = new ProjectItemInfo();
                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//工程编号
                    if (obj != null && obj.toString().length() > 30){
                        message = "第" + (rowNum + 1) + "行工程编号超长，最大长度为30，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        info.setPrjItemNum(obj.toString().trim());
                    }


                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//工程点名称
                    String itemName = obj.toString().trim();
                    if(itemName != null && !"".equals(itemName)){
                        if (obj != null && obj.toString().length() > 150){
                            message = "第" + (rowNum + 1) + "行工程点名称超长，最大长度为150，" + "成功导入："+(rowNum-1)+"条数据。";
                            break;
                        }else {
                            info.setPrjItemName(itemName);
                        }
                    }else{
                        message = "第" + (rowNum + 1) + "行工程点名称不能为空，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//项目名称
                    if (obj != null && obj.toString().length() > 150){
                        message = "第" + (rowNum + 1) + "行项目名称超长，最大长度为150，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        info.setPrjName(obj.toString().trim());
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//工程类型
                    String itemType = obj.toString();
                    if(itemType != null && !"".equals(itemType)){
                        for(int i = 0; i < itemTypeDict.size(); i++){
                            map = itemTypeDict.get(i);
                            if(map.get("text").equals(itemType)){
                                info.setPrjItemType(map.get("value"));
                            }
                        }
                    }else{
                        message = "第" + (rowNum + 1) + "行工程类型不能为空，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//所属公司
                    String company = obj.toString().trim();
                    if (company != null && !"".equals(company)){
                        if(obj.toString().length() > 30){
                            message = "第" + (rowNum + 1) + "行所属公司超长，最大长度为30，" + "成功导入："+(rowNum-1)+"条数据。";
                            break;
                        }else{
                            String companyId = companyInfoMapper.qryCompanyIdByname(company);
                            if (companyId == null || "".equals(companyId)){
                                message = "第" + (rowNum + 1) + "行所属公司不存在，" + "成功导入："+(rowNum-1)+"条数据。";
                                break;
                            }else {
                                info.setBelongCompany(companyId);
                            }
                        }
                    }else {
                        info.setBelongCompany(company);
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//负责人
                    if (obj != null && obj.toString().length() > 20){
                        message = "第" + (rowNum + 1) + "行负责人超长，最大长度为30，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        info.setPersonInCharge(obj.toString().trim());
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//联系电话
                    if (obj != null && !ContactUtils.checkTel(obj.toString())){
                        message = "第" + (rowNum + 1) + "行联系电话格式错误，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        info.setPersonTel(obj.toString().trim());
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//工程状态
                    String itemStatus = obj.toString();
                    if(itemStatus != null && !"".equals(itemStatus)){
                        if(itemStatus.equals("未开始")){
                            info.setPrjItemStatus("0");
                        }else if(itemStatus.equals("进行中")){
                            info.setPrjItemStatus("1");
                        }else{
                            info.setPrjItemStatus("2");//已结束
                        }
                    }else{
                        message = "第" + (rowNum + 1) + "行工程状态不能为空，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//进场时间
                    if (obj != null && !"".equals(obj.toString()) && !ContactUtils.checkData(obj.toString())){
                        message = "第" + (rowNum + 1) + "行进场时间格式错误，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        if(!"".equals(obj.toString())){
                            Date time = sdf.parse(obj.toString());
                            info.setEntryTime(time);
                        }
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//要求部署时间
                    if (obj != null && !"".equals(obj.toString()) && !ContactUtils.checkData(obj.toString())){
                        message = "第" + (rowNum + 1) + "行要求部署时间格式错误，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        if(!"".equals(obj.toString())){
                            Date time = sdf.parse(obj.toString());
                            info.setRequireDeployTime(time);
                        }
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//完成时间
                    if (obj != null && !"".equals(obj.toString()) && !ContactUtils.checkData(obj.toString())){
                        message = "第" + (rowNum + 1) + "行完成时间格式错误，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        if(!"".equals(obj.toString())){
                            Date time = sdf.parse(obj.toString());
                            info.setFinishTime(time);
                        }
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//工程进度
                    if (obj != null && obj.toString().length() > 250){
                        message = "第" + (rowNum + 1) + "行工程进度超长，最大长度为250，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        info.setPersonInCharge(obj.toString());
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//工程地址
                    if (obj != null && obj.toString().length() > 250){
                        message = "第" + (rowNum + 1) + "行工程地址超长，最大长度为250，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        info.setPersonInCharge(obj.toString());
                    }

                    obj = CMPOIDao.getValue(sheet, rowNum, index++);//备注
                    if (obj != null && obj.toString().length() > 1500){
                        message = "第" + (rowNum + 1) + "行备注超长，最大长度为1500，" + "成功导入："+(rowNum-1)+"条数据。";
                        break;
                    }else {
                        info.setPersonInCharge(obj.toString());
                    }

                    info.setCreateTime(new Date());
                    this.save(info);
                }
            }else{
                message = "请使用正确模板导入数据";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public String exportPrjItem (Map<String, String> map, HttpServletResponse response){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            ProjectItemInfo projectItem = new ProjectItemInfo();
            projectItem.setPrjItemName(map.get("prjItemName"));
            projectItem.setPrjItemStatus(map.get("prjItemStatus"));
            projectItem.setPrjName(map.get("prjName"));
            projectItem.setPrjItemType(map.get("prjItemType"));
            projectItem.setBelongCompany(map.get("belongCompany"));
            projectItem.setPrjItemNum(map.get("prjItemNum"));
            projectItem.setPersonInCharge(map.get("personInCharge"));
            projectItem.setProgressOfItem(map.get("progressOfItem"));
            if(map.get("entryTime") != null){
                projectItem.setEntryTime(sdf.parse(map.get("entryTime")));
            }
            if(map.get("finishTime") != null){
                projectItem.setFinishTime(sdf.parse(map.get("finishTime")));
            }
            if(map.get("requireDeployTime") != null){
                projectItem.setRequireDeployTime(sdf.parse(map.get("requireDeployTime")));
            }

            List<ProjectItemVo> projectItemInfoList = projectItemInfoMapper.qryListProjectItemInfo(projectItem);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            ProjectItemVo vo = null;
            for(int i = 0; i < projectItemInfoList.size(); i++){
                list=new ArrayList();
                vo=projectItemInfoList.get(i);

                list.add(vo.getPrjItemNum());
                list.add(vo.getPrjItemName());
                list.add(vo.getPrjName());
                list.add(vo.getItemTypeName());
                list.add(vo.getCompanyName());
                list.add(vo.getPersonInCharge());
                list.add(vo.getPersonTel());
                if("0".equals(vo.getPrjItemStatus())){
                    list.add("未开始");
                }else if("1".equals(vo.getPrjItemStatus())){
                    list.add("进行中");
                }else if("2".equals(vo.getPrjItemStatus())){
                    list.add("已结束");
                }
                if(vo.getEntryTime() != null){
                    list.add(sdf.format(vo.getEntryTime()));
                }else{
                    list.add("");
                }
                if(vo.getRequireDeployTime() != null){
                    list.add(sdf.format(vo.getRequireDeployTime()));
                }else{
                    list.add("");
                }
                if(vo.getFinishTime() != null){
                    list.add(sdf.format(vo.getFinishTime()));
                }else{
                    list.add("");
                }
                list.add(vo.getProgressOfItem());
                list.add(vo.getPrjItemPlace());
                list.add(vo.getRemark());
                lists.add(list);

            }

            ExcelData excelData=new ExcelData();
            excelData.setName("工程点信息");
            String[] titleColumn = {"工程编号","工程点名称","项目名称","工程类型","所属公司","负责人","联系电话","工程状态","进场时间","要求部署时间","完成时间","工程进度","工程地址","备注"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "工程点信息.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "导出成功";
    }

    @Override
    public boolean updateFileIds(ProjectItemInfo projectItemInfo) {

        boolean flag = false;
        String checkFileIds = projectItemInfo.getFileRelId();
        String oldFileRelId = projectItemInfoMapper.qryFileIdById(projectItemInfo.getPrjItemId());
        List<String> checkFileIdList = new ArrayList<>(Arrays.asList(checkFileIds.split(",")));
        for(String checkFile : checkFileIdList){
            if(oldFileRelId.contains(checkFile)){
                oldFileRelId = oldFileRelId.replace(checkFile+",","");
            }
        }

        projectItemInfo.setFileRelId(oldFileRelId);
        int result = projectItemInfoMapper.updateFileIds(projectItemInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public PageInfo<ProjectItemInfo> queryItemList(String itemName,Integer pageNo,Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<ProjectItemInfo> projectItemInfoList = projectItemInfoMapper.qryProjectItemInfoList(itemName);
        return new PageInfo<ProjectItemInfo>(projectItemInfoList);
    }

}
