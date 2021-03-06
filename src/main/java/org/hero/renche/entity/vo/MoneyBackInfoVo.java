package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.MoneyBackInfo;

import java.io.Serializable;

@Data
public class MoneyBackInfoVo extends MoneyBackInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**合同名称**/
    private String contractName;
    /**附件个数**/
    private String fileCount = "0";

}
