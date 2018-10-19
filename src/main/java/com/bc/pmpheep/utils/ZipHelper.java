/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.stereotype.Component;

/**
 * 压缩工具类，依赖zip4j
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class ZipHelper {

	/**
	 * 解压加密的压缩文件
	 *
	 * @param file
	 *            要解压的文件（目录）
	 * @param dest
	 *            解压路径
	 * @param passwd
	 *            压缩密码
	 * @throws ZipException
	 *             压缩异常
	 */
	public void unZip(File file, String dest, String passwd) throws ZipException {
		ZipFile zipFile = new ZipFile(file);
		// zfile.setFileNameCharset("GBK");//在GBK系统中需要设置
		if (!zipFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法，可能已经损坏！");
		}
		File tmp = new File(dest);
		if (tmp.isDirectory() && !tmp.exists()) {
			tmp.mkdirs();
		}
		if (zipFile.isEncrypted()) {
			zipFile.setPassword(passwd.toCharArray());
		}
		zipFile.extractAll(dest);
	}

	/**
	 * 压缩文件并加密（压缩密码为空时不加密）
	 *
	 * @param src
	 *            要压缩的源文件(目录)地址
	 * @param dest
	 *            目标路径
	 * @param isCreateDir
	 *            如果路径不存在是否创建
	 * @param passwd
	 *            压缩密码
	 */
	public void zip(String src, String dest, boolean isCreateDir, String passwd) {
		File srcfile = new File(src);
		// 创建目标文件
		String destname = buildDestFileName(srcfile, dest);
		ZipParameters para = new ZipParameters();
		para.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		para.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		if (passwd != null) {
			para.setEncryptFiles(true);
			para.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			para.setPassword(passwd.toCharArray());
		}
		try {
			ZipFile zipfile = new ZipFile(destname);
			if (srcfile.isDirectory()) {
				if (!isCreateDir) {
					File[] listFiles = srcfile.listFiles();
					ArrayList<File> temp = new ArrayList<>();
					Collections.addAll(temp, listFiles);
					zipfile.addFiles(temp, para);
				}
				zipfile.addFolder(srcfile, para);
			} else {
				zipfile.addFile(srcfile, para);
			}
		} catch (ZipException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * 创建目标文件名称
	 *
	 * @param file
	 *            文件（目录）
	 * @param dest
	 *            目标路径
	 * @return 返回目标文件名称
	 */
	private String buildDestFileName(File file, String dest) {
		if (dest == null) {// 没有给出目标路径时
			if (file.isDirectory()) {
				dest = file.getParent() + File.separator + file.getName() + ".zip";
			} else {
				String filename = file.getName().substring(0, file.getName().lastIndexOf("."));
				dest = file.getParent() + File.separator + filename + ".zip";
			}
		} else {
			createPath(dest);// 路径的创建
			if (dest.endsWith(File.separator)) {
				String filename;
				if (file.isDirectory()) {
					filename = file.getName();
				} else {
					//filename = file.getName().substring(0, file.getName().lastIndexOf(".")>-1?file.getName().lastIndexOf("."):file.getName().length());
					filename = file.getName().replaceAll("\\.[^\\.]*$","");
				}
				dest += filename + ".zip";
			}
		}
		return dest;
	}

	/**
	 * 路径创建
	 *
	 * @param dest
	 *            要创建的路径地址
	 */
	private void createPath(String dest) {
		File destDir;
		if (dest.endsWith(File.separator)) {
			destDir = new File(dest);// 给出的是路径时
		} else {
			destDir = new File(dest.substring(0, dest.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}
}
