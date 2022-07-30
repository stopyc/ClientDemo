package cn.qgstudio.view;

import java.io.IOException;

/**
 * @program: ClientDemo
 * @description: 主启动类
 * @author: stop.yc
 * @create: 2022-07-29 08:45
 **/
public class Main {
    public static Integer software_id;
    public static Integer version_id;
    public static Integer function_type;
    public static void main(String[] args) throws Exception {

        new PlatformFrm().init();
    }
}
