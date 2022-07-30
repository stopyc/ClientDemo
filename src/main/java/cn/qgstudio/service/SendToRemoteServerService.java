package cn.qgstudio.service;

import cn.qgstudio.client.TaskClient;
import cn.qgstudio.po.CodedText;
import cn.qgstudio.util.Declassify;
import cn.qgstudio.util.Encryption;
import cn.qgstudio.util.HardWareUtils;
import cn.qgstudio.util.TimeUtils;
import cn.qgstudio.view.Main;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-30 11:42
 **/
public class SendToRemoteServerService {
    public Integer sendToRemote () throws Exception {
        boolean connect = testInternet();

        while (Main.software_id == null ||  Main.function_type == null || Main.version_id == null) {

        }

        System.out.println(Main.software_id);
        System.out.println(Main.function_type);
        System.out.println(Main.version_id);


        //已经获取了本地软件发来的版本信息

        //该程序被别的启动
        //首先先判断是否有联网
        if (connect) {
            System.out.println("本机正处于联网状态~~");
            //有联网,进行服务端通信,判断是否成功
//            Thread.sleep(1000);
            //获取本地硬件信息
            //mac
            List<String> macAddress = HardWareUtils.getMacAddresses();
            String cpuSerial = HardWareUtils.getCPUSerial();
            String hardDiskSN = HardWareUtils.getHardDiskSN("c");


            //生成网络时间
            Date websiteDatetime = TimeUtils.getWebsiteDatetime();
            CodedText codedText = new CodedText(Main.software_id, Main.function_type, Main.version_id, websiteDatetime, macAddress, cpuSerial, hardDiskSN);

            String jsonString = JSONObject.toJSONString(codedText);

            System.out.println(Encryption.addRsaAndAesToData(jsonString));

            String response = new TaskClient().sendMsg(Encryption.addRsaAndAesToData(jsonString));
            System.out.println("服务器发送的响应为:  "+response);

            int check = Declassify.check(response);
            System.out.println("验证成功与否?" + check);
            if (check != -1) {
                //成功打开对话框提示成功
                JOptionPane.showMessageDialog(null,"授权校验成功,请返回使用的软件");
                if (check > Main.function_type) {
                    String func = "";
                    if (check == 1) {
                        func = "教育版";
                    }else if (check == 2) {
                        func = "企业版";
                    }
                    int i = JOptionPane.showConfirmDialog(null, "检测到您的许可证可使用版本为" + func + ",而当前软件版本权限较低,是否升级软件?");
                    if (i == JOptionPane.YES_OPTION) {
                        //重写run方法
                        new Thread(() -> {
                            //构造命令
                            String cmd = "cmd.exe /c start ";

                            //构造本地文件路径或者网页URL
                            String file = "http://106.13.18.48:9876";
//                            String file = "C:/Users/Wentasy/Desktop/core_java_3_api/index.html";

                            try {
                                //执行操作
                                Runtime.getRuntime().exec(cmd + file);
                            } catch (IOException ignore) {
                                //打印异常
                                ignore.printStackTrace();
                            }
                        }).start();
                    }
                }
                return 1;
            } else {
                JOptionPane.showMessageDialog(null,"授权校验失败");
                return 0;
            }



        }
        else {
            //没有联网
            //打开输入框
            //进行输入授权码
            //判断,是否成功,



            //成功打开对话框,提示成功,失败则返回0,表示都
            return -1;
        }
    }

    /**
     * @Description: 判断是否联网
     * @Param: []
     * @return: boolean
     * @Author: stop.yc
     * @Date: 2022/7/29
     */
    public boolean testInternet() throws IOException {
        BufferedReader bufferedReader = null;


        try {

            Process process = Runtime.getRuntime()

                    .exec("ping " + "180.97.33.107" + " -t");

            bufferedReader = new BufferedReader(new InputStreamReader(

                    process.getInputStream()));

            String connectionStr = null;

            while ((connectionStr = bufferedReader.readLine()) != null) {

                System.out.println(connectionStr);
                return true;

            }
            return false;

        } catch (IOException e) {

            System.out.println("链接失败");

            e.printStackTrace();

            return false;
        } finally {

            bufferedReader.close();

        }
    }
}
