package cn.hurrican.service;

import cn.hurrican.annotation.EnableCache;
import org.springframework.stereotype.Component;

@Component
@EnableCache
public class MailServiceImpl implements MailService {

    /**
     * 发生邮件
     *
     * @param content
     * @param targetEmail
     */
    @Override
    public void sendEmail(String content, String targetEmail) {
        System.out.println("send email...");
    }
}
