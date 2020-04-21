package org.jeecg.modules.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.hero.renche.controller.voentity.VoWorkOrderInfo;
import org.jeecg.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author scott
 * @since 2018-12-20
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	/**
	 * @param username
	 * @return
	 */
	public SysUser getUserByName(@Param("username") String username);

	List<SysUser>  qrySysUserList(@Param("username") String username);
}
