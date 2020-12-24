package com.milesbone.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.json.simple.JSONObject;
import org.junit.Test;

public class StringTest {
	/**
	 * 标点符号正则
	 */
	private static final String NON_PUNCT_REGEX_STRING = "[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥× ]";
	
	@Test
	public void testPUNCRegex(){
		String srcString = "你好！我是xxx,alpha ·啊 我 来自 ……中国＄ 我有一本书 《上下五千年》，一 共781.00元，我的email:： dadasdasda@ldi.com";
		System.out.println(srcString.replaceAll(NON_PUNCT_REGEX_STRING, ""));
		
	}
	
	@Test
	public void testStringBuffer(){
		StringBuffer sb = new StringBuffer();
		
		for(int i = 1; i<3;i++) {
			sb.append(i);
			sb.append(",");
		}
		String res =  sb.substring(0, sb.length()-1);
		System.out.println(res);
		
	}
	
	
	@Test
	public void testStringSpilit(){
		String s = "";
		String s1 = null;
		String[] sa = s1.split(",");
		System.out.println(sa.length);
	}
	
	@Test
	public void testByteArray() {
		byte[] EMPTY_BYTE_ARRAY = new byte[0];
		System.out.println((EMPTY_BYTE_ARRAY == null) + "\t&&\t" + EMPTY_BYTE_ARRAY.length);
	}
	
	
	@Test
	public void testArray2Set() {
		 String[] arr = {"AA","BB","DD","CC","BB"};    
         
         //数组-->Set    
         Set<String> set = new HashSet<String>(Arrays.asList(arr));    
         System.out.println(set);
	}
	
	
	
	/**
     * @param args
     */
    public static List<Integer> StringToIntegerLst(List<String> inList){
        List<Integer> iList =new ArrayList<Integer>(inList.size());
        try{   
           for(int i=0,j=inList.size();i<j;i++){
             iList.add(Integer.parseInt(inList.get(i)));   
           }   
          }catch(Exception  e){
        }
        return iList;
    }
    public static List<Integer> CollStringToIntegerLst(List<String> inList){
        List<Integer> iList =new ArrayList<Integer>(inList.size());
        CollectionUtils.collect(inList, 
                  new Transformer(){
                    public java.lang.Object transform(java.lang.Object input){
                      return new Integer((String)input);
                    }
                  } ,iList );
        return iList;
    }
    
    
    @Test
    public void testJsonList() {
    	List<JSONObject> dataList = null;
		if (dataList  == null) {
			dataList = new ArrayList<JSONObject>(2);
		}
		if(dataList.size()>=2) {
			System.out.println("opps");
		}
    }
    
    public static void main(String[] args) {
        List<String> sList = new ArrayList<String>();
        for (int i=0;i<1000;i++) { 
            sList.add(String.valueOf(i));
        }
        Object[] param=new Object[]{sList};
        try {
            long runTime=Runtimes.invokeStaticMethod("com.milesbone.test.StringTest", "StringToIntegerLst", param);
            System.out.println("采用顺序转化方法执行时间"+runTime);
            long runTimeByColl=Runtimes.invokeStaticMethod("com.milesbone.test.StringTest", "CollStringToIntegerLst", param);
            System.out.println("采用org.apache.commons.collections.CollectionUtils执行时间"+runTimeByColl);
            System.out.println("微秒相差(runTimeByColl-runTime)=" +String.valueOf(runTimeByColl-runTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
