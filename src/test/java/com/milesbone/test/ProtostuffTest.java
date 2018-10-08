package com.milesbone.test;

import com.milesbone.tool.CacheBF;

public class ProtostuffTest {
	 public static void main(String args[]) {  
	        Data data= new Data();  
	        data.setInfo("hello");  
	        data.setName("world");  
	        try {  
	            CacheBF.cache("123",data,100);  
	  
	            System.out.println("缓存是否存在："+CacheBF.check("123"));  
	            Data da=CacheBF.get("123",Data.class);  
	            System.out.println(da);  
	  
	            CacheBF.delete("123");  
	            System.out.println("缓存是否存在："+CacheBF.check("123"));  
	            Data dd=CacheBF.get("123",Data.class);  
	            System.out.println(dd);  
	        }catch (Exception e){  
	            e.printStackTrace();  
	        }  
	  
	  
	    }  
	}  
	//测试使用  
	class Data{  
	    private String name;  
	    private String info;  
	  
	    public String getName() {  
	        return name;  
	    }  
	  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	  
	    public String getInfo() {  
	        return info;  
	    }  
	  
	    public void setInfo(String info) {  
	        this.info = info;  
	    }  
	  
	    @Override  
	    public String toString() {  
	        return "Data{" +  
	                "name='" + name + '\'' +  
	                ", info='" + info + '\'' +  
	                '}';  
	    }  
	}  
