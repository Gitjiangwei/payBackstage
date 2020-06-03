package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.hero.renche.entity.Demand;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.entity.ProjectItemInfo;
import org.hero.renche.entity.vo.DemandVo;
import org.hero.renche.mapper.DemandMapper;
import org.hero.renche.mapper.ProjectItemInfoMapper;
import org.hero.renche.service.IDemandService;
import org.hero.renche.service.IMessageInfoService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements IDemandService {

    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private ProjectItemInfoMapper projectItemInfoMapper;
    @Autowired
    private IMessageInfoService iMessageInfoService;

    @Autowired
    private SysUserMapper sysUserMapper;



   // private SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();

    /**
     * 修改设备需求状态
     *
     * @param demandId,IsSendKey
     * @return
     */
    @Override
    public Boolean updateIsSendKey(String demandId,String IsSendKey) {

        Boolean isFlag = false;
        if(demandId!=null && !demandId.equals("")){
            Demand demand = new Demand();
            demand.setDemandId(demandId);
//            int result = demandMapper.updateDemand(demand);
//            if (result > 0){
                if(IsSendKey.equals("2")){
                    int resultTime = demandMapper.updateWhetherTime(demandId);
                    if(resultTime>0){
                        isFlag = true;
                    }
                }else {
                    isFlag = true;
                }
//            }
        }
        return isFlag;
    }

    /**
     * 通知工程人员领料
     * */
    @Override
    public Boolean AdviceStatus(String demandId,  String status , String taskId) {
        Boolean isFlag = false;
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String userName="";
        if(sysUser!=null){
            userName=sysUser.getUsername();
        }
        if(demandId!=null && !demandId.equals("")){
            Demand demand = new Demand();
            demand.setStatus(status);
            demand.setDemandId(demandId);
            Demand demand1=demandMapper.selectById(demandId);
            ProjectItemInfo projectItemInfo=null;
            String createName="";
            String createNameId="";
            String mesFrom="";
            SysUser sysUser1=null;
            if(demand1!=null){
                String prjItemId=demand1.getPrjItemId();
//                createName=demand1.getCreateName()==null?"":demand1.getCreateName();
//                sysUser1=sysUserMapper.getUserByName(createName);
//                if(sysUser1!=null){
//                    createNameId=sysUser1.getId();
//                }

                if(prjItemId!=null){
                    projectItemInfo=  projectItemInfoMapper.selectById(prjItemId);
                    mesFrom="任务管理";
                }else{
                    mesFrom="设备需求";
                }
            }
            String prjItemName=  projectItemInfo.getPrjItemName();
            String mesCon="工程点: "+prjItemName+"  的设备已到货，请领料！";
            MessageInfo messageInfo=new MessageInfo();
            messageInfo.setMessageId(UUID.randomUUID().toString().replace("-",""));
            messageInfo.setCreateTime(new Date());
            messageInfo.setMessageContent(mesCon);
            messageInfo.setMessageFrom(mesFrom);
            messageInfo.setMessageStatus("1");
            messageInfo.setMessageType("2");
            messageInfo.setReciveUser(createNameId);
            messageInfo.setCreateUser(userName);
            messageInfo.setRelId(taskId==null?"":taskId);
            Boolean isOk=  iMessageInfoService.save(messageInfo);


            int result = demandMapper.updateDemandStatus(demand);
            if (result > 0 && isOk==true){
                    isFlag = true;
            }
        }
        return isFlag;
    }

    /***
     * 设备需求退回
     * @param demandId
     * @return
     */
    @Override
    public Boolean updateDemandStatus(String demandId, String status) {
        Boolean isFlag = false;
        if(demandId!=null&& !demandId.equals("")){
            Demand demand = new Demand();
            demand.setDemandId(demandId);
            demand.setStatus(status);
            int result = demandMapper.updateDemandStatus(demand);
            if(result > 0){
                isFlag = true;
            }
        }
        return isFlag;
    }

    /**
     * 查询全部设备需求
     *
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<DemandVo> queryDemand(DemandVo demand, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<DemandVo> demandList = demandMapper.queryDemand(demand);
        return new PageInfo<DemandVo>(demandList);
    }

    /**
     * 查询全部设备需求(只查询)
     * @param demand
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<DemandVo> queryDemandStatus(DemandVo demand, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<DemandVo> demandList = demandMapper.queryDemandStatus(demand);
        return new PageInfo<DemandVo>(demandList);
    }

    /***
     * 根据任务id删除设备需求
     * @param taskId
     * @return
     */
    @Override
    public Boolean delDemandByTaskId(String taskId) {
        Boolean isFlag = false;
        if(taskId!=null && !taskId.equals("")){
            List<String> ids = new ArrayList<>();
            if(taskId.contains(",")){
                ids = new ArrayList<String>(Arrays.asList(taskId.split(",")));
            }else {
                ids.add(taskId);
            }

            int result = demandMapper.delDemandByTaskId(ids);
            if(result>0){
                isFlag = true;
            }
        }
        return isFlag;
    }

    @Override
    public DemandVo getDemandByPrjItenId(String prjItemId) {

        DemandVo demand= demandMapper.getDemandByPrjItenId(prjItemId);
        return demand;
    }

    @Override
    public List<Demand> queryDemandList(String prjItemId) {

        List<Demand> list=demandMapper.queryDemandList(prjItemId);
        return list;
    }

    @Override
    public List<Demand> queryDemandList1(String prjItemId) {

        List<Demand> list=demandMapper.queryDemandList1(prjItemId);
        return list;
    }

}
