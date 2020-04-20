package redis;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author lr
 * @Package redis
 * @date 2020/4/7 10:50
 * @description   Redis的序列化反序列化的操作
 */
public class RedisOps {
    public static void set(String key,String value){
        Jedis jedis = RedisConnection.getJedis();
        jedis.set(key, value);
        jedis.close();
    }
    public static String get(String key){
        Jedis jedis = RedisConnection.getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 存入序列化对象
     * @param key
     * @param object
     */
    public static void setObject(String key,Object object){
        Jedis jedis = RedisConnection.getJedis();
        jedis.set(key.getBytes(), SerializeUtils.serizlize(object));
        jedis.close();
    }

    /**
     * 获取序列化对象
     * @param key
     * @return
     */
    public static Object getObject(String key){
        Jedis jedis = RedisConnection.getJedis();
        byte[] bytes = jedis.get(key.getBytes());
        jedis.close();
        return SerializeUtils.deserialize(bytes);
    }

    /**
     * 获取所有key
     * @param pattern
     * @return
     */
    public static HashSet<String> keys(String pattern){
        Jedis jedis = RedisConnection.getJedis();
        HashSet<String> list = (HashSet<String>)jedis.keys(pattern);
        jedis.close();
        return list;
    }


}