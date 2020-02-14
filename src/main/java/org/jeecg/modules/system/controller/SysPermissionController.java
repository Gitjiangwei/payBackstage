package org.jeecg.modules.system.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.entity.SysPermission;
import org.jeecg.modules.system.entity.SysRolePermission;
import org.jeecg.modules.system.model.SysPermissionTree;
import org.jeecg.modules.system.model.TreeModel;
import org.jeecg.modules.system.service.ISysPermissionService;
import org.jeecg.modules.system.service.ISysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author scott
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {
	
	@Autowired
	private ISysPermissionService sysPermissionService;
	
	@Autowired
	private ISysRolePermissionService sysRolePermissionService;
	
	
	/**
	 * 加载数据节点
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<List<SysPermissionTree>> list() {
		//@RequestParam(name="pid",required=false) String parentId
		Result<List<SysPermissionTree>> result = new Result<>();
		try {
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getDelFlag, 0);
			query.orderByAsc(SysPermission::getSortNo);
			List<SysPermission> list = sysPermissionService.list(query);
			List<SysPermissionTree> treeList = new ArrayList<>();
			getTreeList(treeList, list, null);
			result.setResult(treeList);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *  查询用户的权限
	 * @return
	 */
	@RequestMapping(value = "/queryByUser", method = RequestMethod.GET)
	public Result<JSONArray> queryByUser(HttpServletRequest req) {
		Result<JSONArray> result = new Result<>();
		try {
			String username = req.getParameter("username");
			List<SysPermission> metaList = sysPermissionService.queryByUser(username);
			JSONArray jsonArray = new JSONArray();
			this.getPermissionJsonArray(jsonArray, metaList, null);
			result.setResult(jsonArray);
			result.success("查询成功");
		} catch (Exception e) {
			result.error500("查询失败:"+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	

	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresRoles({"admin"})
	public Result<SysPermission> add(@RequestBody SysPermission permission) {
		Result<SysPermission> result = new Result<SysPermission>();
		try {
			sysPermissionService.addPermission(permission);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.error500("操作失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	@RequiresRoles({"admin"})
	public Result<SysPermission> eidt(@RequestBody SysPermission permission) {
		Result<SysPermission> result = new Result<>();
		try {
			sysPermissionService.editPermission(permission);
			result.success("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.error500("操作失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@RequiresRoles({"admin"})
	public Result<SysPermission> delete(@RequestParam(name="id",required=true) String id) {
		Result<SysPermission> result = new Result<>();
		try {
			sysPermissionService.deletePermission(id);
			result.success("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.error500(e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	@RequiresRoles({"admin"})
	public Result<SysPermission> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<SysPermission> result = new Result<>();
		try {
			String arr[] = ids.split(",");
			for (String id : arr) {
				if(oConvertUtils.isNotEmpty(id)) {
					sysPermissionService.deletePermission(id);
				}
			}
			result.success("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.error500("删除成功!");
		}
		return result;
	}
	
	/**
	  *  获取全部的权限树
	 * @return
	 */
	@RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
	public Result<Map<String,Object>> queryTreeList() {
		Result<Map<String,Object>> result = new Result<>();
		//全部权限ids
		List<String> ids = new ArrayList<>();
		try {
			LambdaQueryWrapper<SysPermission> query = new LambdaQueryWrapper<SysPermission>();
			query.eq(SysPermission::getDelFlag, 0);
			query.orderByAsc(SysPermission::getSortNo);
			List<SysPermission> list = sysPermissionService.list(query);
			for(SysPermission sysPer : list) {
				ids.add(sysPer.getId());
			}
			
			System.out.println(list.size());
			List<TreeModel> treeList = new ArrayList<>();
			getTreeModelList(treeList, list, null);
			
			Map<String,Object> resMap = new HashMap<String,Object>();
			resMap.put("treeList", treeList); //全部树节点数据
			resMap.put("ids", ids);//全部树ids
			result.setResult(resMap);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 异步加载数据节点
	 * @return
	 */
	@RequestMapping(value = "/queryListAsync", method = RequestMethod.GET)
	public Result<List<TreeModel>> queryAsync(@RequestParam(name="pid",required=false) String parentId) {
		Result<List<TreeModel>> result = new Result<>();
		try {
			List<TreeModel> list = sysPermissionService.queryListByParentId(parentId);
			if(list==null||list.size()<=0) {
				result.error500("未找到角色信息");
			}else {
				result.setResult(list);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	/**
	  *  查询角色授权
	 * @return
	 */
	@RequestMapping(value = "/queryRolePermission", method = RequestMethod.GET)
	public Result<List<String>> queryRolePermission(@RequestParam(name="roleId",required=true) String roleId) {
		Result<List<String>> result = new Result<>();
		try {
			List<SysRolePermission> list = sysRolePermissionService.list(new QueryWrapper<SysRolePermission>().lambda().eq(SysRolePermission::getRoleId, roleId));
			result.setResult(list.stream().map(SysRolePermission -> String.valueOf(SysRolePermission.getPermissionId())).collect(Collectors.toList()));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	  *  保存角色授权
	 * @return
	 */
	@RequestMapping(value = "/saveRolePermission", method = RequestMethod.POST)
	public Result<String> saveRolePermission(@RequestBody JSONObject json) {
		Result<String> result = new Result<>();
		try {
			String roleId = json.getString("roleId");
			String permissionIds = json.getString("permissionIds");
			this.sysRolePermissionService.saveRolePermission(roleId, permissionIds);
			result.success("保存成功！");
		} catch (Exception e) {
			result.error500("授权失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	
	private void getTreeList(List<SysPermissionTree> treeList,List<SysPermission> metaList,SysPermissionTree temp) {
		for (SysPermission permission : metaList) {
			String tempPid = permission.getParentId();
			SysPermissionTree tree = new SysPermissionTree(permission);
			if(temp==null && oConvertUtils.isEmpty(tempPid)) {
				treeList.add(tree);
				if(tree.getIsLeaf()==0) {
					getTreeList(treeList, metaList, tree);
				}
			}else if(temp!=null && tempPid!=null && tempPid.equals(temp.getId())){
				temp.getChildren().add(tree);
				if(tree.getIsLeaf()==0) {
					getTreeList(treeList, metaList, tree);
				}
			}
			
		}
	}
	
	private void getTreeModelList(List<TreeModel> treeList,List<SysPermission> metaList,TreeModel temp) {
		for (SysPermission permission : metaList) {
			String tempPid = permission.getParentId();
			TreeModel tree = new TreeModel(permission);
			if(temp==null && oConvertUtils.isEmpty(tempPid)) {
				treeList.add(tree);
				if(permission.getIsLeaf()==0) {
					getTreeModelList(treeList, metaList, tree);
				}
			}else if(temp!=null && tempPid!=null && tempPid.equals(temp.getKey())){
				temp.getChildren().add(tree);
				if(permission.getIsLeaf()==0) {
					getTreeModelList(treeList, metaList, tree);
				}
			}
			
		}
	}
	/**
	  *  获取菜单JSON数组
	 * @param jsonArray
	 * @param metaList
	 * @param parentJson
	 */
	private void getPermissionJsonArray(JSONArray jsonArray,List<SysPermission> metaList,JSONObject parentJson) {
		for (SysPermission permission : metaList) {
			if(permission.getMenuType()==null) {
				continue;
			}
			String tempPid = permission.getParentId();
			JSONObject json = getPermissionJsonObject(permission);
			if(parentJson==null && oConvertUtils.isEmpty(tempPid)) {
				jsonArray.add(json);
				if(permission.getIsLeaf()==0) {
					getPermissionJsonArray(jsonArray, metaList, json);
				}
			}else if(parentJson!=null && oConvertUtils.isNotEmpty(tempPid) && tempPid.equals(parentJson.getString("id"))){
				if(permission.getMenuType()==0) {
					JSONObject metaJson = parentJson.getJSONObject("meta");
					if(metaJson.containsKey("permissionList")) {
						metaJson.getJSONArray("permissionList").add(json);
					}else {
						JSONArray permissionList = new JSONArray();
						permissionList.add(json);
						metaJson.put("permissionList", permissionList);
					}
					
				}else if(permission.getMenuType()==1) {
					if(parentJson.containsKey("children")) {
						parentJson.getJSONArray("children").add(json);
					}else {
						JSONArray children = new JSONArray();
						children.add(json);
						parentJson.put("children", children);
					}
					
					if(permission.getIsLeaf()==0) {
						getPermissionJsonArray(jsonArray, metaList, json);
					}
				}
			}
			
		
		}
	}
	
	private JSONObject getPermissionJsonObject(SysPermission permission) {
		JSONObject json = new JSONObject();
		//类型(0：一级菜单 1：子菜单  2：按钮)
		if(permission.getMenuType()==2) {
			json.put("action", permission.getPerms());
			json.put("describe", permission.getName());
		}else if(permission.getMenuType()==0||permission.getMenuType()==1) {
			json.put("id", permission.getId());
			if(permission.getUrl()!=null&&(permission.getUrl().startsWith("http://")||permission.getUrl().startsWith("https://"))) {
				json.put("path", MD5Util.MD5Encode(permission.getUrl(), "utf-8"));
			}else {
				json.put("path", permission.getUrl());
			}
			
			//重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
			json.put("name", urlToRouteName(permission.getUrl()));
			
			//是否隐藏路由，默认都是显示的
			if(permission.isHidden()) {
				json.put("hidden",true);
			}
			//聚合路由
			if(permission.isAlwaysShow()) {
				json.put("alwaysShow",true);
			}
			json.put("component", permission.getComponent());
			JSONObject meta = new JSONObject();
			meta.put("title", permission.getName());
			if(oConvertUtils.isEmpty(permission.getParentId())) {
				//一级菜单跳转地址
				json.put("redirect",permission.getRedirect());
				meta.put("icon", oConvertUtils.getString(permission.getIcon(), ""));
			}else {
				meta.put("icon", oConvertUtils.getString(permission.getIcon(), ""));
			}
			if(permission.getUrl()!=null&&(permission.getUrl().startsWith("http://")||permission.getUrl().startsWith("https://"))) {
				meta.put("url", permission.getUrl());
			}
			json.put("meta", meta);
		}
		
		return json;
	}
	
	/**
	  * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-）
	  * 举例： URL = /isystem/role
	  *     RouteName = isystem-role
	 * @return
	 */
	private String urlToRouteName(String url) {
		if(oConvertUtils.isNotEmpty(url)) {
			if(url.startsWith("/")) {
				url = url.substring(1);
			}
			url = url.replace("/", "-");
			return url;
		}else {
			return null;
		}
	}
	
}
