package flink.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @ClassName WebSocketServer
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/20 上午11:42
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@ServerEndpoint(value = "/webSocket")
public class WebSocketServer {
    private Logger logger = Logger.getLogger(this.getClass());
    private Session session;

    public WebSocketServer(){
        System.out.println("WebSocketConnecting..");
    }

    /**
     * 链接建立触发
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        logger.info("onOpen"+session.getId());
        try {
            session.getBasicRemote().sendText("hello client...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭触发
     * @param session
     */
    @OnClose
    public void onClose(Session session){
        logger.info("====== onClose:"+session.getId()+" ======");
    }

    /**
     * 接收到客户端消息时触发
     * @param session
     * @param msg
     */
    @OnMessage
    public void onSend(Session session,String msg){
        try {
            session.getBasicRemote().sendText("client"+session.getId()+"say:"+msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时触发
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session,Throwable error) {
        logger.info(session.getId() + "连接发生错误" + error.getMessage());
        error.printStackTrace();
    }

    public void sendMessage(int status, String message, Object datas) throws IOException{
        JSONObject result = new JSONObject();
        result.put("status", status);
        result.put("message", message);
        result.put("datas", datas);
        this.session.getBasicRemote().sendText(result.toString());
    }

}
