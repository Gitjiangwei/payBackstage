package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.EquipInfo;
import org.hero.renche.entity.vo.EquipInfoVo;
import org.hero.renche.mapper.EquipInfoMapper;
import org.hero.renche.service.IEquipinfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class EquipInfoServiceImpl extends ServiceImpl<EquipInfoMapper,EquipInfo> implements IEquipinfoService {

    @Autowired
    private EquipInfoMapper equipInfoMapper;

    @Override
    public PageInfo<EquipInfoVo> qryEqipCountList(EquipInfoVo equipInfoVo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<EquipInfoVo> equipList = equipInfoMapper.qryEquipList(equipInfoVo);
        return new PageInfo<EquipInfoVo>(equipList);
    }

    @Override
    public PageInfo<EquipInfoVo> qryEquipListKeyDetail(EquipInfo equipInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<EquipInfoVo> equipInfoList = equipInfoMapper.qryEquipListKey(equipInfo);
        return new PageInfo<EquipInfoVo>(equipInfoList);
    }

    /**
     * 根据设备型号id查询详情只查询空闲和维修状态的设备
     *
     * @param equipInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<EquipInfoVo> qryEquipListKey(EquipInfo equipInfo, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<EquipInfoVo> equipInfoList = equipInfoMapper.qryEquipListKeys(equipInfo);
        return new PageInfo<EquipInfoVo>(equipInfoList);
    }

    @Override
    public Boolean updateDetailEquipInfo(EquipInfo equipInfo) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateDetailEquipInfo(equipInfo);
        if(resultOk>0){
            flag = true;
        }

        return flag;
    }

    @Override
    public Boolean updateEquipStatus(String equipId) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateEquipStatus(equipId);
        if (resultOk>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public void exportEquipInfoList(Map<String, String> map, HttpServletResponse response) {
        try {
            EquipInfoVo equipInfoVo=new EquipInfoVo();
            equipInfoVo.setMaterialName(map.get("materialName"));
            equipInfoVo.setMaterialType(map.get("materialType"));
            List<EquipInfoVo> qryList=equipInfoMapper.qryEquipList(equipInfoVo);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            EquipInfoVo vo=null;
            for(int i=0;i<qryList.size();i++){
                list=new ArrayList();
                vo=qryList.get(i);
                list.add(vo.getMaterialNo());
                list.add(vo.getMaterialName());
                list.add(vo.getMaterialType());
                list.add(vo.getCount());
                list.add(vo.getInuseCount());
                list.add(vo.getFreeCount());
                list.add(vo.getScripCount());
                list.add(vo.getMaintenonceCount());
                lists.add(list);
            }
            ExcelData excelData=new ExcelData();
            excelData.setName("设备库存");
            String[] titleColumn = {"物料编号","物料名称","物料型号","数量","使用中","空闲","报废","维修中"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "设备库存.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Boolean updateEquipStatusweix(String equipId) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateEuipStatusweix(equipId);
        if (resultOk>0){
            flag = true;
        }
        return flag;
    }

    /**
     * 设备报废
     *
     * @param equipId
     * @return
     */
    @Override
    public Boolean updateEuipStatusbaof(String equipId) {
        Boolean flag = false;
        int resultOk = equipInfoMapper.updateEuipStatusbaof(equipId);
        if (resultOk>0){
            flag = true;
        }
        return flag;
    }
    /**
     * 根据拥有方式和物料ID查找空闲的设备库存
     */
    @Override
    public List<EquipInfo>  getEquipinfo(String materialId, Integer haveWay) {

        List<EquipInfo>  list =equipInfoMapper.getEquipinfo(materialId ,haveWay );

        return list;
    }
}
