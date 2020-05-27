package com.milesbone.parallel;

import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.milesbone.test.MapTest;
import com.milesbone.test.RandomTest;

/**
 * 多线程测试
 * @author miles
 *
 */
public class ParallelTest {
	
	
	@Test
	public void testParallel(){
		@SuppressWarnings("rawtypes")
		Class[] cls = { MapTest.class, RandomTest.class };

        Result rt;

        // 并发以类为维度
        // rt = JUnitCore.runClasses(ParallelComputer.classes(), cls);

        // 并发以方法为维度
        // rt = JUnitCore.runClasses(ParallelComputer.methods(), cls);

        // 并发以类和方法为维度
        rt = JUnitCore.runClasses(new ParallelComputer(true, true), cls);

        System.out.println(rt.getRunCount() + " " + rt.getFailures()  + " " + rt.getRunTime());
   
	}
	
	
	public static void main(String[] args) {
        @SuppressWarnings("rawtypes")
		Class[] cls = { MapTest.class, RandomTest.class };

        Result rt;

        // 并发以类为维度
        // rt = JUnitCore.runClasses(ParallelComputer.classes(), cls);

        // 并发以方法为维度
        // rt = JUnitCore.runClasses(ParallelComputer.methods(), cls);

        // 并发以类和方法为维度
        rt = JUnitCore.runClasses(new ParallelComputer(true, true), cls);

        System.out.println(rt.getRunCount() + " " + rt.getFailures()  + " " + rt.getRunTime());
    }

	
}


