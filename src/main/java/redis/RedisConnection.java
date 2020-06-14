package redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lr
 * @Package redis
 * @date 2020/4/6 10:52
 * @description   Redis连接
 */
public class RedisConnection {

    private static String HOST = "47.103.29.15";
    private static int PORT = 6379;
    private static int MAX_ACTIVE = 1024;
    private static int MAX_IDLE = 200;
    private static int MAX_WAIT = 10000;

    //数据库模式是16个数据库 0~15
    public static final int DEFAULT_DATABASE = 0;

//    private static RedisConnection instance = new RedisConnection();
    private static Jedis jedis;
    private static JedisPool jedisPool = null;

    private RedisConnection(){}


//    public static RedisConnection getInstance(){
//        return instance;
//    }

    /*
     * 初始化redis连接池
     * */
    private static void initPool(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);//最大连接数
            config.setMaxIdle(MAX_IDLE);//最大空闲连接数
            config.setMaxWaitMillis(MAX_WAIT);//获取可用连接的最大等待时间

            jedisPool = new JedisPool(config,HOST, PORT,DEFAULT_DATABASE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取jedis实例
     * */
    public synchronized static Jedis getJedis() {
        try {
            if(jedisPool == null){
                initPool();
            }
            jedis = jedisPool.getResource();
            jedis.auth("123456");//密码
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
