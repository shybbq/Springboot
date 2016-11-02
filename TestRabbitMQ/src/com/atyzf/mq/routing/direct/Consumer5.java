package com.atyzf.mq.routing.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

// 新的队列对转发器上的指定的black消息感兴趣(routing),带了routingKey 使用direct
// 思考消费者怎么接受routingKey
public class Consumer5 {

	public static final String EXCHANGE_NAME = "exer_Exchange1";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String queue3 = channel.queueDeclare().getQueue();
		channel.queueBind(queue3, EXCHANGE_NAME, "black");
		System.out.println("Waiting for black messages--");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queue3, true, consumer);
		while (true)  
        {  
	        QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
	        String message = new String(delivery.getBody());  
	        System.out.println(" [Consumer5] Received '" + message + "'");  
        } 
	}
}
