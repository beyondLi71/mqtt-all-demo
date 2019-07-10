package com.beyondli.mqtt.producer;

import com.beyondli.mqtt.config.MqttProducerConfiguration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author beyondLi
 */
@Configuration
public class PublishMessage {
    @Resource
    MqttProducerConfiguration mqttProducerConfiguration;

    private  MqttClient connect(String clientId, String userName, String password) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        MqttClient client = new MqttClient(mqttProducerConfiguration.getUrl(), clientId, persistence);
        //目前不需要回调
        //client.setCallback(new PushCallback());
        client.connect(options);
        return client;
    }

    private  void publish(MqttClient client, String msg, String topic) throws MqttException {
        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(mqttProducerConfiguration.getQos());
        message.setRetained(false);
        client.publish(topic, message);
    }

    public  void start(String topic, String msg) throws MqttException {

        MqttClient client = connect(mqttProducerConfiguration.getClientId(), mqttProducerConfiguration.getUsername(), mqttProducerConfiguration.getPassword());
        if (client != null) {
            publish(client, msg, topic);
            System.out.println("我推送给了'" + topic + "'主题，推送的内容为:" + msg);
        }
        if (client != null) {
            client.disconnect();
        }
    }
}
