package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.service.IPurchaseService;
import org.hero.renche.thread.AsynTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseInfoMapper,PurchaseInfo> implements IPurchaseService {

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;

    @Autowired
    private EquipInfoMapper equipInfoMapper;


    @Transactional
    @Override
    public PageInfo<PurchaseInfo> qryPurchaseInfo(PurchaseInfo purchaseInfo, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<PurchaseInfo> purchaseInfoList = purchaseInfoMapper.qryListPurchaseInfo(purchaseInfo);
        return new PageInfo<PurchaseInfo>(purchaseInfoList);
    }

    @Override
    public int qryPurchaseId(String purchaseId) {
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setPurchaseId(purchaseId);
        return purchaseInfoMapper.qryListPurchaseInfo(purchaseInfo).size();
    }

    @Override
    public boolean updatePurchaseIds(String ids) {
        boolean flag = false;
        if (ids==null||"".equals(ids.trim())){
            return false;
        }
        List<String> purchaseIds = Arrays.asList(ids.split(","));
        int updateCount = purchaseInfoMapper.updatePurchaseByIds(purchaseIds);
        if (updateCount > 0){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean insertReceiving(PurchaseInfo purchaseInfo) {
        Boolean flag = false;
        Map<String,Object> receivingMap = new HashMap<String, Object>();
        receivingMap.put("equipCount",purchaseInfo.getQuantity());
        receivingMap.put("equipPrice",purchaseInfo.getPrice());
        receivingMap.put("equipModel",purchaseInfo.getItemModel());
        receivingMap.put("equipName",purchaseInfo.getPurchaseItem());
        receivingMap.put("purchaseId",purchaseInfo.getPurchaseId());
        //查询当前库存的设备数量
        int thisEquipCounts = equipInfoMapper.qryEquipKeyCount(purchaseInfo.getPurchaseId());
        //List<EquipInfo> equipInfoList = equipInfoMapper.qryEquipListKey(equipInfo);
        receivingMap.put("thisEquipCounts",String.valueOf(thisEquipCounts));
        //注入mapper
        receivingMap.put("purchaseInfoMapper",purchaseInfoMapper);
        //执行线程
        new AsynTask().asyncTask(receivingMap);
        try {
            Thread.sleep(1000);
            flag = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Boolean qryPurchaseInfoKey(String purchaseId) {

        String isstorage = purchaseInfoMapper.qryPurchaseInfoKey(purchaseId);
        if(isstorage.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updatePurchaseKeys(PurchaseInfo purchaseInfo) {

        int result = purchaseInfoMapper.updatePurchaseByKey(purchaseInfo);
        if(result>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String qryePurchaseId(String purchaseId) {
        List<PurchaseInfo> purchaseInfoList = purchaseInfoMapper.qryListPurchaseInfoId(purchaseId);
        String whichCompany = "";
        for(PurchaseInfo item:purchaseInfoList){
            whichCompany = item.getWhichCompany();
        }
        return whichCompany;
    }

    @Override
    public boolean updateFileIds(PurchaseInfo purchaseInfo) {
        boolean flag = false;
        PurchaseInfo pur = new PurchaseInfo();
        String oldFileRelId = "";
        String newFileRelId = "";
        pur.setPurchaseId(purchaseInfo.getPurchaseId());
        List<PurchaseInfo> purchaseInfoList = purchaseInfoMapper.qryListPurchaseInfo(pur);
        for(PurchaseInfo item : purchaseInfoList){
            oldFileRelId = item.getFileRelId();
        }
        List<String> oldFlieRelIdList = new ArrayList<>(Arrays.asList(oldFileRelId.split(",")));
        for(String items : oldFlieRelIdList){
            if (!items.equals(purchaseInfo.getFileRelId())){
                newFileRelId =  items + newFileRelId + ",";
            }
        }
        char a = newFileRelId.charAt(newFileRelId.length() - 1);
        if(a == ','){
            newFileRelId = newFileRelId.substring(0,newFileRelId.length() - 1);
        }
        purchaseInfo.setFileRelId(newFileRelId);
        int result = purchaseInfoMapper.updateFileIds(purchaseInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }


}
