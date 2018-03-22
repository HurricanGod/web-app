package cn.hurrican.impl;

import cn.hurrican.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public String sendEmail() {
        System.out.println("success execute send email service!");
        return "success";
    }
}
