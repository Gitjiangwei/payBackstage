package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.hero.renche.entity.OutEquipInfo;

public interface IOutEquipService extends IService<OutEquipInfo> {

    /**
     * 设备出库
     * @param equipIds 设备Id
     * @param prjItemId 工程点Id
     * @return
     */
    Boolean equipInfoOut(String equipIds,String prjItemId);


    Boolean delOutEquip(String outId);
}
