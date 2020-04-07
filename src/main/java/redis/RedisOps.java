package redis;
import redis.clients.jedis.Jedis;

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
    public static void setObject(String key,Object object){
        Jedis jedis = RedisConnection.getJedis();
        jedis.set(key.getBytes(), SerializeUtils.serizlize(object));
        jedis.close();
    }
    public static Object getObject(String key){
        Jedis jedis = RedisConnection.getJedis();
        byte[] bytes = jedis.get(key.getBytes());
        jedis.close();
        return SerializeUtils.deserialize(bytes);
    }

}
