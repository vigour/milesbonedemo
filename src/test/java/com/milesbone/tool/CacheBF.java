package com.milesbone.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.lang3.StringEscapeUtils;

/** 
 * Created by yy on 2017/9/29. 
 * 文件级别缓存的对象工具类 
 * 1、传入一个对象和缓存时间还有缓存名称对这个对象进行缓存 
 * 2、传入一个名称查询是否存在这个名称的缓存 
 * 3、传入一个名称和该类的类型对象获取对应的缓存 
 * 4、传入一个缓存名称删除该名称的缓存 
 */  
public class CacheBF {  
    /** 
     * 根据穿进来的名称和实体缓存对象到文件中 
     * @param name 键名 
     * @param object 需要保存的实体 
     * @param time 过期时间秒 
     * @param <T> 传入的实体类型 
     * @return 保存成功返回true，失败返回false 
     */  
    public static <T> boolean  cache(String name,T object,int time) throws Exception{  
  
        File file=CacheBF.getCacheFile(name);//获取缓存文件  
        FileWriter fw=new FileWriter(file);  
        BufferedWriter bw = new BufferedWriter(fw);  
        //设置过期时间  
        bw.write((System.currentTimeMillis()+(time*1000))+"");  
        bw.newLine();  
        //设置缓存信息  
        bw.write(StringEscapeUtils.escapeJava(new String(Serialization.sequence(object))));//添加转义  
        bw.close();  
        fw.close();  
        return true;  
    }  
  
    /** 
     * 根据传进来的文件名，和类的类型获取缓存的实体类 
     * @param name 缓存名称 
     * @param objectClass 需要取得的实体类型 
     * @param <T> 旋回对应实例 
     * @return 
     * @throws Exception 
     */  
    public static <T> T get(String name,Class<T> objectClass)throws Exception{  
  
        T vo=null;  
        File file=new File(CacheBF.getCacheFileName(name));//获取缓存文件  
        if (file.exists()){//判断文件是否存在  
            FileReader fr=new FileReader(file);  
            BufferedReader br=new BufferedReader(fr);  
            Long now =System.currentTimeMillis();  
            Long time=Long.parseLong(br.readLine());//判断文件缓存是否过期  
            String object=StringEscapeUtils.unescapeJava(br.readLine());//返回程序，反转义字符  
            br.close();  
            fr.close();  
            if (time>now){  
                vo= Serialization.reverse(object.getBytes(),objectClass);  
            }else{  
                file.delete();//过期删除文件，删除前关闭输出流  
            }  
        }  
        return vo;  
    }  
  
    /** 
     * 传入一个缓存名称，查询是否存在此缓存 
     * @param name 缓存名称 
     * @return 存在并且不过期返回true，否则返回false 
     * @throws Exception 
     */  
    public static boolean check(String name)throws Exception{  
  
        boolean back=false;  
        File file=new File(CacheBF.getCacheFileName(name));//获取缓存文件  
        if (file.exists()){  
            FileReader fr=new FileReader(file);  
            BufferedReader br=new BufferedReader(fr);  
            Long now=System.currentTimeMillis();  
            Long time=Long.parseLong(br.readLine());//判断文件缓存是否过期  
            br.close();  
            fr.close();  
            if (time>now){  
                back=true;  
            }else{  
                file.delete();//过期删除文件  
            }  
        }  
        return back;  
    }  
  
    /** 
     * 删除穿进来的缓存名称的文件 
     * @param name 需要删除的缓存的名称 
     * @throws Exception 
     */  
    public static void delete(String name)throws Exception{  
  
        File file=new File(CacheBF.getCacheFileName(name));  
        if (file.exists()){  
            file.delete();  
        }  
  
    }  
    /** 
     * 根据缓存的名称获取需要操作的文件对象 
     * 1、判断父类目录cache是否存在，不存在则创建 
     * 2、判断当前文件是否存在，存在删除后创建，不存在直接创建 
     * 3、文件名MD5加密 
     * @param name 需要得到的缓存的名称 
     * @return 
     * @throws Exception 
     */  
    private static File getCacheFile(String name)throws Exception{  
  
        File file=new File(CacheBF.getCacheFileName(name));//获取操作对象  
        //判断父目录是否存在  
        if (!file.getParentFile().exists()){  
            file.getParentFile().mkdir();  
        }  
        //判断当前文件是否存在  
        if (!file.exists()){  
            file.createNewFile();  
        }else {  
            file.delete();  
            file.createNewFile();  
        }  
        return file;  
    }  
  
    /** 
     * 传入缓存名获取缓存的文件名 
     * @param name 缓存名 
     * @return 
     */  
    private static String getCacheFileName(String name){  
        name=MD5maker.make(name)+".txt";//获取文件名  
        StringBuffer fileName=new StringBuffer(System.getProperty("user.dir"))  
                .append(File.separator).append("src")  
                .append(File.separator).append("main")  
                .append(File.separator).append("webapp")  
                .append(File.separator).append("WEB-INF")  
                .append(File.separator).append("cache")  
                .append(File.separator).append(name);//获取文件路径  
        return fileName.toString();  
    }  
}  