package cn.keking.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class URLUtil {
    //生成短链接的八个字符
    public static String getShortURL() {
        Random random = new Random();

        // 要使用生成 URL 的字符
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        int ln = chars.length;
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 3; ++i) {
            key.append(chars[random.nextInt(ln)]);
        }

        return key.toString();
    }


    //获取请求的协议域名，端口号生成连接的前半部分
    public static String getUrlStart(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme());
        System.out.println(url);
        url.append("://").append(request.getServerName());
        System.out.println(url);
        url.append(":").append(request.getServerPort());
        System.out.println(url);
        url.append("/");
        return url.toString();
    }

}
