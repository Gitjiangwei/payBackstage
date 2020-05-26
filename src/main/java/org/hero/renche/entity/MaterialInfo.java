package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-05-25
 */
@Data
@TableName("tx_material_info")
public class MaterialInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.UUID)
    private String materialId;
    /**物料编号*/
    private String materialNo;
    /**物料名称*/
    private String materialName;
    /**拼音名称*/
    private String pyName;
    /**物料型号*/
    private String materialType;
    /**物料单位*/
    private String materialUnit;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    /**更新时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;


}
