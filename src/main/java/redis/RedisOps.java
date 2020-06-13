package redis;


import flink.dao.VariableRule;
import org.apache.log4j.LogManager;
import redis.clients.jedis.Jedis;

import java.util.*;


/**
 * @author lr
 * @Package redis
 * @date 2020/4/7 10:50
 * @description   Redis的序列化反序列化的操作
 */
public class RedisOps {

    static org.apache.log4j.Logger logger= LogManager.getLogger(RedisOps.class);

    public static void set(String key, String value) {
        Jedis jedis = RedisConnection.getJedis();
        jedis.set(key, value);
        jedis.close();
    }

    public static String get(String key) {
        Jedis jedis = RedisConnection.getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 存入序列化对象,可选库
     *
     * @param key
     * @param object
     */
    public static void setObject(String key, Object object, int index) {
        Jedis jedis = RedisConnection.getJedis();
        jedis.select(index);
        jedis.set(key.getBytes(), SerializeUtils.serialize(object));
        jedis.close();
    }

    /**
     * 获取序列化对象,可选库
     *
     * @param key
     * @return
     */
    public static Object getObject(String key, int index) {
        Jedis jedis = RedisConnection.getJedis();
        jedis.select(index);
        byte[] bytes = jedis.get(key.getBytes());
        jedis.close();
        return SerializeUtils.deserialize(bytes);
    }

    /**
     * 获取所有key,可选库
     *
     * @param pattern
     * @return
     */
    public static HashSet<String> keys(String pattern, int index) {
        Jedis jedis = RedisConnection.getJedis();
        jedis.select(index);
        HashSet<String> list = (HashSet<String>) jedis.keys(pattern);
        jedis.close();
        return list;
    }

    //set redis hash 序列化单次添加,  index为选择插入的数据库
    public static void setObjectHash(String deviceId, int number, Object object, int index) {
        Jedis jedis = RedisConnection.getJedis();
        jedis.select(index);
        jedis.hset(deviceId.getBytes(), DataUtil.intToByteArray(number), SerializeUtils.serialize(object));
        jedis.close();
    }

    //get redis hash 反序列化 批量取key相同的hash表, index为选择查询的数据库
    public static HashMap<Integer, VariableRule> getObjectHashAll(String deviceId, int index) {
        Jedis jedis = RedisConnection.getJedis();
        jedis.select(index);
        HashMap<Integer, VariableRule> map = new HashMap<>();
        Map<byte[], byte[]> list = jedis.hgetAll(deviceId.getBytes());
        for (Map.Entry<byte[], byte[]> entry : list.entrySet()) {
            int i = DataUtil.bytes2int(entry.getKey());
            VariableRule rule = (VariableRule) SerializeUtils.deserialize(entry.getValue());
            map.put(i, rule);
        }
        jedis.close();
        return map;
    }

    //get redis hash 反序列化 单次取出某一值
    public static Object getObjectHash(String deviceId, int number) {
        Jedis jedis = RedisConnection.getJedis();
        byte[] bytes = jedis.hget(deviceId.getBytes(), DataUtil.intToByteArray(number));
        VariableRule rule = (VariableRule) SerializeUtils.deserialize(bytes);
        jedis.close();
        return rule;
    }

    //删除数据，可选库
    public static void delete(String key, int index) {
        Jedis jedis = RedisConnection.getJedis();
        jedis.select(index);
        try {
            jedis.del(key);
            logger.info("删除成功");
        } catch (Exception e) {
            logger.error("删除失败：" + e.getMessage(), e);
        } finally {
            jedis.close();
        }

    }
}
