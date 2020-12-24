package com.milesbone.login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.milesbone.entity.UserInfo;

public class HttpLoginWithKapatchTest implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HttpLoginWithKapatchTest.class);

	private String name;
	
	private int id;
	
	private static final String KAPTCHA_URL = "http://127.0.0.1:8090/windforce/kapatchImagAction_createLoginImag.action";
	
	private static final String LOGIN_URL = "http://127.0.0.1:8090/windforce/j_spring_security_check";
	
	private static final String WELCOME_URL = "http://127.0.0.1:8090/windforce/login.action";
	
	private static final String HOME_STATISTIC_URL = "http://127.0.0.1:8090/windforce/hpCache_homePageFlowStatistics.action";
	
	private static CookieStore cookieStore = null;
	 
	private static HttpClientContext context = null;

	private static final String PIC_PATH = System.getProperty("user.dir") + File.separator + "pic";

	public static final List<UserInfo> list = Collections.synchronizedList(new ArrayList<UserInfo>());
	
	static{
		
		list.add(new UserInfo("hdyf", "888888"));
		list.add(new UserInfo("sqcw", "888888"));
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

	public HttpLoginWithKapatchTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HttpLoginWithKapatchTest(String name) {
		super();
		this.name = name;
	}
	
	

	public HttpLoginWithKapatchTest(int id) {
		super();
		this.id = id;
	}

	public void run() {

		String destFilepath = PIC_PATH + File.separator + UUID.randomUUID() + ".jpg";
		File destFile = new File(destFilepath);
		if (destFile.exists()) {
			destFile.delete();
		}

		// 第一步：先下载验证码到本地
		logger.debug("先下载验证码到本地");
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		HttpGet get = new HttpGet(KAPTCHA_URL);
		CloseableHttpResponse response = null;
		InputStream in = null;
		FileOutputStream fout;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(get);
			entity = response.getEntity();

			in = entity.getContent();
			fout = new FileOutputStream(destFile);
			int l = -1;
			byte[] tmp = new byte[2048];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp);
			}
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		get.releaseConnection();
		logger.debug("下载验证码到本地完成路径:{}" ,destFile );

		String result = "";
		// 第二步: 识别验证码图片
//		logger.debug("识别验证码图片开始");
//		ITesseract instance = new Tesseract(); //
//		
//		try {
//			
//			result = instance.doOCR(destFile);
//			logger.info("识别验证码图片结果:" + result);
//
//		} catch (TesseractException e) {
//			logger.error(e.getMessage());
//		}
//		logger.debug("识别验证码图片结束");
		
		//第三步:模拟登录 
		logger.debug("模拟登录开始");
		UserInfo user = list.get(id);
		logger.debug("得到用户:{}",user.toString());
		HttpPost post = new HttpPost(LOGIN_URL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("loginType", "pwdLogin"));
		params.add(new BasicNameValuePair("systemModule", "0"));
		params.add(new BasicNameValuePair("j_username", user.getUsercode()));
		params.add(new BasicNameValuePair("j_password", user.getPassword()));
		params.add(new BasicNameValuePair("login-kaptcha-imgId", result));
		params.add(new BasicNameValuePair("_spring_security_remember_me", "true"));
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
			response = httpClient.execute(post);
			entity = response.getEntity();
			logger.debug("返回消息:{}", EntityUtils.toString(entity));
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		post.releaseConnection();
		logger.debug("模拟登录结束..");

		logger.debug("登录Action");
		HttpPost homePost = new HttpPost(LOGIN_URL);
		try {
			response = httpClient.execute(homePost);
			entity = response.getEntity();
			logger.debug(EntityUtils.toString(entity));
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		homePost.releaseConnection();
		logger.debug("登录Action结束");
		
		
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		File directory = new File("pic");
		if (!directory.exists()) {
			directory.mkdirs();
		}

		new HttpLoginWithKapatchTest(0).run();

//		 ExecutorService service = Executors.newFixedThreadPool(50);
//		 for (int i = 0; i < 50; i++) {
//		 service.execute(new HttpRequestTest(i%13));// 并发50个用户
//		 }
//		deleteDir(directory, false);
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir, boolean include) {
		logger.debug("删除文件夹下所有的文件");
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]),true);
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		if(include){
			return dir.delete();
		}
		return true;
	}
}
