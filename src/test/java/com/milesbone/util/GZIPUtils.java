package com.milesbone.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class GZIPUtils {
	
	private static final Logger ApiLogger = Logger.getLogger(GZIPUtils.class);
	 public static final String GZIP_ENCODE_UTF_8 = "UTF-8";  
	  
	    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";  
	    private final static String ZIP_FILE_PATH = "D:/Resources/work/sqllite/";
	    
	    private static final String FILE_SOURCE_PATH = "/fsk/api3.0/file/";
	  
	    private static final String FILE_DEST_PATH = "D:/Resources/work/tmp/";
	    /** 
	     * 字符串压缩为GZIP字节数组 
	     *  
	     * @param str 
	     * @return 
	     */  
	    public static byte[] compress(String str) {  
	        return compress(str, GZIP_ENCODE_UTF_8);  
	    }  
	  
	    /** 
	     * 字符串压缩为GZIP字节数组 
	     *  
	     * @param str 
	     * @param encoding 
	     * @return 
	     */  
	    public static byte[] compress(String str, String encoding) {  
	        if (str == null || str.length() == 0) {  
	            return null;  
	        }  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        GZIPOutputStream gzip;  
	        try {  
	            gzip = new GZIPOutputStream(out);  
	            gzip.write(str.getBytes(encoding));  
	            gzip.close();  
	        } catch (IOException e) {  
	            ApiLogger.error("gzip compress error.", e);  
	        }  
	        return out.toByteArray();  
	    }  
	  
	    /** 
	     * GZIP解压缩 
	     *  
	     * @param bytes 
	     * @return 
	     */  
	    public static byte[] uncompress(byte[] bytes) {  
	        if (bytes == null || bytes.length == 0) {  
	            return null;  
	        }  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
	        try {  
	            GZIPInputStream ungzip = new GZIPInputStream(in);  
	            byte[] buffer = new byte[256];  
	            int n;  
	            while ((n = ungzip.read(buffer)) >= 0) {  
	                out.write(buffer, 0, n);  
	            }  
	        } catch (IOException e) {  
	            ApiLogger.error("gzip uncompress error.", e);  
	        }  
	  
	        return out.toByteArray();  
	    }  
	  
	    /** 
	     *  
	     * @param bytes 
	     * @return 
	     */  
	    public static String uncompressToString(byte[] bytes) {  
	        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);  
	    }  
	  
	    /** 
	     *  
	     * @param bytes 
	     * @param encoding 
	     * @return 
	     */  
	    public static String uncompressToString(byte[] bytes, String encoding) {  
	        if (bytes == null || bytes.length == 0) {  
	            return null;  
	        }  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
	        try {  
	            GZIPInputStream ungzip = new GZIPInputStream(in);  
	            byte[] buffer = new byte[256];  
	            int n;  
	            while ((n = ungzip.read(buffer)) >= 0) {  
	                out.write(buffer, 0, n);  
	            }  
	            return out.toString(encoding);  
	        } catch (IOException e) {  
	            ApiLogger.error("gzip uncompress to string error.", e);  
	        }  
	        return null;  
	    }  
	  
	    public static void main(String[] args) throws IOException {
	    	
	    		File dirFile = new File(FILE_SOURCE_PATH);
	    		File destDir = new File(ZIP_FILE_PATH);
	    		
	    		//若在默认路径则注释下面一行
	    		dirFile = new File("C:/Users/user/Downloads");
	    		
	    		if(dirFile.isDirectory()) {
	    			for(File f: dirFile.listFiles()) {
	    				if(!f.isDirectory() && (f.getName().startsWith("Home")||f.getName().startsWith("tv"))) {
	    					FileUtils.copyFileToDirectory(f, destDir);
	    					byte[] str =  FileUtils.readFileToByteArray(f);
	    					File dbfile = new File(ZIP_FILE_PATH + f.getName() + ".db");
	    			        FileUtils.writeByteArrayToFile(dbfile, GZIPUtils.uncompress(str));
	    				}
	    			}
	    		}
	    		System.out.println("done!!");
	    }  
	}  

