package cn.keking.bean;



import java.time.LocalDateTime;

/**
 * @program: springboot
 * @description: 短链接实体类
 * @author: King
 * @create: 2021-09-18 14:02
 */

public class ShortLink {
    private Integer id;
    //随机生成的字符
    private String shorts;
    //短链接
    private String shortLink;
    //长链接
    private String longLink;

    //创建时间
    private LocalDateTime createTime;

    //是否被删除，0表示未删除，1表示已经删除
    //@TableLogic  3.3.0,配置后可以忽略不配置
    private int flag;

    public void println() {
        System.out.println(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShorts() {
        return shorts;
    }

    public void setShorts(String shorts) {
        this.shorts = shorts;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
