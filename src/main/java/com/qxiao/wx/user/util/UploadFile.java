package com.qxiao.wx.user.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// MultipartFile上传文件
	public String getFileInfo(MultipartFile file) {
		String filePath = new String();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String format = df.format(Calendar.getInstance().getTime());
		// String uploadPath =
		// request.getSession().getServletContext().getRealPath("./")+format;
		String uploadPath = "E:\\home\\xiaojiao\\" + format;
		// 如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件的保存路径
				filePath = uploadPath + File.separator + UUID.randomUUID()
						+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				System.out.println(filePath);
				// 转存文件
				// MD5Utils.getFileMD5(file);
				file.transferTo(new File(filePath));// filePath
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}
}