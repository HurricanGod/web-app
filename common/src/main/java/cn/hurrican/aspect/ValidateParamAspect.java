package cn.hurrican.aspect;

import cn.hurrican.anotations.ValidateRequestParam;
import cn.hurrican.exception.BaseAspectRuntimeException;
import cn.hurrican.model.ResMessage;
import cn.hurrican.model.Riddles;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/3/28
 * @Modified 17:27
 */
@Component
@Aspect
public class ValidateParamAspect {

    @Pointcut("@annotation(cn.hurrican.anotations.ValidateRequestParam)")
    public void validate(){
    }

    @Before("validate() && @annotation(args)")
    public ResMessage doCheckRequestParam(JoinPoint point, ValidateRequestParam args){
        System.out.println("exec doCheckRequestParam method");
        ResMessage message = ResMessage.creator();
        Object[] params = point.getArgs();
        HttpServletRequest request = null;
        for (int i = 0; i < params.length; i++) {
            System.out.println("params[i] = " + params[i]);
            if(params[i] instanceof HttpServletRequest){
                request = (HttpServletRequest) params[i];
            }
        }
        if(request != null){
            List<Class<?>> list = Arrays.stream(args.support()).filter(Riddles.class::equals).collect(Collectors.toList());
            if(list != null && list.size() > 0){
                HashMap<String, Object> model = new HashMap<>();
                model.put("test", "test");
                throw BaseAspectRuntimeException.happend(-1, "参数非法！").returnMapEqual(model);
            }
        }

        return message;
    }
}
