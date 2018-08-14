package com.$2012.vo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.utils.ImageSizer;

/*
 * 上传文件（包装类）
 */
@Component("fileCtx")
@Scope("prototype")
public class FileContext {
	/*上传的文件*/
	private File file;
	/*文件名*/
	private String fileFileName;
	/*文件类型*/
	private String fileContentType;
	/*默认压缩后的宽度*/
	private int width = 160;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	/*
	 * simpleDir -- \\images\\brand\\ -- 路径头部
	 * fileSize -- 1024*1024*10 -- 限定上传文件大小10M
	 */
	public String makeUploadPath(FileContext fileCtx, String simpleDir, long fileSize) {
		if (fileCtx.getFile() == null) return "";//未上传文件时，文件路径为""
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/HH");
		String filePath = ServletActionContext.getServletContext()
		.getRealPath(simpleDir + sdf.format(new Date()));
		File filePathDir = new File(filePath);
		if (!filePathDir.exists()) filePathDir.mkdirs();
		if (fileCtx.getFile().length() > fileSize) return "toobig";
		String fileFileName = fileCtx.getFileFileName();
		File target = new File(filePathDir + "\\prototype", fileFileName);
		try {
			//将上传的文件从临时目录拷贝到target目录
			FileUtils.copyFile(fileCtx.getFile(), target);
			//将target目录里的文件压缩保存到filePathDir
			ImageSizer.resize(target, new File(filePathDir, fileFileName), fileCtx.getWidth(), "gif");
		} catch (IOException e) {
			e.printStackTrace();
			return "ioexception";
		}
		//带文件名的路径
		String filePath2 =  filePathDir.getPath().substring(filePathDir.getPath().indexOf(simpleDir)) + "\\" + fileFileName;
		//转换路径分隔符
		String filePath3 = filePath2.replace('\\', '/');
		return filePath3;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
}
