package com.beyondli.controller;

import com.beyondli.mqtt.producer.PublishMessage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by beyondLi
 * Date 2019/7/10 10:45
 * Desc .推送发布信息至mqtt
 */
@RestController
public class MQTTController {
    @Resource
    PublishMessage publishMessage;
    @RequestMapping("/send")
    public void send(String msg) throws MqttException {
        publishMessage.start("topic01", msg);
    }
}
