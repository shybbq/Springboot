package com.atyzf.mq.basic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Work1 {
	
	//定义一个队列名称
	private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
    	
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        // 第一个参数:队列名字，第二个参数:是否序列化，服务启动依旧存在，第三是独占队列（创建者可以使用的私有队列，断开后自动删除）
        // 第四个参数：第四个参数为当所有消费者客户端连接断开时是否自动删除队列 第五个:第五个参数为队列的其他参数
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("Worker1  Waiting for messages");
        //每次从队列获取的数量       channel.basicQos(1);保证一次只分发一个
        channel.basicQos(1);
        
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                
                System.out.println("========================");
                System.out.println("Worker1  Received '" + message + "'");
                System.out.println("========================");
                
                try {
                    // throw new Exception();
                    doWork(message);                
                    }catch (Exception e){
                    channel.abort();
                }finally {
                    System.out.println("Worker1 Done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        // autoAck是否自动回复，如果为true的话，每次生产者只要发送信息就会从内存中删除
        boolean autoAck=false;
        //消息消费完成确认        
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
    }
    private static void doWork(String task) {
        try {
            Thread.sleep(1000);
            // 暂停1秒钟        
            } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
