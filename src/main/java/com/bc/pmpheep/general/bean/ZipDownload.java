package com.bc.pmpheep.general.bean;

import java.io.File;
import java.sql.Timestamp;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.general.controller.FileDownLoadController;

/**
 * 
 * 
 * 功能描述：word文件导出工具类
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月14日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
public class ZipDownload implements Runnable {
	/**
	 * 唯一标识
	 */
	private String id;
	/**
	 * 状态详情
	 */
	private String detail;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 教材名称
	 */
	private String materialName;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	/**
	 * 根据路径删除指定的目录，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录path
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000 * 60 * 30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (FileDownLoadController.map.containsKey(id)) {
			String src = this.getClass().getResource("/").getPath();
			src = src.substring(1);
			if (!src.endsWith(File.separator)) {
				src += File.separator;
			}
			FileDownLoadController.map.remove(id);
			DeleteFolder(src + id);
		}
	}

	@Override
	public String toString() {
		return "ZipDownload [id=" + id + ", detail=" + detail + ", state=" + state + ", materialName=" + materialName
				+ ", createTime=" + createTime + "]";
	}

}
