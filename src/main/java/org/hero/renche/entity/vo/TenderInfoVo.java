package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.TenderInfo;

import java.io.Serializable;

@Data
public class TenderInfoVo extends TenderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**附件个数**/
    private String fileCount = "0";

}
