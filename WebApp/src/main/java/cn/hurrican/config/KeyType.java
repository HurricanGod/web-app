package cn.hurrican.config;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/12
 * @Modified 17:31
 */
public interface KeyType {
    /**
     * 整数字符串
     **/
    int INT_STRING = 0;

    /**
     * 字符型字符串
     **/
    int DOUBLE_STRING = 1;

    /** 普通字符串 **/
    int CHAR_STRING = 2;

    /** 哈希 **/
    int HASH = 3;

    /** 集合 **/
    int SET = 4;

    /** 列表 **/
    int LIST = 5;

    /** 排序集 **/
    int SORTED_SET = 6;

}
