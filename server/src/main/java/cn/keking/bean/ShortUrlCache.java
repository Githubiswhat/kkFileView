package cn.keking.bean;

import cn.keking.utils.ConfigUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class ShortUrlCache {

    private static Map<String, String> cache = new HashMap<>();

    static {
        String shortUrlPath = ConfigUtils.getShortUrlConfigPath();
        try (FileInputStream is = new FileInputStream(shortUrlPath)){
            Properties properties = new Properties();
            properties.load(is);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                cache.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException("读取持久化文件失败", e);
        }
    }

    public String getLongUrl(String shortUrl){
        return cache.get(shortUrl);
    }

    public void saveShortUrl(String shortUrl, String longUrl){
        String shortUrlPath = ConfigUtils.getShortUrlConfigPath();
        try (FileOutputStream fos = new FileOutputStream(shortUrlPath)){
            cache.put(shortUrl, longUrl);
            // 创建 Properties 对象
            Properties properties = new Properties();
            // 将 Map 中的所有键值对添加到 Properties 对象
            properties.putAll(cache);
            properties.store(fos, "这是一个配置文件，用于短链的持久化");
        } catch (IOException e) {
            throw new RuntimeException("持久化失败了", e);
        }
    }

    public boolean hasShortUrl(String shortUrl){
        return cache.containsKey(shortUrl);
    }

    public boolean hasLongUrl(String longUrl){
        return cache.values().contains(longUrl);
    }

    public String getShortUrl(String longUrl){
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            if (entry.getValue().equals(longUrl)){
                return entry.getKey();
            }
        }
        return "";
    }

    private static ClassLoader[] getClassLoaders(){
        return new ClassLoader[]{
                ShortUrlCache.class.getClassLoader(),
                Thread.currentThread().getContextClassLoader(),
                ClassLoader.getSystemClassLoader(),
        };
    }

}
