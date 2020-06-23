package netty.devicemessage;

import lombok.Data;

/**
 * @ClassName DeviceShadow
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/5/31 上午10:52
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class DeviceShadow {
    /**
     * 信息的唯一id,网关生成的
     */
    private String id;
    /**
     * 代表请求方法，有update、post、get等。目前暂时只有update
     */
    private String method;
    /**
     * 代表物模型的版本，目前物模型没有分版本
     */
    private String version;
    /**
     * 设备状态：
     * 0-离线
     * 1-在线
     */
    private int state;

    /**
     * 无参构造方法
     */
    public DeviceShadow(){}
    /**
     * 有参构造方法
     * @param id
     * @param method
     * @param version
     * @param state
     */
    public DeviceShadow(String id, String method, String version, int state){}

    public boolean isDeviceShadow(String topics){

        String[] token = topics.split("/");
        if(token[1].equals("weshare") && token[2].equals("iot") &&token[3].equals("update") &&
                token[4].equals("shadow"))
            return true;
        return false;
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public int getState() {
        return state;
    }
}
