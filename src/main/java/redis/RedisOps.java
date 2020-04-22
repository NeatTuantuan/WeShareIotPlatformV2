package redis;

import flink.dao.VariableRule;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author lr
 * @Package redis
 * @date 2020/4/7 10:50
 * @description   Redis的序列化反序列化的操作
 */
public class RedisOps {
    static RedisConnection redisConnection = RedisConnection.getInstance();

    public static void set(String key,String value){
        Jedis jedis =redisConnection.getJedis();
        jedis.set(key,value);
        jedis.close();
//        RedisConnection.getJedis().close();
    }
    public static String get(String key){
        Jedis jedis = redisConnection.getJedis();
        String value =jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 存入序列化对象
     * @param key
     * @param object
     */
    public static void setObject(String key,Object object){
        Jedis jedis = redisConnection.getJedis();
        jedis.set(key.getBytes(), SerializeUtils.serialize(object));
        jedis.close();
    }

    /**
     * 获取序列化对象
     * @param key
     * @return
     */
    public static Object getObject(String key){
        Jedis jedis = redisConnection.getJedis();
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

    //set redis hash 序列化单次添加
    public static void setObjectHash(String deviceId,int number,Object object){
        Jedis jedis =redisConnection.getJedis();
        jedis.hset(deviceId.getBytes(),DataUtil.intToByteArray(number),SerializeUtils.serialize(object));
        jedis.close();
    }

    //get redis hash 反序列化 批量取key相同的hash表
    public static HashMap<Integer, VariableRule> getObjectHashAll(String deviceId){
        Jedis jedis = redisConnection.getJedis();
        HashMap<Integer,VariableRule> map = new HashMap<>();
        Map<byte[],byte[]> list = jedis.hgetAll(deviceId.getBytes());
        for(Map.Entry<byte[],byte[]> entry : list.entrySet()){
            int i= DataUtil.bytes2int(entry.getKey());
            VariableRule rule =(VariableRule)SerializeUtils.deserialize(entry.getValue());
            map.put(i,rule);
        }
        jedis.close();
        return map;
    }

    //get redis hash 反序列化 单次取出某一值
    public static Object getObjectHash(String deviceId,int number){
        Jedis jedis = redisConnection.getJedis();
        byte[] bytes = jedis.hget(deviceId.getBytes(), DataUtil.intToByteArray(number));
        VariableRule rule =(VariableRule)SerializeUtils.deserialize(bytes);
        jedis.close();
        return rule;
    }

}
