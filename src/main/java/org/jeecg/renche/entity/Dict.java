package org.jeecg.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典表
 *
 */
@Data
@TableName("tx_dict")
public class Dict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String dictId;
    /**字典类型编号*/
    private String dictCode;
    /**字典编号id*/
    private String dictCodeId;
    /**字典名称*/
    private String dictCodeName;
    /**父级*/
    private String parent;
    /**排序*/
    private String orderNo;
    /**是否使用*/
    private String isUse;


}
