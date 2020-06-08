package org.hero.renche.thread;

import org.apache.shiro.authc.AuthenticationException;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.util.DailyinComeNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * 有返回值线程
 */
public class CallableThread implements Callable<Integer> {

    @Autowired
    PurchaseInfoMapper purchaseInfoMapper;

    @Autowired
    EquipInfoMapper equipInfoMapper;


    Map<String,String> map = new HashMap<String, String>();

    public CallableThread(Map<String,String> equipMap){
        this.map = equipMap;
    }
    @Override
    public Integer call() throws IOException {
        try {
            final int[] resultCount = {0};
            List<EquipInfo> equipInfoList1 = new ArrayList<EquipInfo>();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        Date expirationDate = null;
                        String expirationTime = map.get("expirationDate");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                        if(expirationTime != null && !"".equals(expirationTime)){
                            expirationDate = sdf.parse(expirationTime);
                        }

                        //查询当前库存的设备数量
                        EquipInfo equipInfo = new EquipInfo();
                        String thisEquipCount = String.valueOf(equipInfoMapper.qryEquipKeyCount(map.get("materialId")));
                        //入库生成设备编号
                        Map<String, String> receivingMap = new HashMap<String, String>();
                        for (int i = 1; i <= Integer.valueOf(map.get("equipCount") == null ? "0" : map.get("equipCount")); i++) {
                            equipInfo = new EquipInfo();
                            receivingMap = DailyinComeNumberUtil.dailyinNumber(String.valueOf(i), thisEquipCount,map);
                            thisEquipCount = receivingMap.get("thisEquipCount");
                            equipInfo.setEquipId(UUID.randomUUID().toString().replace("-", ""));
                            equipInfo.setMaterialId(map.get("materialId"));
                            equipInfo.setEquipNo(receivingMap.get("num"));//设备编号
                            equipInfo.setEquipPrice(map.get("equipPrice"));
                            equipInfo.setPurchaseId(map.get("purchaseId"));
                            equipInfo.setHaveWay(Integer.parseInt(map.get("haveWay")));
                            equipInfo.setExpirationDate(expirationDate);
                            equipInfoList1.add(equipInfo);
                        }
                        resultCount[0] = purchaseInfoMapper.insertReceiving(equipInfoList1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
            return resultCount[0];
        }catch (Exception e){
            throw new AuthenticationException("设备“" + map.get("materialName") == null ? "" : map.get("materialName")+ "”入库失败！",e);
        }
    }
}
