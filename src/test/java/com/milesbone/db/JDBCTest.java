package com.milesbone.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import junit.framework.TestCase;


/**
 * 测试JDBC数据库的连接
 * @author miles
 *
 */
public class JDBCTest extends TestCase{

	@Test
	public void testJdbc () throws  Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
        String url="jdbc:mysql://localhost:3306/mydrp?useUnicode=true&characterEncoding=UTF-8"; 
        String user="logistics"; 
        String password="logistics"; 
        System.out.println("try");
        Connection conn= DriverManager.getConnection(url,user,password);
        System.out.println("Done!OK!!! ");
        
        PreparedStatement   ps=conn.prepareStatement( "select * from   users "); 
        ResultSet   rs=ps.executeQuery(); 
        while   (rs.next())
        { 
                System.out.println( rs.getString(1)); 
        }
        conn.close();
		
	}
}
