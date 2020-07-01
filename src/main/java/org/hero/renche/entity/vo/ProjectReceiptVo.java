package org.hero.renche.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hero.renche.entity.ProjectReceipt;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 工程验收单
 * </p>
 *
 * @author MeiJiaLin
 * @since 2020-06-19
 */
@Data
public class ProjectReceiptVo extends ProjectReceipt implements Serializable {

    private static final long serialVersionUID = 1L;


    /**工程点名称*/
    private String prjItemName;



}
