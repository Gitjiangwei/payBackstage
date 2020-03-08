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




   public void asyncTask(Map<String,String> map){
        try {
            final int[] resultCount = {0};
            String equipName = map.get("equipName") == null ? "" : map.get("equipName");
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
                    String thisEquipCount = map.get("thisEquipCounts");
                    //入库生成设备编号
                    Map<String, String> receivingMap = new HashMap<String, String>();
                    for (int i = 1; i <= Integer.valueOf(map.get("equipCount") == null ? "0" : map.get("equipCount")); i++) {
                        EquipInfo equipInfo = new EquipInfo();
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
                    AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
                    PurchaseInfoMapper purchaseInfoMapper = ac.getBean(PurchaseInfoMapper.class);
                    resultCount[0] = purchaseInfoMapper.insertReceiving(equipInfoList1);
                }
            });
            thread.start();
            if(resultCount[0]>0) {
                throw new AuthenticationException("设备“" + map.get("equipName") == null ? "" : map.get("equipName") + "”入库成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new AuthenticationException("设备“" + map.get("equipName") == null ? "" : map.get("equipName")+ "”入库失败！",e);
        }
    }
}
