package com.atyzf.mq.publish.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer4 {

	public static final String EXCHANGE_NAME = "exer_Exchange";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		// 同时声明该转发器名称及类型
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		// 创建一个非持久的、唯一的且自动删除的队列  
		String queue2 = channel.queueDeclare().getQueue();
		System.out.println(queue2);
		// 将队列和转发器进行绑定(第三个参数为routingKey)
		channel.queueBind(queue2, EXCHANGE_NAME, "");
		System.out.println("Waiting for messages--");
		QueueingConsumer consumer = new QueueingConsumer(channel);
        // 指定接收者，第二个参数为自动应答，无需手动应答  
        channel.basicConsume(queue2, true, consumer);  
        while (true)  
        {  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            System.out.println(" [Consumer4] Received '" + message + "'");  
        }  
	}
}
