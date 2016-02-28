package com.jayu.note;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import com.jayu.note.common.Const;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import com.jayu.freemarker.directive.CustomOverrideTemplate;
import com.jayu.log.Log;
import com.jayu.web.spring.controller.ServletActionContext;

import freemarker.core.Environment;

/**
 * 模板模块内容从缓存取，
 * 或硬盘文件里，
 * 或 数据库里取
 * <P>
 * @author jayu
 * @version V1.0 2012-12-8
 * @createDate 2012-12-8 上午11:03:36
 * @modifyDate jayu 2012-12-8 <BR>
 */
public class CacheCustomTemplate implements CustomOverrideTemplate {
	static final Log log =Log.getLog(CacheCustomTemplate.class);
	public String getOverrideContent(Environment env, Map params) {
		String fileName=String.valueOf(params.get("name"));
		log.debug("query parma={}", ServletActionContext.getRequest().getQueryString());
		String dir="";
		if(params.containsKey("path")){
			dir= Const.CONST_CACHE_DIR+String.valueOf(params.get("path"));
		}else{
			dir=Const.CONST_CACHE_DIR+ServletActionContext.getPathWithinHandlerMapping()+"/";
		}
		dir=dir.replace("\\", "/").replace("//", "/");
		File dirFile=new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		File file=new File(dir+fileName+".html");
		try {
			if(file.isFile())return FileUtils.readFileToString(file,  env.getTemplate().getEncoding());
		} catch (IOException e) {
			log.warn("加载缓存文件失败", e);
		}
		return null;
	}
	public void saveOverrideContent(String content,Environment env, Map params) {
		try {
			String fileName=String.valueOf(params.get("name"));
			String dir="";
			if(params.containsKey("path")){
				dir=Const.CONST_CACHE_DIR+String.valueOf(params.get("path"));
			}else{
				 dir=Const.CONST_CACHE_DIR+ServletActionContext.getPathWithinHandlerMapping()+"/";
			}
			dir=dir.replace("\\", "/").replace("//", "/");
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir+fileName+".html"), env.getTemplate().getEncoding()));
			FileCopyUtils.copy(content,out);
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
		} catch (FileNotFoundException e) {
			log.error("",e);
		} catch (IOException e) {
			log.error("",e);
		}
		
	}

}
