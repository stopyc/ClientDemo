package cn.qgstudio.util;

import cn.qgstudio.po.Network;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 15:23
 **/
public class HardWareUtils extends Applet {
    public HardWareUtils() {
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void paint(Graphics paint) {
        super.paint(paint);
        paint.drawString("获取硬件信息", 10, 10);
        paint.drawString("CPU  SN:" + HardWareUtils.getCPUSerial(), 10, 30);
        try {
            paint.drawString("C盘   SN:" + HardWareUtils.getHardDiskSN("c"), 10, 70);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            paint.drawString("MAC  SN:" + HardWareUtils.getMacAddresses(), 10, 90);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取CPU***** @return
     */
    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result = "无CPU_ID被读取";
        }
        return result.trim();
    }


    /**
     * 一台机器可能存在多个网卡, 故返回数组
     * 网络接口地址(NetworkInterface) ----> 接口地址(InterfaceAddress) ----> IP地址(InetAddress)
     *  ----> 网络接口地址(NetworkInterface)
     * @return
     * @throws Exception
     */
    public static java.util.List<String> getMacAddresses() throws Exception {

        // 获取机器上的所有网络接口, 返回结果至少包含一项(即, loopback本地环回测试)
        // getNetworkInterfaces() + getInetAddresses()可以获取到所有IP地址
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();

        StringBuilder sb = new StringBuilder();

        ArrayList<String> networkNames = new ArrayList<String>();

        // 保存mac地址列表
        ArrayList<Network> macList = new ArrayList<Network>();

        // 使用标签, 跳出多重循环
        here:
        while (en.hasMoreElements()) {

            NetworkInterface iface = en.nextElement();

            // 获取对应网络接口的所有接口地址(InterfaceAddress)
            java.util.List<InterfaceAddress> addrs = iface.getInterfaceAddresses();

            for (InterfaceAddress addr : addrs) {

                // InetAddress: Internet Protocol (IP) address: IP地址
                // 返回网络接口地址对应的IP地址
                InetAddress ip = addr.getAddress();

                // 由IP地址获取网络接口(NetworkInterface)
                // 方便方法搜索到绑定到其的具体IP地址的网络接口(NetworkInterface)
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);

                // 若为空, 跳过
                if (networkInterface == null) {

                    continue;

                }

                // 获取以太网等名称, 如：eth0, eth1, wlan1
                String name = networkInterface.getName();

                // 获取描述
                String displayName = networkInterface.getDisplayName();

                // 当网络接口有权限连接, 并且其具有MAC地址时, 返回二进制MAC硬件地址
                byte[] mac = networkInterface.getHardwareAddress();

                // 是否为虚拟网络接口
                boolean virtual = networkInterface.isVirtual();

                // 网络接口是否启动
                boolean up = networkInterface.isUp();

                if (mac == null) {

                    continue;

                }

                // 清空StringBuffer中的内容
                sb.delete(0, sb.length());

                // 转换MAC地址格式
                for (int i = 0; i < mac.length; i++) {

                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

                }

                Network network = new Network();

                // 免若网络接口首次出现, 避返回结果重复
                if (!networkNames.contains(name)) {

                    networkNames.add(name);

                    network.setName(name);
                    network.setDisplayName(displayName);
                    network.setMac(sb.toString());
                    network.setVirtual(virtual);
                    network.setUp(up);

                    macList.add(network);

                }

//                // 若找到, 跳出
//                if (networkNames.contains("wireless")) {
//
//                    break here;
//
//                }
            }


        }

        List<String> macs = new ArrayList<>();
        for (Network network : macList) {
            macs.add(network.getMac());
        }

        return macs;

    }

    /**
    * @Description: 获取硬盘信息
    * @Param: [drive]
    * @return: java.lang.String
    * @Author: stop.yc
    * @Date: 2022/7/29
    */
    public static String getHardDiskSN(String drive) throws IOException {
        long start = System.currentTimeMillis();
        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "diskdrive", "get", "serialnumber"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
        System.out.println(property + ":" + serial);
        System.out.println("time" + (System.currentTimeMillis() - start));

        return serial;
    }

}
