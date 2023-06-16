package cn.keking.service.shorturl;

import cn.keking.bean.ShortLink;
import cn.keking.bean.ShortUrlCache;
import cn.keking.utils.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShortLinkServiceImpl {

    @Autowired
    ShortUrlCache shortUrlCache;

    //通过长链生成对应短链并存在数据库中
    public ShortLink addShortLink(String longLink, String url) {
        ShortLink shortLink = new ShortLink();
        url += "s";
        shortLink.setLongLink(longLink);
        if (shortUrlCache.hasLongUrl(longLink)){
            shortLink.setShorts(shortUrlCache.getShortUrl(longLink));
            shortLink.setShortLink(url + "/" + shortLink.getShorts());
        }else {
            String shortURL = URLUtil.getShortURL();
            while(shortUrlCache.hasShortUrl(shortURL)){
                shortURL = URLUtil.getShortURL();
            }
            shortLink.setShorts(shortURL);
            shortLink.setShortLink(url + "/" + shortLink.getShorts());
            shortUrlCache.saveShortUrl(shortURL, longLink);
        }
        return shortLink;
    }

    //通过短链查询到对应长链
    public ShortLink getLongLink(String shortLink) {
        String longUrl = shortUrlCache.getLongUrl(shortLink);
        ShortLink shorts = new ShortLink();
        shorts.setShortLink(shortLink);
        shorts.setLongLink(longUrl);
        return shorts;
    }


}


