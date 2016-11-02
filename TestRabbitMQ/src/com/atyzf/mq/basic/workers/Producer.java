package com.atyzf.mq.basic.workers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

	public final static String QUEUE_NAME = "ASSIGN_WORKERS";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, true, null);
		for(int i = 9; i > 0 ; i--){
			String message = "hello world"+i; 
			System.out.println("准备发送处理耗时为: "+i+"MS 消息   --"+"'" + message + "'");  
			// 通过设置服务端①queueDeclare第二个参数为true②basicPublish的第三参数为MessageProperties.PERSISTENT_TEXT_PLAIN
			// 开启持久化，使得服务重启，队列中的数据依旧存在
			channel.basicPublish("",QUEUE_NAME,null, message.getBytes());
			System.out.println("Done");
		}
		channel.close();
		conn.close();
	}
}
