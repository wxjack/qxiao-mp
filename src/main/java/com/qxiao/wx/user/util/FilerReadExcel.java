package com.qxiao.wx.user.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jxl.Sheet;
import jxl.Workbook;

public class FilerReadExcel {
	private static final Logger logger = Logger.getLogger(FilerReadExcel.class);

	public static List<List<String>> readExcel(File file) {
		List<List<String>> list = new ArrayList<>();
		

		try {
			// 创建输入流，读取Excel
			InputStream is = new FileInputStream(file.getAbsolutePath());
			// jxl提供的Workbook类
			Workbook wb = Workbook.getWorkbook(is);
			// Excel的页签数量
			int sheet_size = wb.getNumberOfSheets();
			for (int index = 0; index < sheet_size; index++) {
				// 每个页签创建一个Sheet对象
				Sheet sheet = wb.getSheet(index);
				// sheet.getRows()返回该页的总行数
				for (int i = 10; i < sheet.getRows(); i++) {
					// sheet.getColumns()返回该页的总列数
					List<String> li = new ArrayList<>();
					for (int j = 1; j < sheet.getColumns(); j++) {
						String cellinfo = sheet.getCell(j, i).getContents();
						li.add(cellinfo);
					}
					list.add(li);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return list;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		FilerReadExcel f = new FilerReadExcel();
		List<List<String>> readExcel = f.readExcel(new File("E:\\home\\xiaojiao\\2018-12-11\\e8ede3a6-4856-4b92-9f91-b7da0be3280c.xls"));
		for (List<String> obj : readExcel) {
			System.out.println(obj);
		}
	}
}
