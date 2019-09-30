package com.tensquare.rabbitmq.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.common.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
@PropertySource(value = "classpath:properties/sms.properties",encoding = "utf-8")
public class SmsListener {

    @Value("${sms.signname}")
    private String sign;

    @Value("${sms.templateCode}")
    private String template;

    @Autowired
    private SmsUtil smsUtil;

    //向阿里大于平台提交发送短信验证码请求
    @RabbitHandler
    public void sendToAliDaYu(Map map) {
        String mobile = (String) map.get("mobile");
        String param = "{\"code\":\"" + map.get("validCode") + "\"}";
        try {
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, template, sign, param);
            System.out.println(sendSmsResponse.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

}
