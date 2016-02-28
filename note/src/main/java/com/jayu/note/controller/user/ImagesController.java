package com.jayu.note.controller.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.jayu.note.common.Const;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jayu.note.bmo.ArticleFileBmo;
import com.jayu.note.dao.model.ArticleFile;
import com.jayu.note.domain.SessionAdmin;
import com.jayu.note.domain.SysConstant;
import com.jayu.common.util.DateUtil;
import com.jayu.common.util.UIDGenerator;
import com.jayu.exception.BusinessException;
import com.jayu.exception.PortalCheckedException;
import com.jayu.file.util.ImageUtil;
import com.jayu.web.ServletUtils;
import com.jayu.web.spring.controller.BaseController;

/**
 * 商品图片控制类 . <BR>
 * 商品图片上传，列表，商品图片目录创建.
 * <P>
 * 
 * @author jayu
 * @version V1.0 2012-7-23
 * @createDate 2012-7-23 下午11:05:00
 * @modifyDate jayu 2012-7-23 <BR>
 */
@Controller("user.ImagesController")
@RequestMapping("/user/images/*")
public class ImagesController extends BaseController {
	@Resource(name = "ArticleFileBmoImpl")
	ArticleFileBmo articleFileBmo;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map singleImgUpload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws PortalCheckedException {
	 	SessionAdmin sessionAdmin = (SessionAdmin) ServletUtils.getSessionAttribute(request,
                SysConstant.SESSION_KEY_LOGIN_USER);
	 	Map json = new HashMap();
		
	 	try {
			int count=articleFileBmo.getCountByUser(sessionAdmin.getId());
			if(count > Const.CONST_MAX_FILE){
				json.put("success", 0);
				json.put("message", "允许个人上传的文件数在服务器上不能超过 "+Const.CONST_MAX_FILE+" 件！");
				return json;
			}
		} catch (BusinessException e1) {
			log.error(e1);
		}
		response.setCharacterEncoding("UTF-8");
		MultipartFile file = request.getFile("editormd-image-file");
		// 如果请求中存在文件夹名称，则定位到文件夹中
		String dir=Const.CONST_UPLOAD_IMAGE_DIR;
		if("".equals(dir)){
			dir= request.getSession().getServletContext()
					.getRealPath("/");
		}
		int len=dir.length();
		if(!(dir.lastIndexOf("/")==len-1 || dir.lastIndexOf("\\")==len-1)){
			dir=dir+File.separator+ SysConstant.FILE_UPLOAD_DIR;
		}else{
			dir=dir + SysConstant.FILE_UPLOAD_DIR;
		}
		String ym = DateUtil.getNow("yyyyMM");
		File folder = new File(dir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		ArticleFile entity = new ArticleFile();
		entity.setUserId(sessionAdmin.getId());
		entity.setCreateDt(new Timestamp(System.currentTimeMillis()));
		String newFileName = ImageUtil.genFileName(ImageUtil.getExt(file
				.getOriginalFilename()));
		File destFile=null;
		try {
			log.debug("realPath={}", dir);
			entity.setId(UIDGenerator.generateNonceYYmd(14));
			entity.setUrl("/" + SysConstant.FILE_UPLOAD_DIR + "/"
					+ SysConstant.FILE_UPLOAD_DIR_N9 + "/" + ym + "/"
					+ newFileName);
			entity.setName(ImageUtil.getRealFileName(file
					.getOriginalFilename()));
			entity.setType(ImageUtil.getExt(file.getOriginalFilename()));
			String destFilePath=dir + File.separator+newFileName;
			long size0=0;
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destFilePath));
			destFile=new File(destFilePath);
			destFilePath=dir
					+ File.separator + SysConstant.FILE_UPLOAD_DIR_N9
					+ File.separator + ym + File.separator
					+ newFileName;
			size0=ImageUtil.createThumbnailByMaxWH(destFile, 
					destFilePath, SysConstant.IMAGE_WIDTH_800,
					SysConstant.IMAGE_HEIGHT_3500);
		
			
			entity.setSize(new BigDecimal(size0)
					.divide(new BigDecimal(1024)));
			articleFileBmo.insert(entity);
		} catch (BusinessException e) {
			log.error(e);
			json.put("success", 0);
			json.put("message", "上传异常！");
			return json;
		} catch (IOException e) {
			log.error(e);
			json.put("success", 0);
			json.put("message", "上传异常！");
			return json;
		}finally{
			if(destFile!=null){
				destFile.delete();
			}
		}
		
		json.put("success", 1);
		json.put("url", Const.CONST_SITE_URL+entity.getUrl());
		return json;
	}

}
