package org.hero.renche.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tx_file_rel")
public class FileRel implements Serializable {
    private static final long serialVersionUID = 1L;

    /*附件Id*/
    private String fileRelId;

    /*附件关联表名*/
    private String reltable;

    /*附件id*/
    private String fileId;

    /*附件路径*/
    private String fileUrl;

    /*附件名称*/
    private String fileName;

}
