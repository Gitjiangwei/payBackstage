package org.hero.renche.thread;

import org.apache.shiro.authc.AuthenticationException;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.util.DailyinComeNumberUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备入库异步线程
 */
public class AsynTask {

   public void asyncTask(Map<String,Object> map){
        try {
            final int[] resultCount = {0};
            String equipName = map.get("materialName").toString();
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

                    try {
                        Date expirationDate = null;
                        String expirationTime = map.get("expirationDate") == null?null : map.get("expirationDate").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                        if(expirationTime != null && !"".equals(expirationTime)){
                            expirationDate = sdf.parse(expirationTime);
                        }

                        String thisEquipCount = map.get("thisEquipCounts").toString();
                        Map<String,String> purchaseMap = new HashMap<String, String>();
                        //设备名称
                        purchaseMap.put("equipName",equipName);
                        //设备型号
                        purchaseMap.put("equipModel",map.get("materialType").toString());
                        //入库生成设备编号
                        Map<String, String> receivingMap = new HashMap<String, String>();
                        for (int i = 1; i <= Integer.valueOf(map.get("equipCount").toString() == null ? "0" : map.get("equipCount").toString()); i++) {
                            EquipInfo equipInfo = new EquipInfo();
                            receivingMap = DailyinComeNumberUtil.dailyinNumber(String.valueOf(i), thisEquipCount,purchaseMap);
                            thisEquipCount = receivingMap.get("thisEquipCount");
                            equipInfo.setEquipId(UUID.randomUUID().toString().replace("-", ""));
                            equipInfo.setMaterialId(map.get("materialId").toString());
                            equipInfo.setEquipNo(receivingMap.get("num"));//设备编号
                            equipInfo.setEquipPrice(map.get("equipPrice").toString());
                            equipInfo.setPurchaseId(map.get("purchaseId").toString());
                            equipInfo.setHaveWay(map.get("haveWay").toString());
                            equipInfo.setExpirationDate(expirationDate);
                            equipInfoList1.add(equipInfo);
                        }
                        PurchaseInfoMapper purchaseInfoMapper = (PurchaseInfoMapper)map.get("purchaseInfoMapper");
                        resultCount[0] = purchaseInfoMapper.insertReceiving(equipInfoList1);
                        int count = resultCount[0];
                        if(count>0) {
                            purchaseInfoMapper.updatePurchaseKey(map.get("purchaseId").toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }catch (Exception e){
            e.printStackTrace();
            throw new AuthenticationException("设备“" + map.get("materialName") == null ? "" : map.get("materialName")+ "”入库失败！",e);
        }
    }
}
