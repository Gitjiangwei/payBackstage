package org.jeecg.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户拜访记录表
 *
 */
@Data
@TableName("tx_visit_info")
public class VisitInfo  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String visitId;
    /**客户信息id*/
    private String companyId;
    /**拜访时间*/
    private String visitTime;
    /**拜访人*/
    private String visitor;
    /**拜访方式*/
    private String way;
    /**拜访内容*/
    private String content;
    /**拜访结果*/
    private String result;
    /**附件关联id*/
    private String fileRelId;

}
