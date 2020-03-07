package org.hero.renche.entity.modelData;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class EquipinfoModel implements Serializable {

    //设备Id
    private String purchaseId;

    //库存设备名称
    private String equipName;

    //库存设备型号
    private String equipModel;

    //库存数量
    private String count;

    //库存使用中数量
    private String inuseCount;

    //库存空闲数量
    private String freeCount;

    //库存报废数量
    private String scripCount;

    //库存维修数量
    private String maintenonceCount;

    /**创建时间*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
