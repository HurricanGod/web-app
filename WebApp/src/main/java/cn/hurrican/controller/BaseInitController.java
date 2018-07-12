package cn.hurrican.controller;

import cn.hurrican.anotations.Verify;
import cn.hurrican.model.ResMessage;
import cn.hurrican.utils.BaseController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.ArrayList;

/**
 * @Author: Hurrican
 * @Description:
 * @Date 2018/3/29
 * @Modified 16:42
 */
@Controller
@RequestMapping("/mobile")
public class BaseInitController extends BaseController{



    @RequestMapping(value = "/init.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage init(){

        return ResMessage.creator();
    }


    @RequestMapping(value = "/take.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Verify
    public ResMessage takeContent() {
        Random random = new Random();
        int val = random.nextInt();
        System.out.println("val = " + val);
        return ResMessage.creator().retCodeEqual(0).logIs("success").put("randomInt", val);
    }


    @RequestMapping(value = "/viewTest.do")
    public ModelAndView viewTest(){
        ModelAndView modelAndView = new ModelAndView("redirect:/template/item.jsp");
        modelAndView.addObject("name", "Hurrican");
        modelAndView.addObject("time", "2018年6月1日 11:40:06");

        ArrayList<String> list = new ArrayList<>();
        list.add("aaaaaaa");
        list.add("BBBBBBB");
        list.add("ccccccc");
        list.add("ddddddd");
        modelAndView.addObject("list", list);

        return modelAndView;
    }
}
