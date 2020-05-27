package com.milesbone.test;

import java.util.regex.Pattern;

import org.junit.Test;

public class PartternTest {

	@Test
	public void testIPPORTParttern(){
		 Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
		 String source = "192.168.4.128:8080";
		 System.out.println(p.matcher(source).matches());;
	}
	
}
