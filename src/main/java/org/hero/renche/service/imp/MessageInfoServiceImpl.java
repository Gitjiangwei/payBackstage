package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hero.renche.entity.MessageInfo;
import org.hero.renche.mapper.MessageInfoMapper;
import org.hero.renche.service.IMessageInfoService;
import org.springframework.stereotype.Service;

@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements IMessageInfoService {

}
