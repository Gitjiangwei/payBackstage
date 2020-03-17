package org.hero.renche.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.hero.renche.entity.FileRel;

import java.util.Map;

/**
 * 附件
 */
public interface IFileRelService extends IService<FileRel> {

    /**
     *附件上传
     * @param fileRel
     * @return
     */
    Map<String,Object> fileUpload(FileRel fileRel);

    PageInfo<FileRel> qryFileRel(FileRel fileRels,String fileRel,Integer pageNo,Integer pageSize);

    String qryFileRelKey(String fileRelId);

    /**
     * 附件删除
     */
    Boolean deleteFile(String fileIds);

}
