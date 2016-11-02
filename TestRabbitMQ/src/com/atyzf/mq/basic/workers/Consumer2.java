package com.atyzf.mq.basic.workers;
//模拟同一个队列下的消费者
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer2 {
	public final static String QUEUE_NAME = "ASSIGN_WORKERS";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, true, null);
		System.out.println("Waiting for messages:	"); 
		// 创建队列消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 通过设置basicConsume第二个参数为false打开消息应答机制，使工作者断开后，其未接受信息被其他消费者接受
		boolean ack = false;
		// 指定消费队列
		// boolean ack = true;
		channel.basicConsume(QUEUE_NAME, ack, consumer);
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            // 每次处理完消息后，手动发送应答
            // channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); 
            System.out.println("[Consumer2]"+" Received :'" + message + "'"); 
            // 模拟处理时间
            analyseData(message);
            System.out.println("[Consumer2]"+"Done");  
		}
	}
	public static void analyseData(String data) throws InterruptedException{
		int dealTime = data.codePointAt(data.length()-1);
		Thread.sleep(dealTime*100);
	}
}
