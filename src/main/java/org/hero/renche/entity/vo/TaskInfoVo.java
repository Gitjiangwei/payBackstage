package org.hero.renche.entity.vo;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.hero.renche.entity.TaskInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TaskInfoVo extends TaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**附件个数**/
    private String fileCount = "0";
    /**设备需求list**/
    private List<T> equipList = new ArrayList<>();

}
