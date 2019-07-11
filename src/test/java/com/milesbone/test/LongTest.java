package com.milesbone.test;

import org.junit.Test;

public class LongTest {

	@Test
	public void testLong() {
		Long a = 100L;
		Long b = 50L;
		Long c = 100L;
		Long d = null;
//		System.out.println(Long.parseLong(String.valueOf(d)));
		
		System.out.println("大于:" + a.compareTo(b));
		System.out.println("小于"+b.compareTo(a));
		System.out.println("等于" +a.compareTo(c));
	}
}
