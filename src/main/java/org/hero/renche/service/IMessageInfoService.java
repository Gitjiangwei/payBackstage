package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.entity.vo.MessageInfoVo;

import java.util.List;

public interface IMessageInfoService extends IService<MessageInfo> {

    PageInfo<MessageInfo> qryMessageListByRecive(Integer page, Integer pageSize, MessageInfoVo messageInfo);

    boolean updateMessageRead(MessageInfo messageInfo);

    boolean updateAllReadStatus(String reciveUser);

    boolean deleteMessage(List<String> ids);

}
