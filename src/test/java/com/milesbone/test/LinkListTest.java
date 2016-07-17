package com.milesbone.test;

import java.util.LinkedList;
import java.util.List;

public class LinkListTest {

	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		list.add("g");
		list.add("h");
		list.add("i");
		list.add("j");
		print(list);
		
		System.out.println(list.size());

		list.push("k");

		
		list.pollLast();
		print(list);
		System.out.println(list.size());
	}

	private static void print(List<String> list) {
		System.out.println("---------------------");
		for (String s : list) {
			System.out.println(s);
		}
		System.out.println("---------------------");
		
	}
}
