package com.atyzf.mq.routing.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

// 新的队列对转发器上的指定的white消息感兴趣(routing),带了routingKey 使用direct
public class Consumer6 {

	public static final String EXCHANGE_NAME = "exer_Exchange1";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String queue4 = channel.queueDeclare().getQueue();
		channel.queueBind(queue4, EXCHANGE_NAME, "white");
		System.out.println("Waiting for white messages--");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queue4, true, consumer);
		while (true)  
        {  
	        QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
	        String message = new String(delivery.getBody());  
	        System.out.println(" [Consumer6] Received '" + message + "'");  
        } 
	}
}
