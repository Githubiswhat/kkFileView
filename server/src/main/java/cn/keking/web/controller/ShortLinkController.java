package cn.keking.web.controller;

import cn.keking.bean.ShortLink;
import cn.keking.service.shorturl.ShortLinkServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ShortLinkController {
    @Autowired
    ShortLinkServiceImpl shortLinkService;

    //实现短链跳转
    @RequestMapping(value = "/s/{shortLink}")
    public void sendRedirect(HttpServletResponse response, @PathVariable String shortLink) {
        ShortLink shorts = shortLinkService.getLongLink(shortLink);
        if (shorts != null) {
            try {
                response.sendRedirect(shorts.getLongLink());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
