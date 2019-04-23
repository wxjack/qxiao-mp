/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qxiao.wx.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此类接收CB上传的Beacon扫描数据
 * 
 * @author admin
 */
public class HttpPostDataServlet extends HttpServlet {
	public static String beaconData = "未接收到数据";

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try {
			// 接收前端数据
			beaconData = StreamToString(request);
			System.out.println("beaconData-----==" + beaconData);
			request.setAttribute("HttpPostData", beaconData);
			response.getWriter().print("數據：" + beaconData);
		} catch (IOException e) {
			println(e.getMessage());
		}
	}

	private String StreamToString(HttpServletRequest request) throws IOException {
		int totalbytes = request.getContentLength();
		if (totalbytes <= 0)
			return "";

		byte[] bytes = new byte[1024 * 1024];
		InputStream is = request.getInputStream();
		int nRead = 1;
		int nTotalRead = 0;
		while (nRead > 0) {
			nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
			if (nRead > 0)
				nTotalRead = nTotalRead + nRead;
		}

		String reqcontent = new String(bytes, 0, nTotalRead, "utf-8");
		return reqcontent;
	}

	public static void println(String strOut) {
		if (strOut == null)
			return;

		try {
			System.out.write(strOut.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			System.out.println(strOut);
		} catch (IOException ex) {
			System.out.println(strOut);
		}
	}
}
