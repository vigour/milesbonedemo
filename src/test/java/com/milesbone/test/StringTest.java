package com.milesbone.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	
}
