package com.milesbone.test;

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
}
