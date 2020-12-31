package com.milesbone.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;


/**
 * 测试JDBC数据库的连接
 * @author miles
 *
 */
public class JDBCTest extends TestCase{

	private static final Logger logger = LoggerFactory.getLogger(JDBCTest.class);
	
	@Test
	public void testJdbc () throws  Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url="jdbc:mysql://miles-db:3306/mydrp?useUnicode=true&characterEncoding=UTF-8"; 
//        String user="javadmin"; 
        String user="logistics"; 
//        String password="fsk_12345"; 
        String password="logistics"; 
        System.out.println("try");
        Connection conn= DriverManager.getConnection(url,user,password);
        System.out.println("Done!OK!!! ");
        
        PreparedStatement   ps=conn.prepareStatement( "select * from   fsk_category "); 
        ResultSet   rs=ps.executeQuery(); 
        while   (rs.next())
        { 
                System.out.println( rs.getString(1)); 
        }
        conn.close();
		
	}
	
	
	@Test
	public void testLogger(){
		String a ="";
		logger.info("ceshi :{}", a);
		for(int i = 0; i <=5; i++){
			if(i==5){
				throw new RuntimeException("yuxsasadasdasda*");
			}
		}
		
		logger.info("finish");
	}
	
	public static void main(String[] args) throws IOException {
		String a ="";
		logger.info("ceshi :{}", a);
		for(int i = 0; i <=5; i++){
			if(i==5){
				throw new RuntimeException("yuxsasadasdasda*");
			}
		}
		
		logger.info("finish");
	}
}
