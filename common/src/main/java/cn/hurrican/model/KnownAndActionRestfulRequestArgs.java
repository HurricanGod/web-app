package cn.hurrican.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/8/31
 * @Modified 11:58
 */
@XmlRootElement(name = "xml")
public class KnownAndActionRestfulRequestArgs {

    @XmlElement(name = "app_id")
    private String appId;

    @XmlElement(name = "scene_id")
    private String sceneId;

    @XmlElement(name = "open_id")
    private String exposedOpenid;

    @XmlElement(name = "subscribe_time")
    private String subscribeTime;

    @XmlElement(name = "sign")
    private String sign;


    public void setAppId(String appId) {
        this.appId = appId;
    }



    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }



    public void setExposedOpenid(String exposedOpenid) {
        this.exposedOpenid = exposedOpenid;
    }


    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
