package org.hero.renche.controller.voentity;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
@Data
public class projectStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer value;
    private String name;

}
