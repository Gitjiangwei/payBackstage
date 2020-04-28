package org.hero.renche.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hero.renche.entity.MessageInfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageInfoVo extends MessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**开始时间**/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    /**结束时间**/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;

}
