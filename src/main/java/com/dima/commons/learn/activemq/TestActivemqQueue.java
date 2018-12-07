package com.dima.commons.learn.activemq;

import java.io.IOException;
import java.util.Date;

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
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

/**
 * Title: TestActivemqQueue
 * Description: Activemq测试--Queue点对点模式
 * @author Dima
 * @date 2018年12月7日 下午9:52:34
 */
public class TestActivemqQueue {
	
	//测试Queue形式的发布消息(点对点模式--生产者)
    @SuppressWarnings("static-access")
	@Test
    public void testQueueProducer() throws JMSException{
        //1、创建一个连接工厂对象ConnectionFactory对象，指定服务的IP和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.107.142.11:61616");
        //2、使用ConnectionFactory对象创建Connection连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接，调用start方法
        connection.start();
        //4、使用Connection对象创建session对象
        //第一个参数：是否开启事务，一般不开启，false；当第一个参数为false时，第二个参数才有意义。
        //第二个参数：消息的应答模式：手动应答、自动应答，一般使用自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用Session创建Destination目的地对象，有两种：Queue、Topic
        Queue queue = session.createQueue("testActiveMq");
        //6、使用session创建一个producer对象
        MessageProducer producer = session.createProducer(queue);
        //7、使用session创建一个TextMessage对象（五种格式：StreamMessage 、MapMessage、TextMessage、ObjectMessage、BytesMessage）
        String text = "【这是使用activemq点对点模式发送的消息,消息发送时间：" + new DateFormatUtils().format(new Date(), "yyyy-MM-dd HH:mm:ss" + ",时间毫秒值：" + System.currentTimeMillis() + "】");
        TextMessage message = session.createTextMessage(text);//消息内容
        //8、使用producer对象发布消息
        producer.send(message);
        System.out.println("点对点模式--生产者--消息发送成功！时间毫秒值：" + System.currentTimeMillis());
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }
    
  //queue消费者测试(点对点模式--消费者)
    @Test
    public void testQueueConsumer() throws JMSException, IOException{
        //1、创建一个连接工厂对象ConnectionFactory对象，指定服务的IP和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.107.142.11:61616");
        //2、使用ConnectionFactory对象创建Connection连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接，调用start方法
        connection.start();
        //4、使用Connection对象创建session对象
        //第一个参数：是否开启事务，一般不开启，false；当第一个参数为false时，第二个参数才有意义。
        //第二个参数：消息的应答模式：手动应答、自动应答，一般使用自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用Session创建Destination目的地对象，有两种：Queue、Topic
        Queue queue = session.createQueue("testActiveMq");
        //6、使用session创建一个consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7、接受消息
        consumer.setMessageListener(new MessageListener() {
            @SuppressWarnings("static-access")
			public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println("接收到的activemq消息内容：" + text + ",消息接收时间：" + new DateFormatUtils().format(new Date(), "yyyy-MM-dd HH:mm:ss") + ",时间毫秒值：" + System.currentTimeMillis());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //8、等待键盘输入(程序等待，可以继续接收消息)
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
