package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author Yss
 * @Date 2019/10/23 0023
 */


@Component
public class RabbotMailListener {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitListener(queues = "maila_queue")
    public void sendMail(Email email){

//        System.out.println("shop_mail中listener的RabbitMailListener接收到的email为："+email);
        //创建邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //获得springboot封装的对象
        MimeMessageHelper mimeMessageHelper = null;
        try {
            //将创建的邮件封装至MimeMessageHelper中
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            //设置发送人
            mimeMessageHelper.setFrom(from);
            //设置接收方法
            mimeMessageHelper.setTo(email.getTo());
            //设置邮件标题
            mimeMessageHelper.setSubject(email.getSubject());
            //设置邮件内容
            mimeMessageHelper.setText(email.getContent(),true);
            //设置发送时间
            mimeMessageHelper.setSentDate(new Date());
            //发送邮件
            javaMailSender.send(mimeMessage);
//            System.out.println("shop_mail中listener的RabbitMailListener邮件发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
