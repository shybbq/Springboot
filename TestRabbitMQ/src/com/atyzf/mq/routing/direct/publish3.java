package com.atyzf.mq.routing.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class publish3 {

	public static final String EXCHANGE_NAME = "exer_Exchange1";
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		// 声明转发器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String message = "WE ARE USE DIRECT!!!";
		System.out.println("Ready Sent black'" + message + "'"); 
		QueueingConsumer consuemr = new QueueingConsumer(channel);
		// 声明routignKey
		channel.basicPublish(EXCHANGE_NAME, "black", null, message.getBytes());
		System.out.println("Done");
		channel.close();
		conn.close();
	}
}
