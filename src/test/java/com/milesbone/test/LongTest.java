package com.milesbone.test;

import java.util.Calendar;
import java.util.Date;

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
	
	@Test
	public void testLongTime() {
		System.out.println("12H :" + 1000*60*60*12);
	}
	
	
	@Test
	public void testLongTime1() {
		System.out.println("72H :" + 1000*60*60*72);
	}
	
	@Test
	public void testLongTime2() {
		long date = Calendar.getInstance().getTimeInMillis();
		System.out.println("now:" + date);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_YEAR, -3);
		Date date1 = c.getTime();
		System.out.println("Add :" + date1);
//		System.out.println("72H :" + 1000*60*60*72);
	}
}
