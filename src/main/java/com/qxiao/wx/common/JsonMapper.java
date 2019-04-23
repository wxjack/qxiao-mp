package com.qxiao.wx.common;

import java.io.IOException;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

	private static ObjectMapper mapper = new ObjectMapper();

	private JsonMapper() {
	}

	/**
	 * 获取mapper对象
	 * 
	 * @return
	 */
	public static ObjectMapper getMapper() {
		return mapper;
	}

	/**
	 * 转String
	 * 
	 * @param object
	 * @return
	 */
	public static String obj2String(Object object) {
		try {
			if (StringUtils.isEmpty(object)) {
				return null;
			}
			return getMapper().writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转具体实例
	 * 
	 * @param src
	 * @param clazz
	 * @return
	 */
	public static <T> T obj2Instance(String str, Class<T> clazz) {
		try {
			if (StringUtils.isEmpty(str)) {
				return null;
			}
			return getMapper().readValue(str, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Integer> toMap(String str) {
		try {
			return getMapper().readValue(str, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
//		Map<String, Long> map = toMap("{\"classId\":1}");
//		Set<Entry<String, Long>> entrySet = map.entrySet();
//		for (Entry<String, Long> entry : entrySet) {
//			System.out.println(entry.getValue());
//		}
	}
}
