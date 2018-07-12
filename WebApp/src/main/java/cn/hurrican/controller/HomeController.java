package cn.hurrican.controller;

import cn.hurrican.anotations.ValidateRequestParam;
import cn.hurrican.aop.EnableCache;
import cn.hurrican.exception.BaseAspectRuntimeException;
import cn.hurrican.model.AppletSceneParam;
import cn.hurrican.model.ResMessage;
import cn.hurrican.model.Riddles;
import cn.hurrican.utils.SpringContext;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/home")
public class HomeController {


    @RequestMapping(value = "/{title}/end.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    @ValidateRequestParam(support = Riddles.class)
    public ResMessage takeData(@PathVariable String title, @RequestParam(required = false) String json, HttpServletRequest request){
        System.out.println(title);
        ResMessage message = ResMessage.creator().logIs("ok");
        return message;
    }

    @ExceptionHandler(value = {BaseAspectRuntimeException.class})
    public void handleBaseAspectRuntimeException(HttpServletResponse response, Exception e){

        System.out.println(e.getClass());
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> model = new HashMap<>();
        if(e != null && e instanceof  BaseAspectRuntimeException){
            model.putAll(((BaseAspectRuntimeException) e).getReturnVal());
        }
        if(response != null){
            try {
                PrintWriter writer = response.getWriter();
                JSONObject jsonObject = JSONObject.fromObject(model);
                writer.write(jsonObject.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "/test.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ResMessage test() {
        ResMessage resMessage = ResMessage.creator();
        String className = "cn.hurrican.model.AppletSceneDetail";
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("aid", 1);
        properties.put("appid", "wxc869c96b05be267f");
        properties.put("createtime", new Date());
        properties.put("scenetype", 1);
        properties.put("openid", "dceeewcece_aADASDWQDQ");
        AppletSceneParam param = AppletSceneParam.init(className, properties);
        resMessage.put("param", param);
        return resMessage;
    }


    @RequestMapping(value = "/getContext.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage getContext(){
        ResMessage resMessage = ResMessage.creator();
        Map<String, Object> beansWithAnnotation = SpringContext.getContext().getBeansWithAnnotation(EnableCache.class);
        beansWithAnnotation.forEach((k,v) -> {
            System.out.println(k);
            System.out.println(v);
            Annotation[] annotations = v.getClass().getAnnotations();
            Arrays.asList(annotations).forEach(e -> {
                System.out.println(e.toString());
            });
        });
        return resMessage;
    }

}

