package org.jeecg.common.system.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hero.renche.entity.FileRel;
import org.hero.renche.service.IFileRelService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.system.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/sys/common")
public class CommonController {

	@Value(value = "${uploadpath}")
	private String uploadpath;

	@Autowired
	private IFileRelService fileRelService;


	@PostMapping(value = "/upload")
	public Result<SysUser> upload(HttpServletRequest request, HttpServletResponse response) {
		Result<SysUser> result = new Result<>();
		try {
			String ctxPath = uploadpath;
			String fileName = null;
			String bizPath = "user";
			String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
			File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
			if (!file.exists()) {
				file.mkdirs();// 创建文件根目录
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
			String orgName = mf.getOriginalFilename();// 获取文件名
			fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
			String savePath = file.getPath() + File.separator + fileName;
			File savefile = new File(savePath);
			FileCopyUtils.copy(mf.getBytes(), savefile);
			String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
			if (dbpath.contains("\\")) {
				dbpath = dbpath.replace("\\", "/");
			}
			FileRel fileRel = new FileRel();
			fileRel.setFileName(orgName);
			fileRel.setFileUrl(dbpath);
			Map<String,Object> fileMap = fileRelService.fileUpload(fileRel);
			Boolean resultOk = Boolean.parseBoolean(fileMap.get("flag").toString());
			if(resultOk){
				result.setSuccess(true);
				result.setMessage(fileMap.get("fileRelId").toString());
			}else {
				result.setMessage("上传失败！");
				result.setSuccess(false);
			}
		} catch (IOException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 预览图片
	 * 请求地址：http://localhost:8080/common/view/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
	 * 
	 * @param request
	 * @param response
	 */
	@PostMapping(value = "/view/**")
	public void view(HttpServletRequest request, HttpServletResponse response) {
		// ISO-8859-1 ==> UTF-8 进行编码转换
		String imgPath = extractPathFromPattern(request);
		// 其余处理略
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			imgPath = imgPath.replace("..", "");
			if (imgPath.endsWith(",")) {
				imgPath = imgPath.substring(0, imgPath.length() - 1);
			}
			response.setContentType("image/jpeg;charset=utf-8");
			String localPath = uploadpath;
			String imgurl = localPath + File.separator + imgPath;
			inputStream = new BufferedInputStream(new FileInputStream(imgurl));
			outputStream = response.getOutputStream();
/*			byte[] buf = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}*/
			response.flushBuffer();
		} catch (IOException e) {
			log.info("预览图片失败" + e.getMessage());
			// e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	  *  把指定URL后的字符串全部截断当成参数 
	  *  这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
	 * @param request
	 * @return
	 */
	private static String extractPathFromPattern(final HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
	}




	@GetMapping(value = "/download")
	public HttpServletResponse download(@RequestParam(name = "fileRelId") String fileRelId, HttpServletResponse response) {
		     try {

		     		String path = fileRelService.qryFileRelKey(fileRelId);
		     		String uploadpaths = uploadpath.replace("\\","/");
		     		path = uploadpaths + "/" + path;
			       // path是指欲下载的文件的路径。
			       File file = new File(path);
			       // 取得文件名。
			       String filename = file.getName();
			       // 取得文件的后缀名。
			       //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

			       // 以流的形式下载文件。
			       InputStream fis = new BufferedInputStream(new FileInputStream(path));
			       byte[] buffer = new byte[fis.available()];
			       fis.read(buffer);
			       fis.close();
			       // 清空response
			       response.reset();
			      // 设置response的Header
			       response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"),"ISO-8859-1"));
			       response.addHeader("Content-Length", "" + file.length());
			       OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			       response.setContentType("application/octet-stream");
			       toClient.write(buffer);
			       toClient.flush();
			       toClient.close();
			     } catch (IOException ex) {
			       ex.printStackTrace();
			     }
		     return response;
		   }

}
