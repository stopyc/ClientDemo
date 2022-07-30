package cn.qgstudio.server;

import cn.qgstudio.constant.SystemConstant;
import cn.qgstudio.service.SendToRemoteServerService;
import cn.qgstudio.util.Encryption;
import cn.qgstudio.util.StringUtil;
import cn.qgstudio.view.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @program: Software-management-platform
 * @description: 创建线程
 * @author: stop.yc
 * @create: 2022-07-28 19:22
 **/
//@Controller
public class SocketThread extends Thread {


//    @Autowired
//    private CheckCodeTxtService checkCodeTxtService ;
    /**
     * 服务端对象
     */
    private ServerSocket serverSocket;
    /**
     * 默认监听9991端口
     */
    private int port = SystemConstant.PORT;


    /**
     * 构造方法，初始化服务端
     */
    public SocketThread() {
        try {
            // 创建Socket服务器对象，监听9991端口

            serverSocket = new ServerSocket(port);
            System.out.println("ServerSocket创建了....");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ServerSocket创建出错....");
            closeSocketServer();
        }
    }

    /**
     * 重写run方法
     */
    @Override
    public void run() {
//        try {
//            //注意点,因为这个是另起的线程,而serverSocket是主线程进行创建赋值,所以可能会导致此线程先于主线程运行,导致空指针异常
////            Thread.sleep(1000);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("服务端启动了，等待客户端发送消息....");
        // 循环监听，直到线程中断为止
        while (!this.isInterrupted()) {
            try {
                // accept是阻塞方法，等待客户端发消息
                Socket socket = serverSocket.accept();
                if (socket != null && !socket.isClosed()) {
                    // 处理socket消息
                    handleSocket(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description: 处理服务端接收到的socket消息
     * @Param: [socket] :socket对象
     * @return: void
     * @Author: stop.yc
     * @Date: 2022/7/28
     */
    private void handleSocket(Socket socket) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        StringBuffer result = new StringBuffer();
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            br = new BufferedReader(isr);

            String info = null;
            // 从流中读取客户端消息
            while ((info = br.readLine()) != null) {
                result.append(info);
            }

            System.out.println("服务端获取的客户端消息为: " + result);
            socket.shutdownInput();

            //从本地软件接受到了版本信息软件信息
            String[] split = result.toString().split("&");
            Main.software_id = Integer.parseInt(split[0]);
            System.out.println(Main.software_id);
            Main.version_id = Integer.parseInt(split[1]);
            System.out.println(Main.version_id);
            Main.function_type = Integer.parseInt(split[2]);
            System.out.println(Main.function_type);

            Integer resultToLocal = new SendToRemoteServerService().sendToRemote();
            String s = Encryption.addRsaAndAesToData(resultToLocal + StringUtil.getRandomString(300));

            // 给客户端响应消息
            os = socket.getOutputStream();
            pw = new PrintWriter(os);

            pw.write(s);

            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (pw != null) {
                    pw.close();
                }
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭socket
     */
    public void closeSocketServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
