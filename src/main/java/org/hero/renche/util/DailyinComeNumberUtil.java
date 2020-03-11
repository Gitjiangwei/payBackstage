package org.hero.renche.util;

import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.thread.AsynTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 生成编号工具类
 */
public class DailyinComeNumberUtil {


    /**
     * @param purchaseNumber 当前传入数
     * @param thisEquipCount 数据库的数量
     * @return
     */
    public static Map<String,String> dailyinNumber(String purchaseNumber,String thisEquipCount,Map<String,String> purchaseMap){
        Map<String,String> map = new HashMap<String, String>();
        String number = "";
        //每个设备最大数为6位数
        String numberCount = "";
        int a = Integer.valueOf(thisEquipCount);
        String purchaeName = purchaseMap.get("equipName")==null?"":purchaseMap.get("equipName");
        String purchaseModel = purchaseMap.get("equipModel") == null?"":purchaseMap.get("equipModel");
        String pinyinName = PinyinUtil.getPinYinHeadChar(purchaeName,true);
        //生成时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Date(System.currentTimeMillis()));
        if( a > 0){
            thisEquipCount = String.valueOf(a+1);
            int num = 6 - thisEquipCount.length();
            for(int i = 0;i< num; i++){
                numberCount = "0" + numberCount;
            }
            number = "HEROSB-"+pinyinName+purchaseModel+"-"+date+numberCount+thisEquipCount;
        }else{
            thisEquipCount =  String.valueOf(a+1);
            int num = 6 - purchaseNumber.length();
            for(int i = 0;i< num; i++){
                numberCount = "0" + numberCount;
            }
            number = "HEROSB-"+pinyinName+purchaseModel+"-"+date+numberCount+thisEquipCount;
        }
        map.put("thisEquipCount",thisEquipCount);
        map.put("num",number);
        return map;
    }

    public static void main(String[] args){
        String thisEquipCount = "98";
        Map<String,String> map = new HashMap<String,String>();
        Map<String,String> purchaseMap = new HashMap<String,String>();
        purchaseMap.put("equipName","空调");
        //设备型号
        purchaseMap.put("equipModel","X90");
        map.put("",thisEquipCount);
       for (int i = 1; i<=10; i++) {
           map = dailyinNumber(String.valueOf(i),thisEquipCount,purchaseMap);
           thisEquipCount = map.get("thisEquipCount");
           System.out.println(map.get("num"));
       }


       try {
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        ExecutorService executorService = Executors.newCachedThreadPool();
//        AsynTask asynTask = new AsynTask(receivingMap);
//        Future<Integer> result = executorService.submit(asynTask);
//        if(executorService != null){
//            executorService.shutdown();
//        }
//        int resultCount = 0;
//        try {
//             resultCount = result.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        System.out.println("1111111111111111111111111111");

    }
}
