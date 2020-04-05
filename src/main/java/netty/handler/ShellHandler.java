package netty.server;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.deviceMessage.DeviceMessage;
import netty.util.FileUtil;
import netty.util.PropertiesUtil;
import netty.util.UserClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @ClassName ShellHandler
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/2 下午5:24
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class ShellHandler extends SimpleChannelInboundHandler<DeviceMessage> {
    //经过用户脚本编译的class 文件目录
    private String CLASS_PATH = PropertiesUtil.getKey("CLASS_PATH");
    //包名，用户在web端设置
    private String PACKET_NAME = PropertiesUtil.getKey("PACKET_NAME");
    //存储文件名的集合类
    private ArrayList<String> fileNameList = new ArrayList<>();

    JSONObject customResult = null;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DeviceMessage msg) throws Exception {
        FileUtil.getAllFileName(CLASS_PATH,fileNameList);

        //判断文件中有没有编译好的class文件（log.class文件名应与msg中产品ID一致，用来解析指定产品），如果有的话利用反射调用脚本解析
        if (fileNameList.contains("Log.class")){
            UserClassLoader myClassLoader = new UserClassLoader(CLASS_PATH);
            //加载Log这个class文件
            Class<?> Log = myClassLoader.loadClass(PACKET_NAME);

            //利用反射获取test方法
            Method method = Log.getDeclaredMethod("test", DeviceMessage.class);
            Object object = Log.newInstance();
            //向自定义脚本传入参数（上一个handler解析mqtt后的数据）
            //获取自定义脚本解析后的数据，正式版本应补货异常，如果没有成功解析就用一个默认的串来代替。
//            JSONObject result = (String) method.invoke(object, (String)msg);

            try {
                customResult = (JSONObject) method.invoke(object, msg);
            } catch (Exception e) {
                e.printStackTrace();
                customResult.put("Data",e.toString());
            } finally {
                msg.setFormatData(customResult);
            }

//            System.out.println(result);




        }else {
            //没有脚本，不做任何操作
            ctx.writeAndFlush(msg);
        }
    }
}
