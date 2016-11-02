package com.atyzf.mq.publish.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

// 模拟发布者并不知道该将消息发送那个队列中的情况
public class Producer2 {

	public static final String EXCHANGE_NAME = "exer_Exchange"; 
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		// 交换机声明 一参为交换机名称，二参为广播类型
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String message = "Hello EveryBody!!!";
		System.out.println("Ready Sent '" + message + "'");  
		// 开始发布 
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println("Done");
		channel.close();
		conn.close();
	}
}
