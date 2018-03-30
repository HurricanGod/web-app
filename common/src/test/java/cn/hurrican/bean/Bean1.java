package cn.hurrican.bean;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/3/29
 * @Modified 11:46
 */
public class Bean1 extends BaseBean{
    private String title;
    private Integer type;

    public Bean1(String title, Integer type) {
        this.title = title;
        this.type = type;
    }

    public Bean1() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
