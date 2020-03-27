package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.entity.vo.MessageInfoVo;

import java.util.List;

/**
 * 消息 Mapper 接口
 *
 */
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {

    List<MessageInfo> qryMessageListByRecive(@Param("messageInfo") MessageInfoVo messageInfo);

    int editMessageRead(String messageId);

    int updateAllReadStatus(String reciveUser);

    int deleteMessage(@Param("ids") List<String> ids);
}
