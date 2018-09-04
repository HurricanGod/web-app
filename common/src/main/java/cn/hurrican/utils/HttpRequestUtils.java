package cn.hurrican.utils;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/31
 * @Modified 14:30
 */

public class HttpRequestUtils {


    /**
     * httpclient读取内容时使用的字符集
     */
    public static final String CONTENT_CHARSET = "UTF-8";

    private static final Map<String, ResponseParser> RESPONSE_PARSER_MAP = new HashMap<>(8);

    private static JsonResponseParser jsonParser = new JsonResponseParser();

    static {
        RESPONSE_PARSER_MAP.put("text/json", jsonParser);
        RESPONSE_PARSER_MAP.put("application/json", jsonParser);
    }


    public static JSONObject simplePostByXml(String url, String xml, String charset) throws IOException {
        charset = Optional.ofNullable(charset).orElse(CONTENT_CHARSET);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","text/html;charset=UTF-8");
        //解决中文乱码问题
        StringEntity stringEntity = new StringEntity(xml,charset);
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpclient.execute(httpPost);
        Header contentTypeHeader = response.getLastHeader("Content-Type");
        String content = EntityUtils.toString(response.getEntity());

        ResponseParser parser = RESPONSE_PARSER_MAP.get(contentTypeHeader.getValue().toLowerCase());
        if(parser != null){
            return parser.parse(content);
        }
        return JSONObject.fromObject(content);
    }

}
