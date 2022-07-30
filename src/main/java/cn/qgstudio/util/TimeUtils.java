package cn.qgstudio.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @program: Software-management-platform
 * @description: 时间处理工具类
 * @author: stop.yc
 * @create: 2022-07-26 09:34
 **/
public class TimeUtils {

    public static Date getWebsiteDatetime() throws IOException {
        //百度
        String baiduUrl = "http://www.baidu.com";
        // 取得资源对象
        URL url = new URL(baiduUrl);
        // 生成连接对象
        URLConnection uc = url.openConnection();
        // 发出连接
        uc.connect();
        // 读取网站日期时间
        long ld = uc.getDate();
        // 转换为标准时间对象
        Date date = new Date(ld);
        // 输出北京时间
//        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss", Locale.CHINA);

////        Instant instant = Instant.ofEpochMilli(ld);
////        ZoneId zone = ZoneId.systemDefault();
//        return LocalDateTime.ofInstant(instant, zone);
        return date;
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }
}
