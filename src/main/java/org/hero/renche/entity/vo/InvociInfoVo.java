package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.InvociInfo;

import java.io.Serializable;

@Data
public class InvociInfoVo extends InvociInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**合同名称**/
    private String contractName;
    /**附件个数**/
    private String fileCount = "0";

}
