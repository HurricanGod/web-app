package cn.hurrican.model;

import lombok.Data;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/7/16
 * @Modified 12:14
 */
@Data
public class QueryResult<T> {

    private T obj;

    private boolean hitCache = false;

    public static QueryResult build(){
        return new QueryResult<>();
    }

    public QueryResult queryResultIs(T queryResult){
        this.obj = queryResult;
        return this;
    }

    public QueryResult hadHitCache(Boolean hitCache){
        this.hitCache = hitCache;
        return this;
    }
}
