package cn.hurrican.impl;

import cn.hurrican.redis.RedisExecutor;
import cn.hurrican.service.EmailService;
import cn.hurrican.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private EmailService emailService;

    @Override
    public String saveAndCache() {
        System.out.println("save data to db!\n");
        return emailService.sendEmail().toUpperCase();
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
