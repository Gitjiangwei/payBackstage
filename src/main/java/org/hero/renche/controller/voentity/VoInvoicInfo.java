package org.hero.renche.controller.voentity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

@Data
public class VoInvoicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    private String invociId;
    /**发票名称*/
    private String invociName;
    /**开票时间*/
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
     /*附件数量*/
    private int fileRelNum;
    /*附件名称*/
    private String fileName;
    /**附件关联id*/
    private String fileRelId;


}
