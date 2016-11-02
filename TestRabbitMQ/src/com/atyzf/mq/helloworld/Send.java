package com.atyzf.mq.helloworld;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private final static String QUEUE_NAME = "hello";  
    public static void main(String[] argv) throws java.io.IOException  
    {
    	ConnectionFactory factory = new ConnectionFactory();
    	try {
			Connection conn = factory.newConnection();
			// 设置MabbitMQ所在主机ip或者主机名 
			factory.setHost("localhost");
			Channel channel = conn.createChannel();
	        /* 指定一个队列  
	         * ①队列名称②开启持久化?true表示是，队列将在服务器重启时生存
	         * 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除
	         * 第四个参数为当所有消费者客户端连接断开时是否自动删除队列
	         * 第五个参数为队列的其他参数
	         */
			channel.queueDeclare(QUEUE_NAME, false, false, true, null);
			String message = "hello world!"; 
			System.out.println("Ready Sent :'" + message + "'");  
			// 往队列中发出一条消息  
	        /*
	         * 第一个参数为交换机名称、第二个参数为队列映射的路由key
	         * 第三个参数为消息的其他属性、第四个参数为发送信息的主
	         */
			channel.basicPublish("",QUEUE_NAME,null, message.getBytes());
			System.out.println("Done");
			channel.close();
			conn.close();
			
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
    }
}
