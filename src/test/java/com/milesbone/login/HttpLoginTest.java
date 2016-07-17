package com.milesbone.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.milesbone.entity.UserInfo;


public class HttpLoginTest implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HttpLoginTest.class);

	private String name;
	
	private int id;
	
	
	private static final String LOGIN_URL = "http://127.0.0.1:8090/windforce/j_spring_security_check";
	
	
	private static final String HOME_STATISTIC_URL = "http://127.0.0.1:8090/windforce/hpCache_homePageFlowStatistics.action";
	
	private static CookieStore cookieStore = null;
	 
	public static final List<UserInfo> list = Collections.synchronizedList(new ArrayList<UserInfo>());
	
	static{
		
		list.add(new UserInfo("hdyf", "888888"));
		list.add(new UserInfo("hdyf001", "888888"));
		list.add(new UserInfo("hdyf002", "888888"));
		list.add(new UserInfo("hdyf003", "888888"));
		list.add(new UserInfo("hdyf004", "888888"));
		list.add(new UserInfo("hdyf005", "888888"));
		list.add(new UserInfo("hdyf006", "888888"));
		list.add(new UserInfo("hdyf007", "888888"));
		list.add(new UserInfo("hdyf008", "888888"));
		list.add(new UserInfo("hdyf009", "888888"));
		list.add(new UserInfo("hdyf010", "888888"));
		list.add(new UserInfo("hdyf011", "888888"));
		list.add(new UserInfo("hdyf012", "888888"));
		list.add(new UserInfo("hdyf013", "888888"));
		list.add(new UserInfo("hdyf014", "888888"));
		list.add(new UserInfo("hdyf015", "888888"));
		list.add(new UserInfo("sqcw", "888888"));
		list.add(new UserInfo("sqcw001", "888888"));
		list.add(new UserInfo("sqcw002", "888888"));
		list.add(new UserInfo("sqcw003", "888888"));
		list.add(new UserInfo("sqcw004", "888888"));
		list.add(new UserInfo("sqcw005", "888888"));
		list.add(new UserInfo("hdcs", "888888"));
		list.add(new UserInfo("hdkf", "888888"));
		list.add(new UserInfo("hclt", "888888"));
		list.add(new UserInfo("hdcp", "888888"));
		list.add(new UserInfo("hdyx", "888888"));
		list.add(new UserInfo("hdnc", "888888"));
		list.add(new UserInfo("hwht", "888888"));
		list.add(new UserInfo("cdyh", "888888"));
		list.add(new UserInfo("hnzx", "888888"));
		list.add(new UserInfo("hrjx", "888888"));
		list.add(new UserInfo("tqtz", "888888"));
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HttpLoginTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HttpLoginTest(String name) {
		super();
		this.name = name;
	}
	
	

	public HttpLoginTest(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public HttpLoginTest(int id) {
		super();
		this.id = id;
	}

	public void run() {


		// 第一步：初始化
//		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy(); 
		RequestConfig requestConfig = RequestConfig.custom()
										.setCookieSpec(CookieSpecs.DEFAULT)
										.setRedirectsEnabled(true)
										.build();
		CloseableHttpClient httpClient = HttpClients.custom()
//										.setRedirectStrategy(redirectStrategy)
										.setDefaultRequestConfig(requestConfig)
										.setDefaultCookieStore(cookieStore)
										.build();
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		
		//第二步:模拟登录 
		logger.info("模拟登录开始");
		UserInfo user = list.get(id);
		logger.info("得到用户:{}",user.toString());
		HttpPost post = new HttpPost(LOGIN_URL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("loginType", "pwdLogin"));
		params.add(new BasicNameValuePair("systemModule", "0"));
		params.add(new BasicNameValuePair("j_username", user.getUsercode()));
		params.add(new BasicNameValuePair("j_password", user.getPassword()));
		params.add(new BasicNameValuePair("_spring_security_remember_me", "true"));
		
		String newuri = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
			response = httpClient.execute(post);
			
			
			if(response.getStatusLine().getStatusCode()==302){  
				Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
			    newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
			    logger.info("重定向的URI: {}",newuri);
			    
			    post = new HttpPost("http://127.0.0.1:8090"+newuri);
			    post.setEntity(new UrlEncodedFormEntity(params));
			    response = httpClient.execute(post);
			    logger.error("执行后的状态码:{}", response.getStatusLine().getStatusCode());
			}  
			System.out.println("返回消息:{}" + response.toString());
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		post.releaseConnection();
		logger.info("模拟登录结束..");

		logger.info("进入主页");
		HttpPost homePost = new HttpPost(HOME_STATISTIC_URL);
		try {
			response = httpClient.execute(homePost);
			entity = response.getEntity();
			logger.info(EntityUtils.toString(entity));
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		homePost.releaseConnection();
		logger.info("登录Action结束,线程id: {},name:{}", this.id,this.name);
		
		
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		new HttpLoginTest(0).run();

		 ExecutorService service = Executors.newFixedThreadPool(500);
		 for (int i = 0; i < 500; i++) {
			 service.execute(new HttpLoginTest("theadName"+i,i%33));// 并发50个用户
		 }
		 service.shutdown();
		 logger.info("i={},execute finish");
//		deleteDir(directory, false);
	}

}
