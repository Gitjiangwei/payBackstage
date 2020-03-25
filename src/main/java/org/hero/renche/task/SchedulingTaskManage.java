package org.hero.renche.task;

import org.hero.renche.entity.ContractInfo;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.mapper.ContractInfoMapper;
import org.hero.renche.service.IMessageInfoService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SchedulingTaskManage {

    @Autowired
    private ContractInfoMapper contractInfoMapper;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IMessageInfoService messageInfoService;

    public void manageTask(String type){

        try {
            List<ContractInfo> contractInfos = contractInfoMapper.qryIdListByRemaindType(type.toUpperCase());
            SysUser sysUser = sysUserService.getUserByName("admin");//默认接收者
            List<MessageInfo> messageList = new ArrayList<>();

            MessageInfo messageInfo = null;
            for (int i = 0; i < contractInfos.size(); i++){
                ContractInfo contractInfo = contractInfos.get(i);
                messageInfo = new MessageInfo();
                messageInfo.setMessageContent("合同："+contractInfo.getContractName()+"  已签订，请注意催收回款");
                messageInfo.setMessageFrom("tx_contract_info");
                messageInfo.setMessageStatus("1");//1未读 0已读
                messageInfo.setCreateTime(new Date());
                messageInfo.setRelId(contractInfo.getContractId());
                messageInfo.setMessageType("1");
                messageInfo.setReciveUser(sysUser.getId());

                messageList.add(messageInfo);
            }

            messageInfoService.saveBatch(messageList);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
