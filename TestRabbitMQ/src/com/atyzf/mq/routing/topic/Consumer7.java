package com.atyzf.mq.routing.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

// 本次发送 <speed>.<color>.<species>类型的topic
public class Consumer7 {
	public static final String EXCHANGE_NAME = "exer_Exchange2";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String queue5 = channel.queueDeclare().getQueue();
		channel.queueBind(queue5, EXCHANGE_NAME, "kernal.*");
		System.out.println("Waiting for  messages--");
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queue5, true, consumer);
		while (true)  
        {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            System.out.println(" [Consumer7] Received kernal.* :'" + message + "'");  
        }  
	}
}
