package com.milesbone.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class URLConnectionUtil  {
  
  public static void main(String[] args) {
	  
	  String result = "";
      URL url = null;
      BufferedReader reader = null;
      try {
    	  
    	  JSONObject jo = new JSONObject();
    	  JSONArray ja = new JSONArray();
    	  JSONObject tempJo = new JSONObject();
    	  tempJo.put("taskId", "1_1");
    	  tempJo.put("count", 10);
    	  ja.add(tempJo);
    	  tempJo = new JSONObject();
    	  tempJo.put("taskId", "1_2");
    	  tempJo.put("count", 101);
    	  ja.add(tempJo);
    	  
    	  jo.put("data", ja);
    	  
          String parma = jo.toJSONString();
          System.out.println(parma);
          
          url = new URL("http://120.234.24.43:8080/COM.FOXCONN.API.EXT/StatisticalTask");
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
          // 1分钟读取超时
          connection.setReadTimeout(10000);
          // 30秒连接超时
          connection.setConnectTimeout(10000);
          connection.setDoOutput(true);
          connection.setDoInput(true);
          connection.setUseCaches(false);
          connection.setRequestMethod("POST");
          connection.setRequestProperty("Connection", "Keep-Alive");
          connection.setRequestProperty("Content-Length", String.valueOf(parma.length()));
          connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
          connection.setRequestProperty("accept","application/json");

          OutputStream out = connection.getOutputStream();
          out.write(parma.getBytes());
          out.flush();
          out.close();

          if (connection.getResponseCode()==200){

              reader = new BufferedReader(
                      new InputStreamReader(connection.getInputStream()));
              result = reader.readLine();

          }
      } catch (Exception e) {
          e.printStackTrace();
      }finally {
          if (reader != null) {
              try {
                  reader.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }

      System.out.println("result: " +result);
}

}
