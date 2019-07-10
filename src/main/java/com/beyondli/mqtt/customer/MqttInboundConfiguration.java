package com.beyondli.mqtt.customer;

import com.beyondli.mqtt.config.MqttCustomerConfiguration;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * MQTT消费端
 * 
 * @author beyondLi
 *
 */
@Configuration
public class MqttInboundConfiguration {
	
	@Resource
	private MqttCustomerConfiguration mqttCustomerConfiguration;


	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		    String[] array = mqttCustomerConfiguration.getUrl().split(",");
			MqttConnectOptions options = new MqttConnectOptions();
			options.setServerURIs(array);
			options.setUserName(mqttCustomerConfiguration.getUsername());
			options.setPassword(mqttCustomerConfiguration.getPassword().toCharArray());
			//接受离线消息
			options.setCleanSession(false);
			factory.setConnectionOptions(options);
		return factory;
	}
	
	@Bean
	public MessageProducer inbound() {
		String[] inboundTopics = mqttCustomerConfiguration.getTopics().split(",");
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
				mqttCustomerConfiguration.getClientId(),mqttClientFactory(), inboundTopics);
		adapter.setCompletionTimeout(5000);
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public MessageHandler handler() {
		
		return new MessageHandler() {
			public void handleMessage(Message<?> message) throws MessagingException {
				String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
				System.out.println("我接收到了主题为:"+ topic + "的消息推送，内容为:" + message);

			}

		};
	}
}
