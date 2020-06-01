package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.PurchaseInfo;
import org.hero.renche.entity.vo.PurchaseInfoVo;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.mapper.PurchaseInfoMapper;
import org.hero.renche.service.IPurchaseService;
import org.hero.renche.thread.AsynTask;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseInfoMapper,PurchaseInfo> implements IPurchaseService {

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;

    @Autowired
    private EquipInfoMapper equipInfoMapper;


    @Transactional
    @Override
    public PageInfo<PurchaseInfoVo> qryPurchaseInfo(PurchaseInfoVo purchaseInfo, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<PurchaseInfoVo> purchaseInfoList = purchaseInfoMapper.qryListPurchaseInfo(purchaseInfo);
        return new PageInfo<PurchaseInfoVo>(purchaseInfoList);
    }

    @Override
    public boolean updatePurchaseIds(String ids) {
        if (ids==null||"".equals(ids.trim())){
            return false;
        }
        List<String> purchaseIds = Arrays.asList(ids.split(","));
        purchaseInfoMapper.updatePurchaseByIds(purchaseIds);
        return true;
    }

    @Override
    public boolean insertReceiving(PurchaseInfoVo purchaseInfo) {
        Boolean flag = false;
        Map<String,Object> receivingMap = new HashMap<String, Object>();
        receivingMap.put("equipCount",purchaseInfo.getQuantity());
        receivingMap.put("equipPrice",purchaseInfo.getPrice());
        receivingMap.put("purchaseId",purchaseInfo.getPurchaseId());
        receivingMap.put("materialId",purchaseInfo.getMaterialId());
        receivingMap.put("materialName",purchaseInfo.getMaterialName());
        receivingMap.put("materialType",purchaseInfo.getMaterialType());
        receivingMap.put("haveWay",purchaseInfo.getHaveWay());
        receivingMap.put("expirationDate",purchaseInfo.getExpirationDate());
        //查询当前库存的设备数量
        int thisEquipCounts = equipInfoMapper.qryEquipKeyCount(purchaseInfo.getMaterialId());
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
    public boolean updateFileIds(PurchaseInfo purchaseInfo) {
        boolean flag = false;
        PurchaseInfoVo pur = new PurchaseInfoVo();
        String oldFileRelId = "";
        String newFileRelId = "";
        pur.setPurchaseId(purchaseInfo.getPurchaseId());
        List<PurchaseInfoVo> purchaseInfoList = purchaseInfoMapper.qryListPurchaseInfo(pur);
        for(PurchaseInfo item : purchaseInfoList){
            oldFileRelId = item.getFileRelId();
        }
        List<String> oldFlieRelIdList = new ArrayList<>(Arrays.asList(oldFileRelId.split(",")));
        List<String> delFlieRelIdList = new ArrayList<>(Arrays.asList(purchaseInfo.getFileRelId().split(",")));
        LinkedList<String> result = new LinkedList<>(oldFlieRelIdList);
        HashSet<String> set = new HashSet<>(delFlieRelIdList);
        Iterator<String> itor = result.iterator();
        while(itor.hasNext()){
            if(set.contains(itor.next())){
                itor.remove();
            }
        }
        newFileRelId = Arrays.toString(result.toArray());
        if(newFileRelId != null && !newFileRelId.equals("")) {
            newFileRelId = newFileRelId.substring(1);
            newFileRelId = newFileRelId.substring(0, newFileRelId.length() - 1);
            if(!newFileRelId.equals("")) {
                char a = newFileRelId.charAt(newFileRelId.length() - 1);
                if (a == ',') {
                    newFileRelId = newFileRelId.substring(0, newFileRelId.length() - 1);
                }
            }
        }else{
            newFileRelId = "";
        }
        purchaseInfo.setFileRelId(newFileRelId);
        int results = purchaseInfoMapper.updateFileIds(purchaseInfo);
        if(results>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public void exportPurchaseInfo (Map<String, String> map, HttpServletResponse response){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            PurchaseInfoVo purchaseInfo=new PurchaseInfoVo();
            purchaseInfo.setMaterialName(map.get("materialName"));
            purchaseInfo.setMaterialType(map.get("materialType"));
            purchaseInfo.setPurchaser(map.get("purchaser"));
            purchaseInfo.setIsarrival(map.get("isarrival"));
            purchaseInfo.setIsstorage(map.get("isstorage"));
            purchaseInfo.setHaveWay(map.get("haveWay"));
            if(map.get("purchaseTime") != null){
                purchaseInfo.setPurchaseTime(sdf.parse(map.get("purchaseTime")));
            }
            List<PurchaseInfoVo> qryList=purchaseInfoMapper.qryListPurchaseInfo(purchaseInfo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            PurchaseInfoVo vo=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vo=qryList.get(i);
                list.add(vo.getMaterialNo());
                list.add(vo.getMaterialName());
                list.add(vo.getMaterialType());
                list.add(vo.getPrice());
                list.add(vo.getQuantity());
                list.add(vo.getTotalPrice());
                list.add(vo.getPurchaser());
                if(vo.getPurchaseTime() != null){
                    list.add(sdf.format(vo.getPurchaseTime()));
                }else{
                    list.add("");
                }
                list.add(vo.getCompanyName());
                list.add(vo.getHaveWay().equals('0')?"租赁":"购买");
                if(vo.getExpirationDate() != null){
                    list.add(sdf.format(vo.getExpirationDate()));
                }else{
                    list.add("");
                }
                if(vo.getArrivalTime() != null){
                    list.add(sdf.format(vo.getArrivalTime()));
                }else{
                    list.add("");
                }
                list.add(vo.getIsarrival().equals("1")?"是":"否");
                list.add(vo.getIsstorage().equals("1")?"已入库":"未入库");
                list.add(vo.getRemark());
                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("设备采购");
            String[] titleColumn = {"物料编号","物料名称","物料型号","单价","数量","总价","采购人员","采购日期","采购来源","拥有方式","租赁到期日期","是否到货","到货日期","是否入库","备注"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "设备采购.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
