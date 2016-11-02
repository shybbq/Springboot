package com.atyzf.mq.basic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	public final static String Queue_Name = "Hello MQ!";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		//factory.setUsername("shao");
		factory.setHost("localhost");
		//factory.setPort(2888);
		//factory.setPassword("");
		try {
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			channel.queueDeclare(Queue_Name, false, false, false, null);
			String msg = "HELLO RabbitMQW!";
			channel.basicPublish("", Queue_Name, null, msg.getBytes("UTF-8"));
			System.out.println("Producer Send +'" + msg + "'");
			
			channel.close();
			conn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	
	}
}
