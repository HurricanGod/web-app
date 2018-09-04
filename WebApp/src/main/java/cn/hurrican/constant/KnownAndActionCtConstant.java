package cn.hurrican.constant;

import org.springframework.stereotype.Component;

/**
 * @Author: Hurrican
 * @Description: 知而行定制相关常量类
 * @Date 2018/8/30
 * @Modified 15:08
 */
@Component
public class KnownAndActionCtConstant {

    /**
     * 知而行 openid 推送接口，使用<b>POST</b>请求，请求参数如下：<br/>
     * <ul>
     *     <li>app_id    &nbsp&nbsp&nbsp&nbsp   参数值固定为 10038，字符型</li>
     *     <li>scene_id   &nbsp&nbsp&nbsp&nbsp  参数在奖品记录里的某个字段，整型</li>
     *     <li>open_id    &nbsp&nbsp&nbsp&nbsp  可以唯一标志用户的字符串，字符型</li>
     *     <li>subscribe_time   &nbsp&nbsp&nbsp&nbsp    当前时间，字符型</li>
     *     <li>sign &nbsp&nbsp&nbsp&nbsp   签名串，字符型</li>
     * </ul>
     */
    public static final String PUSH_OPENID_URL = "http://app.sweetmartmarketing.com/WXPubServer/wxapp/member.do";

    /**
     * Awards.busiExtend 扩展 json 里中的一个字段，表示模版场景值
     */
    public static final String TEMPLATE_SCENE = "templateScene";


    /**
     *
     */
    private static final  Integer UID = 488142;



    public static Integer getUid(){
        return UID;
    }

    public static Integer getAppid(){
        return 10038;
    }

    public static String getSignKey(){
        return "TNkB8dyBFn9gEkl1lSVmHZV5kR85K6Ht";
    }



}
