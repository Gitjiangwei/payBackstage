package org.hero.renche.entity.vo;

import org.hero.renche.entity.MessageInfo;

public class MessageInfoTransformation {

    public static MessageInfo toPo(MessageInfoVo vo){
        MessageInfo po = new MessageInfo();
        po.setMessageId(vo.getMessageId());
        po.setMessageContent(vo.getMessageContent());
        po.setMessageStatus(vo.getMessageStatus());
        po.setMessageType(vo.getMessageType());
        po.setMessageFrom(vo.getMessageFrom());
        po.setRelId(vo.getRelId());
        po.setReciveUser(vo.getReciveUser());
        po.setCreateUser(vo.getCreateUser());
        po.setCreateTime(vo.getCreateTime());
        po.setUpdateTime(vo.getUpdateTime());
        return  po;
    }

    public static MessageInfoVo toVo(MessageInfo po){
        MessageInfoVo vo = new MessageInfoVo();
        vo.setMessageId(po.getMessageId());
        vo.setMessageContent(po.getMessageContent());
        vo.setMessageStatus(po.getMessageStatus());
        vo.setMessageType(po.getMessageType());
        vo.setMessageFrom(po.getMessageFrom());
        vo.setRelId(po.getRelId());
        vo.setReciveUser(po.getReciveUser());
        vo.setCreateUser(po.getCreateUser());
        vo.setCreateTime(po.getCreateTime());
        vo.setUpdateTime(po.getUpdateTime());
        return  vo;
    }

}
