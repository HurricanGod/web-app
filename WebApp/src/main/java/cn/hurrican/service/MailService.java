package cn.hurrican.service;


public interface MailService {

    /**
     * 发生邮件
     * @param content
     * @param targetEmail
     */
    void sendEmail(String content, String targetEmail);
}
