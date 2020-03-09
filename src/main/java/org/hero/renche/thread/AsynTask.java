package org.hero.renche.thread;

import org.apache.shiro.authc.AuthenticationException;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.service.IEquipinfoService;
import org.hero.renche.service.imp.PurchaseServiceImpl;
import org.hero.renche.util.DailyinComeNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * 设备入库异步线程
 */
public class AsynTask {




   public void asyncTask(Map<String,Object> map){
        try {
            final int[] resultCount = {0};
            String equipName = map.get("equipName") == null ? "" : map.get("equipName").toString();
            List<EquipInfo> equipInfoList1 = new ArrayList<EquipInfo>();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //为防止阻塞让线程5秒以后开始执行
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String thisEquipCount = map.get("thisEquipCounts").toString();
                    //入库生成设备编号
                    Map<String, String> receivingMap = new HashMap<String, String>();
                    for (int i = 1; i <= Integer.valueOf(map.get("equipCount").toString() == null ? "0" : map.get("equipCount").toString()); i++) {
                        EquipInfo equipInfo = new EquipInfo();
                        receivingMap = DailyinComeNumberUtil.dailyinNumber(String.valueOf(i), thisEquipCount);
                        thisEquipCount = receivingMap.get("thisEquipCount");
                        equipInfo.setEquipId(UUID.randomUUID().toString().replace("-", ""));
                        equipInfo.setEquipName(equipName);
                        equipInfo.setEquipModel(map.get("equipModel").toString());
                        equipInfo.setEquipNo(receivingMap.get("num"));//设备编号
                        equipInfo.setEquipPrice(map.get("equipPrice").toString());
                        equipInfo.setPurchaseId(map.get("purchaseId").toString());
                        System.out.println(receivingMap.get("num"));
                        equipInfoList1.add(equipInfo);
                    }
                    PurchaseInfoMapper purchaseInfoMapper = (PurchaseInfoMapper)map.get("purchaseInfoMapper");
                    resultCount[0] = purchaseInfoMapper.insertReceiving(equipInfoList1);
                    int count = resultCount[0];
                   if(count>0) {
                       purchaseInfoMapper.updatePurchaseKey(map.get("purchaseId").toString());
                    }
                }
            });
            thread.start();

        }catch (Exception e){
            e.printStackTrace();
            throw new AuthenticationException("设备“" + map.get("equipName") == null ? "" : map.get("equipName")+ "”入库失败！",e);
        }
    }
}
