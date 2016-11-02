package com.atyzf.mq.basic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Customer {

	public final static String Queue_Name = "Hello MQ!";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try {
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			channel.queueDeclare(Queue_Name, false, false, false, null);
			System.out.println("Customer Waiting Received messages");
			
			Consumer consumer = new DefaultConsumer(channel){
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
	                System.out.println("Customer Received '" + message + "'");
				}
				//自动回复队列应答 -- RabbitMQ中的消息确认机制      
			};
			channel.basicConsume(Queue_Name, true, consumer);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
