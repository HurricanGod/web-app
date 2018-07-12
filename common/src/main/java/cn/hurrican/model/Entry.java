package cn.hurrican.model;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/3/20
 * @Modified 16:34
 */
public class Entry<k, v> {
    k key;
    v value;

    @Override
    public String toString() {
        return "Entry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    public Entry(k key, v value) {
        this.key = key;
        this.value = value;
    }

    public Entry() {
    }

    public k getKey() {
        return key;
    }

    public void setKey(k key) {
        this.key = key;
    }

    public v getValue() {
        return value;
    }

    public void setValue(v value) {
        this.value = value;
    }
}
