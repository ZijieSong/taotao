package com.itheima.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

public class QueueCustomer {
	@Test
	public void recieve() throws Exception {
		//1.创建连接的工厂
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://10.211.55.3:61616");
		//2.创建连接
		Connection connection = factory.createConnection();
		//3.开启连接
		connection.start();
		//4.创建session
		//第一个参数：表示是否开启分布式事务（JTA）  一般是false 不开启。
		//第二个参数：就是设置消息的应答模式   如果 第一个参数为false时，第二个参数设置才有意义。用的是自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建接收消息的一个目的地
		Queue queue = session.createQueue("queue-test");
		//6.创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//7.接收消息 打印
			//第一种
		/*while(true){
			Message message = consumer.receive(1000000);//设置接收消息的超时时间
			//没有接收到消息就跳出循环
			if(message==null){
				break;
			}
			if(message instanceof TextMessage){
				TextMessage message2 = (TextMessage) message;
				System.out.println("接收的消息为"+message2.getText());
			}
		}*/
			//第二种
		
		//设置一个监听器
		//System.out.println("start");
		//这里其实开辟了一个新的线程
		consumer.setMessageListener(new MessageListener() {
			
			//当有消息的时候会执行以下的逻辑
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage){
					TextMessage message2 = (TextMessage) message;
					try {
						System.out.println("接收的消息为"+message2.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		//System.out.println("end");
		Thread.sleep(199999);
		//8.关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
