package com.talkweb.weixin.pojo;

public class JSSDK {
	// 微信加密签名
	private static String SIGNATURE;
	// 时间戳
	private static String TIMESTAPP;
	// 随机串
	private static String NONCESTR = "1246546";
	// 密文
	private static String ENCERYPT;

	private static String TICKET;

	public static String getTICKET() {
		return TICKET;
	}

	public static void setTICKET(String tICKET) {
		TICKET = tICKET;
	}

	public static String getSIGNATURE() {
		return SIGNATURE;
	}

	public static void setSIGNATURE(String sIGNATURE) {
		SIGNATURE = sIGNATURE;
	}

	public static String getTIMESTAPP() {
		return TIMESTAPP;
	}

	public static void setTIMESTAPP(String tIMESTAPP) {
		TIMESTAPP = tIMESTAPP;
	}

	public static String getNONCESTR() {
		return NONCESTR;
	}

	public static void setNONCESTR(String nONCESTR) {
		NONCESTR = nONCESTR;
	}

	public static String getENCERYPT() {
		return ENCERYPT;
	}

	public static void setENCERYPT(String eNCERYPT) {
		ENCERYPT = eNCERYPT;
	}

}
