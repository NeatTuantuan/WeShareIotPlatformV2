package netty.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 4426762885453392258L;

    private String topic;

    private int mqttQoS;

    private byte[] messageBytes;

}
