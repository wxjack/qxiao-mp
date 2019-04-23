package com.qxiao.wx.common.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.qxiao.wx.common.ConstPool;
import com.spring.jpa.service.ServiceException;

public class UploadUtils {

	private static Logger log = Logger.getLogger(UploadUtils.class);

	public static Map<String, List<Map<String, String>>> downloadImage(String accessToken, JSONArray mediaIds) {
		if (StringUtils.isBlank(accessToken) || mediaIds.length() < 1) {
			return null;
		}
		Map<String, List<Map<String, String>>> images = new HashMap<>();
		List<Map<String, String>> pathList = new ArrayList<>();
		String requestUrl = ConstPool.ACCESS_TOKEN_URL + accessToken;
		URL url;
		BufferedInputStream bis = null;
		HttpURLConnection conn = null;
		try {
			for (Object id : mediaIds) {
				url = new URL(requestUrl + "&media_id=" + id);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setRequestMethod("GET");
				bis = new BufferedInputStream(conn.getInputStream());
				String path = ConstPool.ip;
				String localFilePath = ConstPool.ROOT_PATH;
				// 根据内容类型获取扩展名
				String fileExt = conn.getHeaderField("Content-Type").split("/")[1];
				if (ConstPool.IMG_FOTMAT.contains(fileExt)) {
					path += ConstPool.IMAGE_URL;
					localFilePath += ConstPool.IMAGE_URL;
				} else if (ConstPool.VIDEO_FORMAT.contains(fileExt)) {
					localFilePath += ConstPool.VIDEO_URL;
					path += ConstPool.VIDEO_URL;
				} else {
					throw new ServiceException("文件格式不对...");
				}
				String fileName = UUID.randomUUID() + "." + fileExt;// 图片存储路径

				File file = new File(localFilePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file + "/" + fileName);
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1) {
					fos.write(buf, 0, size);
				}
				fos.close();
				path += "/" + fileName;
				Map<String, String> map = new HashMap<>();
				map.put("imageUrl", path);
				pathList.add(map);
			}
		} catch (Exception e) {
			log.error("上传失败..." + e.getMessage());
			return null;
		} finally {
			try {
				if(bis!=null) {
					bis.close();
					conn.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		images.put("paths", pathList);
		return images;
	}

	public static void deleteImage(String url) throws ServiceException {
		if (StringUtils.isNotEmpty(url)) {
			String filename = url.substring(url.lastIndexOf("/") + 1);
			String ext = filename.substring(filename.lastIndexOf(".") + 1);
			String filePath = ConstPool.ROOT_PATH + ConstPool.IMAGE_URL + "/" + filename;
			if (ConstPool.VIDEO_FORMAT.contains(ext)) {
				filePath = ConstPool.ROOT_PATH + ConstPool.VIDEO_URL + "/" + filename;
			}
			File file = new File(filePath);
			file.delete();
		}
	}

}
