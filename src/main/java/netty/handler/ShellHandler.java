package netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.devicemessage.DeviceMessage;
import netty.util.FileUtil;
import netty.util.PropertiesUtil;
import netty.util.UserClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName ShellHandler
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/2 下午5:24
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class ShellHandler extends SimpleChannelInboundHandler<DeviceMessage> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 经过用户脚本编译的class 文件目录
     */
    private String classPath = PropertiesUtil.getKey("CLASS_PATH");
    /**
     * 包名，用户在web端设置
     */
    private String packetName = PropertiesUtil.getKey("PACKET_NAME");
    /**
     * 存储文件名的集合类
     */
    private ArrayList<String> fileNameList = new ArrayList<>();
    /**
     * 自定义类加载器加载类
     */
    UserClassLoader myClassLoader;
    /**
     * 用户解析的json串
     */
    JSONObject customResult = null;
    /**
     * 错误信息
     */
    HashMap<String, Object> errorMap = new HashMap<>(10);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceMessage msg) throws Exception {

        FileUtil.getAllFileName(classPath,fileNameList);

        //判断文件中有没有编译好的class文件（log.class文件名应与msg中产品ID一致，用来解析指定产品），如果有的话利用反射调用脚本解析
        if (fileNameList.contains("Log.class")){

            myClassLoader= new UserClassLoader(classPath+"/Log.class");
            //加载Log这个class文件
            Class<?> Log = myClassLoader.loadClass(packetName);
            //利用反射获取shell方法
            Method method = Log.getDeclaredMethod("shell", byte[].class);
            Object object = Log.newInstance();

            try {
                //向自定义脚本传入参数（上一个handler解析mqtt后的数据）
                String result = (String) method.invoke(object, msg.getVariableReport().getMetaData());

                logger.info("----ShellHandler----:"+"shell result = "+result);

                this.customResult = JSON.parseObject(result);
            } catch (Exception e) {
                errorMap.put("msg","format error! please check your shell file");
                e.printStackTrace();
            } finally {
                msg.getVariableReport().setVariableJson(customResult);
            }
        }else {
            errorMap.put("msg","no shell file");
            msg.getVariableReport().setVariableJson(new JSONObject(errorMap));
        }
        ctx.fireChannelRead(msg);
    }
}
