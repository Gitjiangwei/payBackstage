package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息表
 *
 */
@Data
@TableName("tx_company_info")
public class CompanyInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String companyId;
    /**名称*/
    private String companyName;
    /**类型*/
    private String type;
    /**税号*/
    private String shuihao;
    /**开户银行*/
    private String bank;
    /**银行账号*/
    private String bankNo;
    /**联系人*/
    private String contacts;
    /**联系电话*/
    private String phone;
    /**所在地址*/
    private String address;
    /**邮箱*/
    private String email;
    /**公司简介*/
    private String introduc;
    /**身份证号*/
    private String idCard;
    /**创建时间*/
    private Date createTime;

}
