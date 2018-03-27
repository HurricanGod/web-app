package cn.hurrican.controller;

import cn.hurrican.anotations.SystemLog;
import cn.hurrican.model.ResMessage;
import cn.hurrican.service.EmailService;
import cn.hurrican.utils.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/email/service/")
public class EmailController extends BaseController {

    @Autowired
    private EmailService emailService;


    @SystemLog(level = 1)
    @RequestMapping(value = "send/{target}/{title}/end.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResMessage sendEmail(@PathVariable("target") String target, @PathVariable("title") String title) {
        System.out.println("execute send Email Service!");
        System.out.println("target = " + target);
        System.out.println("title = " + title);
        return ResMessage.creator().logIs("success execute!");
    }
}
