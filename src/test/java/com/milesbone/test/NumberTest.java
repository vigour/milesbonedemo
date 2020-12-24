package com.milesbone.test;

import static org.mockito.Matchers.intThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class NumberTest {

	
	/**
	 * 寻找一串自然数中缺失的数字
	 * 
	 * @param src 数组
	 * @param maxElement 数组中最大元素
	 * @return
	 */
	public static String getMissingNumber(int[] src, int maxElement) {
	    StringBuffer result = new StringBuffer();
	    int[] toolArray = new int[maxElement + 1];
	    toolArray[0] = 1;
	    for (int i = 0; i < src.length; i++) {
	        int num = src[i];
	        toolArray[num] = 1;
	    }
	    for (int i = 0; i < toolArray.length; i++) {
	        int num = toolArray[i];
	        if (num != 1) {
	            result.append(i + ",");
	        }
	    }
	    if (result.length() > 0) {
	        result.deleteCharAt(result.length() - 1);
	    }
	    return result.toString();
	}

	public static void main(String[] args) {
//	    Integer[] arr = new Integer[] {1,10,7,4,7};
//	    Integer[] arr = new Integer[20];
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(4);
		list.add(5);
		list.add(9);
		list.add(1);
	    //System.out.println(Arrays.stream(arr).max().getAsInt());
	    //System.out.println(Arrays.stream(arr).min().getAsInt());
//	    String result = getMissingNumber(arr, Arrays.stream(arr).max().getAsInt());
		
		Integer[] arr = list.toArray(new Integer[list.size()]);//转换Integer数组
	    int max = (int) Collections.max(Arrays.asList(arr));//取数组最大值
	    String result = getMissingNumber(ArrayUtils.toPrimitive(arr), max);//取缺少的数值
//	    String result = getMissingNumber(arr, 3);
	    
	    System.out.println(result);
	    System.out.println(Arrays.asList(result.split(",")));
//	    System.out.println(getMissingNumber(arr, 100));
	}
}
