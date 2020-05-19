package org.hero.renche.entity.vo;

import lombok.Data;
import org.hero.renche.entity.TaskInfo;

import java.io.Serializable;

@Data
public class TaskInfoVo extends TaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**附件个数**/
    private String fileCount = "0";
    /**工程进度**/
    private String progressOfItem;
    /**工程点名称*/
    private String prjItemName;

}
