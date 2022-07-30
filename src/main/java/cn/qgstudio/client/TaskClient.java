package cn.qgstudio.client;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 09:19
 **/
public class TaskClient {
    public String sendMsg(String data)  {
        String response = new Client().sendMessage(data);
        return response;
    }
}
