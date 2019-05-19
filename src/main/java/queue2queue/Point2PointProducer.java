package queue2queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Point2PointProducer {
    public static void main(String[] args) throws JMSException {
//        1.创建连接工厂:java中使用的是Tcp连接,java外部使用http连接http://192.168.200.128:8161/
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.200.128:61616");
//        2.获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
//        3.启动连接
        connection.start();

//        4.获取Session(1.是否启动事务,2.消息确认模式)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//        5.创建队列对象:组名
        Queue queue = session.createQueue("test-queue");
//        6.创建消息生产者:消息发送器
        MessageProducer producer = session.createProducer(queue);
//        7.创建消息:消息内容
        TextMessage textMessage = session.createTextMessage("第二次实时:组名:test-queue,消息发送器:producer,消息内容:textMessage");
//        8.发送消息:使用消息发送器发送消息(消息内容)
        producer.send(textMessage);
//        9.关闭资源
        producer.close();
        session.close();
        connection.close();


    }
}
