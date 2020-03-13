package org.hero.renche.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.FileRel;
import org.hero.renche.mapper.FileRelMapper;
import org.hero.renche.service.IFileRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class FileRelServiceImpl extends ServiceImpl<FileRelMapper,FileRel> implements IFileRelService {

    @Autowired
    private FileRelMapper relMapper;


    @Override
    public Map<String, Object> fileUpload(FileRel fileRel) {
        Map<String,Object> fileRelMap = new HashMap<String, Object>();
        if(fileRel.getFileRelId()==null||fileRel.getFileRelId().equals("")){
            fileRel.setFileRelId(UUID.randomUUID().toString().replace("-",""));
        }
        int count = relMapper.addFileRel(fileRel);
        if(count>0){
            fileRelMap.put("flag",true);
            fileRelMap.put("fileRelId",fileRel.getFileRelId());
        }else {
            fileRelMap.put("flag",false);
        }

        return fileRelMap;
    }

    @Override
    public PageInfo<FileRel> qryFileRel(FileRel fileRels,String fileRel,Integer pageNo,Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        String[] file = fileRel.split(",");
        List<String> fileRelList = new ArrayList<String>(Arrays.asList(file));
        List<FileRel> fileRelLists = relMapper.qryFileRel(fileRelList,fileRels.getFileName());
        return new PageInfo<FileRel>(fileRelLists);
    }

    @Override
    public String qryFileRelKey(String fileRelId) {
        String fileRelUrl = "";
        List<String> list = new ArrayList<String>();
        list.add(fileRelId);
        List<FileRel> fileRelList = relMapper.qryFileRel(list,null);
        for (FileRel item:fileRelList){
            fileRelUrl = item.getFileUrl();
        }
        return fileRelUrl;
    }
}
