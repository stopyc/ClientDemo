package cn.qgstudio.view;

import cn.qgstudio.client.TaskClient;
import cn.qgstudio.po.CodedText;
import cn.qgstudio.server.SocketThread;
import cn.qgstudio.util.Declassify;
import cn.qgstudio.util.Encryption;
import cn.qgstudio.util.HardWareUtils;
import cn.qgstudio.util.TimeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.omg.CORBA.TIMEOUT;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 08:56
 **/
public class PlatformFrm extends JFrame {

    private JPanel platformPanel;
    private JPanel panel1;
    //项目启动时,新建socket通信线程
    private SocketThread socketThread;

    /**
     * @Description: 初始化页面
     * @Param: []
     * @return: void
     * @Author: stop.yc
     * @Date: 2022/3/18
     */
    public void init() throws Exception {
        JFrame frame = new JFrame("PlatformFrm");
        frame.setContentPane(new PlatformFrm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 440);
        frame.setLocationRelativeTo(null);



        //已经获取本地软件传来的版本id,软件id,类别id.
        //这里还没做完,先模拟数据

        //启动服务端
        if (null == socketThread) {
//新建线程类
            socketThread = new SocketThread();
//启动线程
            socketThread.start();
        }

        frame.setVisible(true);
    }

}
