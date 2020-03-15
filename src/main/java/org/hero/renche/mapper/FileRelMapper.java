package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.FileRel;

import java.util.List;


public interface FileRelMapper extends BaseMapper<FileRel> {
    int addFileRel(@Param("FileRel") FileRel fileRel);


    List<FileRel> qryFileRel(@Param("templist") List<String> fileRelId,@Param("fileName") String fileName);

    int deleteFile(@Param("list") List<String> fileListId);
}
