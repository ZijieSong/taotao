package com.itheima.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

public class QueueProducer {
	//生产者发送消息
	@Test
	public void send() throws Exception{
		//1.创建一个连接工厂 connectionfactory
		//参数：就是要连接的服务器的地址
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://10.211.55.3:61616");
		//2.通过工厂获取连接对象 创建连接
		Connection connection = factory.createConnection();
		//3.开启连接
		connection.start();
		//4.创建一个session对象  提供发送消息等方法
		//第一个参数：表示是否开启分布式事务（JTA）  一般是false 不开启。
		//第二个参数：就是设置消息的应答模式   如果 第一个参数为false时，第二个参数设置才有意义。用的是自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//
		//5.创建目的地 （destination）  queue  
		//参数：目的地的名称
		Queue queue = session.createQueue("queue-test");
		//6.创建个生产者
		MessageProducer producer = session.createProducer(queue);
		//7.构建消息的内容  
		TextMessage textMessage = session.createTextMessage("queue测试发送的消息");
//		TextMessage message = session.createTextMessage();
//		message.setText("queue测试发送的消息");
		//8.发送消息
		producer.send(textMessage);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
}
