package org.hero.renche.thread;

import org.apache.shiro.authc.AuthenticationException;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.util.DailyinComeNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.CharBuffer;
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
            String equipName = map.get("equipName") == null ? "" : map.get("equipName");
            List<EquipInfo> equipInfoList1 = new ArrayList<EquipInfo>();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //查询当前库存的设备数量
                    EquipInfo equipInfo = new EquipInfo();
                    equipInfo.setPurchaseId(map.get("purchaseId") == null ? "" : map.get("purchaseId"));
                    List<EquipInfo> equipInfoList = equipInfoMapper.qryEquipListKey(equipInfo);
                    String thisEquipCount = String.valueOf(equipInfoList.size());
                    //入库生成设备编号
                    Map<String, String> receivingMap = new HashMap<String, String>();
                    for (int i = 1; i <= Integer.valueOf(map.get("equipCount") == null ? "0" : map.get("equipCount")); i++) {
                        equipInfo = new EquipInfo();
                        receivingMap = DailyinComeNumberUtil.dailyinNumber(String.valueOf(i), thisEquipCount);
                        thisEquipCount = receivingMap.get("thisEquipCount");
                        equipInfo.setEquipId(UUID.randomUUID().toString().replace("-", ""));
                        equipInfo.setEquipName(equipName);
                        equipInfo.setEquipModel(map.get("equipModel"));
                        equipInfo.setEquipNo(receivingMap.get("num"));//设备编号
                        equipInfo.setEquipPrice(map.get("equipPrice"));
                        equipInfo.setPurchaseId(map.get("purchaseId"));
                        System.out.println(receivingMap.get("num"));
                        equipInfoList1.add(equipInfo);
                    }
                    resultCount[0] = purchaseInfoMapper.insertReceiving(equipInfoList1);
                }
            });
            thread.start();
            return resultCount[0];
        }catch (Exception e){
            throw new AuthenticationException("设备“" + map.get("equipName") == null ? "" : map.get("equipName")+ "”入库失败！",e);
        }
    }
}
