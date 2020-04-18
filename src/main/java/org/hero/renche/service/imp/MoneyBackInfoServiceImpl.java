package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.MoneyBackInfo;
import org.hero.renche.entity.vo.MoneyBackInfoVo;
import org.hero.renche.mapper.MoneyBackInfoMapper;
import org.hero.renche.service.IMoneyBackInfoService;
import org.hero.renche.util.ExcelData;
import org.hero.renche.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class MoneyBackInfoServiceImpl extends ServiceImpl<MoneyBackInfoMapper, MoneyBackInfo> implements IMoneyBackInfoService {

    @Autowired
    private MoneyBackInfoMapper moneyBackInfoMapper;

    @Override
    public PageInfo<MoneyBackInfoVo> qryBackInfoList(MoneyBackInfoVo moneyBackInfo, Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<MoneyBackInfoVo> backList = moneyBackInfoMapper.qryListBackInfo(moneyBackInfo);
        return new PageInfo<MoneyBackInfoVo>(backList);
    }

    @Override
    public boolean updateFileIds(MoneyBackInfo moneyBackInfo) {
        boolean flag = false;
        String chectFileIds = moneyBackInfo.getFileRelId();
        String oldFileRelId = moneyBackInfoMapper.qryFileIdsById(moneyBackInfo.getBackId());
        List<String> chectFileIdList = new ArrayList<>(Arrays.asList(chectFileIds.split(",")));
        for(String items : chectFileIdList){
            oldFileRelId =  oldFileRelId.replace(items+",","");
        }

        moneyBackInfo.setFileRelId(oldFileRelId);
        int result = moneyBackInfoMapper.updateFileIds(moneyBackInfo);
        if(result>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public void exportBackInfo (Map<String, String> map, HttpServletResponse response){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            MoneyBackInfoVo info = new MoneyBackInfoVo();
            if(map.get("backTime") != null){
                info.setBackTime(sdf.parse(map.get("backTime")));
            }
            info.setContractName(map.get("contractName"));

            List<MoneyBackInfoVo> backInfoList = moneyBackInfoMapper.qryListBackInfo(info);
            List<List<Object>> lists=new ArrayList<>();
            List<Object> list=null;
            MoneyBackInfoVo vo = null;
            for(int i = 0; i < backInfoList.size(); i++){
                list=new ArrayList();
                vo=backInfoList.get(i);
                list.add(vo.getContractName());
                if(vo.getBackTime()!= null){
                    list.add(sdf.format(vo.getBackTime()));
                }else{
                    list.add("");
                }
                list.add(vo.getBackMoney());
                list.add(vo.getBank());
                list.add(vo.getBankNo());
                list.add(vo.getBackPerson());
                list.add(vo.getEmail());
                list.add(vo.getRemark());
                lists.add(list);
            }

            ExcelData excelData=new ExcelData();
            excelData.setName("回款信息");
            String[] titleColumn = {"合同名称","回款日期","回款金额","回款开户银行","回款银行账户","负责人","邮箱","备注"};
            List<String> titlesList = Arrays.asList(titleColumn);
            excelData.setTitles(titlesList);
            excelData.setRows(lists);
            ExcelUtils.exportExcel(response , "回款信息.xlsx" , excelData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
