package com.ruoyi.common.utils.mail;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 14:59
 */

public interface MailService {
    /**
     * 发送文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    void sendSimpleMail(String to, String subject, String content, String... cc);
}
