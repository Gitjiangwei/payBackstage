package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典表
 *
 */
@Data
@TableName("tx_pro_progress_record")
public class ProProgressRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**工程点id*/
    private String prjItemId;
    /**工程进度*/
    private String progressOfItem;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date creatTime;
    /**创建人*/
    private String createUserName;
    /**创建人*/
    private String createUser;
    /**是否使用*/
    private String isUse;


}
