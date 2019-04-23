package com.talkweb.weixin.main;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.talkweb.weixin.pojo.JSSDK;
import com.talkweb.weixin.util.LogUtil;

public class StartOnLoad implements ServletContextListener {
	private static LogUtil log=new LogUtil(StartOnLoad.class);
	
	//获取access_token的APPID;
	public static String TOKEN_APPID;
	
	//获取access_token的secret;
	public static String TOKEN_SECRET;
	
	//微信校验token // 与微信接口配置信息中的Token要一致  
	public static String TOKEN;
	
	// 模板Id
	public static String PUNCH_ID;
	public static String HOMEWORK_ID;
	public static String NOTICE_ID;
	public static String FRESH_ID;
	public static String RECIPE_ID;
	//实时播报次数
	public static String REALSHUTTLE;
	
	public static String WEBTTS_URL;
	
	public static String APPID;
	
	public static String API_KEY;
	
	//实时播报开始时间
	public static String START_TIME;
	//实时播报结束时间
	public static String END_TIME;
	
	//微信子线程组
	public static TokenThread tokenThread1;


	public static Thread sendMsgThread;

	
	//是否需要创建菜单
	public String isCreateMenu;
	
	//项目的URL
	public static String LOCAL_URL;
	
	public static String GOAL_URL;
	public static String PIC_URL;
	public static String GOAL_LOCAL_URL;
	public static String WS_URL;
	public static String QIYE_AGENTID;
	public void contextDestroyed(ServletContextEvent arg0) {

	}
	
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		//读取数据库初始配置文件		
		//定义配置文件
		Properties dbProperties=new Properties();
		//定义输入流
		InputStream in=null;
		try{
			log.info("+++++读取连接配置文件++++++");
			in=StartOnLoad.class.getResourceAsStream("/resources.properties");
			dbProperties.load(in);
			TOKEN_APPID=dbProperties.getProperty("token.appId");
			TOKEN_SECRET=dbProperties.getProperty("token.secret");
			TOKEN=dbProperties.getProperty("token");
			PUNCH_ID = dbProperties.getProperty("punch_id");
			FRESH_ID = dbProperties.getProperty("fresh_id");
			HOMEWORK_ID = dbProperties.getProperty("homework_id");
			RECIPE_ID = dbProperties.getProperty("recipe_id");
			NOTICE_ID = dbProperties.getProperty("notice_id");
			START_TIME=dbProperties.getProperty("start_time");
			END_TIME=dbProperties.getProperty("end_time");
			REALSHUTTLE=dbProperties.getProperty("realShuttle");
			isCreateMenu=dbProperties.getProperty("isCreateMenu");
			GOAL_LOCAL_URL=dbProperties.getProperty("goalLocalUrl");
			LOCAL_URL=dbProperties.getProperty("localURL");
			GOAL_URL=dbProperties.getProperty("goalURL");
			WEBTTS_URL=dbProperties.getProperty("webtts_url");
			APPID=dbProperties.getProperty("app_id");
			API_KEY=dbProperties.getProperty("api_key");
			
			JSSDK.setENCERYPT(dbProperties.getProperty("encrypt"));

		}catch (Exception e) {
			log.error(e);
		}finally{
			//关闭输入流
			try{
				if(in!=null){
				in.close();
				}
			}catch (Exception e) {
				// TODO: handle exception
				log.error(e);
			}			
		}
		tokenThread1= new TokenThread(TOKEN_APPID, TOKEN_SECRET);
		
		new Thread(tokenThread1).start();		
		log.info("启动消息发送线程.....");
		sendMsgThread = new SendMsgThread();		
		new Thread(sendMsgThread).start();

		//判断是否需要调用创建菜单接口
		if("true".equals(isCreateMenu)){
			MenuManager.CreateMenu();			
			
		}		

	}
}
