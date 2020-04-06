package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 人车4G数据字典类型表
 */
@Data
@TableName("tx_dict_item")
public class DictItem implements Serializable {


    //数据字典类型ID
    private String dictItemId;

    //数据字典类型编码
    private String dictItemCode;

    //数据字典类型名称
    private String dictItemName;

    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    //修改时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String uploadTime;

    //是否删除 0：未删除 1：已删除
    private String isFlag;
}
