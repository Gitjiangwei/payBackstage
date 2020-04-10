package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.entity.vo.MessageInfoVo;
import org.hero.renche.mapper.MessageInfoMapper;
import org.hero.renche.service.IMessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

    @Autowired
    private MessageInfoMapper messageInfoMapper;

    @Override
    public PageInfo<MessageInfo> qryMessageListByRecive(Integer page, Integer pageSize, MessageInfoVo messageInfoVo) {

        PageHelper.startPage(page,pageSize);
        List<MessageInfo> messageInfoList = messageInfoMapper.qryMessageListByRecive(messageInfoVo);
        return new PageInfo<MessageInfo>(messageInfoList);
    }

    @Override
    public boolean updateMessageRead(MessageInfo messageInfo) {
        int count = messageInfoMapper.editMessageRead(messageInfo.getMessageId());
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateAllReadStatus(String reciveUser) {
        int count = messageInfoMapper.updateAllReadStatus(reciveUser);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteMessage(List<String> ids) {
        int count = messageInfoMapper.deleteMessage(ids);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }
}
