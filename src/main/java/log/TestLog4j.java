package log;


import org.apache.log4j.Logger;

/**
 * @author lr
 * @Package redis
 * @date 2020/4/10 15:18
 * @description
 */
public class TestLog4j {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(TestLog4j.class);
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");

    }
}
