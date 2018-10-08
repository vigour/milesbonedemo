package com.milesbone.tool;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Created by yy on 2017/9/28.
 * 用与序列化以及反序列化对象的类
 * 1、传入一个对象，返回该对象序列化后的字节数组
 * 2、传入字节数组和类的类型对象，获取这个数据的反序列对象
 */
public class Serialization {
    /**
     * 传入一个对象，返回这个对象的序列化后的字节数组
     * @param object 需要实例化的对象
     * @param <T> 传入对象的类型
     * @return 返回对应的字节数组
     * @throws Exception 序列化失败继续抛出异常
     */
    public static <T> byte[] sequence(T object) throws Exception{
        //安全判断,要是传入的对象为空返回对应的也是空
        if(object==null){
            return null;
        }
        //获取对应的类
        Class<T> objectClass = (Class<T>) object.getClass();
        //使用LinkedBuffer分配一块默认大小的buffer空间；
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            //生成对应的图，返回对应的字节数组
            //使用ProtoStuff-runtime生成模式，以便在运行时通过反射进行缓存和使用。
            RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(objectClass);
            return ProtostuffIOUtil.toByteArray(object, runtimeSchema, linkedBuffer);
        } catch (Exception e) {
            //继续抛出异常
            throw new RuntimeException("对象序列化失败！",e);
        } finally {
            //关闭Buffer空间
            linkedBuffer.clear();
        }
    }

    /**
     * 根据穿进来的字节数组和具体的类的类型对象，获取对应反序列的结果对象
     * @param info 序列化完成的字节数组对象
     * @param classInfo 类的类型对象
     * @param <T> 具体要转换的类类型
     * @return 序列化成功返回序列化前的对象，要是传入的数据为空返回空对象
     * @throws Exception 抛出反序列化的异常
     */
    public static <T> T reverse(byte[] info,Class<T> classInfo)throws Exception{
        //安全判断
        if(info.length==0||classInfo==null){
            return null;
        }
        //根据传进来的数据反序列具体对象
        try{
            RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(classInfo);
            T object =runtimeSchema.newMessage();
            ProtostuffIOUtil.mergeFrom(info,object,runtimeSchema);
            return object;
        }catch(Exception e){
            throw new RuntimeException("反序列对象失败！",e);
        }
    }
}