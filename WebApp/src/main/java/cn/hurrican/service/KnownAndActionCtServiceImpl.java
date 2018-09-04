package cn.hurrican.service;

import cn.hurrican.constant.KnownAndActionCtConstant;
import cn.hurrican.model.KnownAndActionRestfulRequestArgs;
import cn.hurrican.model.ResMessage;
import cn.hurrican.utils.DateTimeUtils;
import cn.hurrican.utils.HttpRequestUtils;
import cn.hurrican.utils.XmlUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: Hurrican
 * @Description: “
 * @Date 2018/8/30
 * @Modified 15:48
 */
@Service
public class KnownAndActionCtServiceImpl {


    private static Logger logger = LogManager.getLogger(KnownAndActionCtServiceImpl.class);




    public boolean supportGame(Integer gameTemplateId){
        return true;
    }

    public boolean needCustomize(Integer unionId, Integer pageTemplateId, Integer templateScene) {
        return unionId.equals(KnownAndActionCtConstant.getUid()) && supportGame(pageTemplateId) && templateScene != null;
    }


    public ResMessage syncAwardRecordToThirdSystem(String openid, Integer sceneId) throws IOException {
        String exposedOpenid = getExposedOpenid(openid, true);
        String date = DateTimeUtils.format(new Date());
        String xml = getXmlRequestBody(KnownAndActionCtConstant.getAppid().toString(),
                sceneId.toString(), exposedOpenid, date);
        JSONObject jsonObject = HttpRequestUtils.simplePostByXml(KnownAndActionCtConstant.PUSH_OPENID_URL, xml, "UTF-8");
        System.out.println(jsonObject);
        return null;
    }

    public String getExposedOpenid(String openid, Boolean debug){
        if(debug){
            return UUID.randomUUID().toString().replace("-", "");
        }
        char[] chars = openid.toCharArray();
        char[] exposedCharArray = new char[openid.length()];
        for (int i = 0; i < chars.length; i++) {
            exposedCharArray[i] = (char) (chars[i] + 1);
        }
        return new String(exposedCharArray);
    }




    /**
     *
     * @param appId appid，由知而行提供
     * @param sceneId 理论上由知而行提供
     * @param exposedOpenid 暴露给知而行的 openid
     * @param subscribeTime 调用知而行推送 openid 的时间
     * @return xml 请求参数 or 空串(异常时返回)
     */
    private String getXmlRequestBody(String appId, String sceneId, String exposedOpenid, String subscribeTime){
        String md5Sign = getMD5Sign(appId, sceneId, exposedOpenid, subscribeTime);
        KnownAndActionRestfulRequestArgs requestArgs = new KnownAndActionRestfulRequestArgs();
        requestArgs.setAppId(appId);
        requestArgs.setSceneId(sceneId);
        requestArgs.setExposedOpenid(exposedOpenid);
        requestArgs.setSign(md5Sign);
        requestArgs.setSubscribeTime(subscribeTime);
        try {
            return  XmlUtils.convertToXml(requestArgs, XmlUtils.DEFAULT_ENCODING);
        } catch (Exception e) {
            logger.error("将请求参数转化为 xml 时发生异常：{}", e);
            return "";
        }
    }


    /**
     * 根据知而行需求对请求参数进行加密
     * @param appId appid，由知而行提供
     * @param sceneId 理论上由知而行提供
     * @param exposedOpenid 暴露给知而行的 openid
     * @param subscribeTime 调用知而行推送 openid 的时间
     * @return
     */
    private String getMD5Sign(String appId, String sceneId, String exposedOpenid, String subscribeTime){
        String[] params = new String[]{"app_id=" + appId, "scene_id=" + sceneId,
                "open_id=" + exposedOpenid, "subscribe_time=" + subscribeTime};
        Arrays.sort(params);
        String signString = StringUtils.join(params, "&") + KnownAndActionCtConstant.getSignKey();
        logger.info("待签名的字符串为：{}", signString);
        return DigestUtils.md5Hex(signString);
    }


}
