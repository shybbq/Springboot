package com.atyzf.mq.routing.topic;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

// 本次发送 <speed>.<color>.<species>类型的topic
public class producer4 {

	public static final String EXCHANGE_NAME = "exer_Exchange2";
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String[] routing_keys = new String[] { "kernal.info", "cron.warning",  
                "auth.info", "kernal.debug" }; 
		for (String routing_key : routing_keys){
            String msg = UUID.randomUUID().toString();  
            System.out.println("Ready Sent routingKey = "+routing_key+" ,msg = " + msg + ".");  
            channel.basicPublish(EXCHANGE_NAME, routing_key, null, msg.getBytes());  
            System.out.println("Done");
	     } 
		/*String message = "MY TEST ON TOPIC";
		System.out.println("READY TO SEND TOPIC---");
		channel.basicPublish(EXCHANGE_NAME,"*.red.*", null, message.getBytes());
		System.out.println("Done");*/
		channel.close();
		conn.close();
	}
}
