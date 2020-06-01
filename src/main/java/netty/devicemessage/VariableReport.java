package netty.devicemessage;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @ClassName VariableReport
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/5/31 上午10:55
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class VariableReport {
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
     * 存放变量的集合
     */
    private JSONObject variableJson;

    private byte[] metaData;

    /**
     * 无参构造方法
     */
    public VariableReport(){}

    /**
     * 有参构造方法
     * @param id
     * @param method
     * @param version
     * @param variableMap
     */
    public VariableReport(String id, String method, String version, JSONObject variableMap, byte[] metaData){
        this.id = id;
        this.method = method;
        this.version = version;
        this.variableJson = variableMap;
        this.metaData = metaData;
    }
}
