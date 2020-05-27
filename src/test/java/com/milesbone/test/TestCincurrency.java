package com.milesbone.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestCincurrency {

	public static void main(String[] args) {
		List<Integer> list = new CopyOnWriteArrayList<Integer>();
		list.add(2);
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			Integer integer = iterator.next();
			if (integer == 2)
				list.remove(integer);
		}
	}

}
