package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 发票信息表
 *
 */
@Data
@TableName("tx_invoci_info")
public class InvociInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String invociId;
    /**发票名称*/
    private String invociName;
    /**开票时间*/
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invociTime;
    /**发票内容*/
    private String content;
    /**税号*/
    private String shuihao;
    /**单位地址*/
    private String address;
    /**电话号码*/
    private String tel;
    /**开户银行*/
    private String bank;
    /**银行账号*/
    private String bankNo;
    /**附件关联id*/
    private String fileRelId;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
