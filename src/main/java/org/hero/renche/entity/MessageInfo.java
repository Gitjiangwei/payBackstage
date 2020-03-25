package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同信息表
 *
 */
@Data
@TableName("tx_message")
public class MessageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String messageId;
    /**消息内容*/
    private String messageContent;
    /**消息类型*/
    private String messageType;
    /**关联id*/
    private String relId;
    /**消息来源*/
    private String messageFrom;
    /**状态*/
    private String messageStatus;
    /**创建人*/
    private String createUser;
    /**创建时间*/
    private Date createTime;
    /**接收人*/
    private String reciveUser;
    /**修改时间*/
    private Date updateTime;

}
