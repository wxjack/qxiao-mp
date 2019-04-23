package com.qxiao.wx.common;

public interface ConstPool {

	// 图片根路径
	String ROOT_PATH = "E://qxiao_image/static";
	
	// 静态资源完成路径
	String ROOT_URL_IMAGE = "E://qxiao_image/static/image";
	String ROOT_URL_VIDEO = "E://qxiao_image/static/video";
	String ROOT_URL_VOICE="E://qxiao_image/static/image";

	// 请求图片相对路径
	String IMAGE_URL = "/image";
	String server_name = "http://192.168.18.113";
	// 视频相对路径
	String VIDEO_URL = "/video";
	String VOICE_URL="/image";
	// ip 地址
	String ip = "http://192.168.18.113";

	// access_token 接口地址
	String ACCESS_TOKEN_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
	
	// jsapi_ticket
	String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	String IMG_FOTMAT = "jpg,png,gif,bmp,psd,jpeg,pcx";

	String VIDEO_FORMAT = "rm,rmvb,mp4,mtv,wmv,flv,mov";
}
