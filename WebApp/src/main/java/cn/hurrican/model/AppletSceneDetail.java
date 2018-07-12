package cn.hurrican.model;

import cn.hurrican.aop.EnableCache;
import lombok.Data;
import net.sf.json.JSONObject;

import java.util.Date;

@Data
public class AppletSceneDetail {
    /**
     * Table:     t_applet_scene_detail
     * Column:    id
     * Nullable:  false
     */
    private Integer id;

    /**
     * 场景类型
     * {@link }
     * Table:     t_applet_scene_detail
     * Column:    scene_type
     * Nullable:  false
     */
    private Integer sceneType;

    /**
     * 平台类型
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    platform_id
     * Nullable:  false
     */
    private Integer platformId;

    /**
     * 活动id
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    aid
     * Nullable:  false
     */
    private Integer aid;

    /**
     * 平台用户id
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    uid
     * Nullable:  false
     */
    private Integer uid;

    /**
     * 小程序appid
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    appid
     * Nullable:  false
     */
    private String appid;

    /**
     * 小程序粉丝openid
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    openid
     * Nullable:  false
     */
    private String openid;

    /**
     * 场景值，32位，见微信小程序说明
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    scene_value
     * Nullable:  false
     */
    private String sceneValue;

    /**
     * 创建时间
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    create_time
     * Nullable:  false
     */
    private Date createTime;

    /**
     * 最后更新时间
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    last_upadte_time
     * Nullable:  true
     */
    private Date lastUpadteTime;

    /**
     * 自定义扩展字段
     * <p>
     * Table:     t_applet_scene_detail
     * Column:    extend
     * Nullable:  true
     */
    private String extend;

    public AppletSceneDetail saveExtend(String key, Object value) {
        JSONObject jsonObject = extend == null ? new JSONObject() : JSONObject.fromObject(extend);
        jsonObject.put(key, value);
        extend = jsonObject.toString();
        return this;
    }

}
