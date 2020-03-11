package org.hero.renche.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.hero.renche.entity.FileRel;


public interface FileRelMapper extends BaseMapper<FileRel> {
    int addFileRel(@Param("FileRel") FileRel fileRel);
}
