package topic2topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicCusumer_2 {
    public static void main(String[] args) throws Exception {
        //        1.创建工厂:使用的是tcp协议 访问同一个mq
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.200.128:61616");
        //        2.获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
//        3.启动连接
        connection.start();

        //        4.获取session(1.是否事务,2消息确认模式)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //        5.创建队列对象:组名,与消息提供方相同
//        Queue queue = session.createQueue("test-queue");
        Topic topic_groupName_B = session.createTopic("text-topic");
        //        6.创建 消息消费者:监听接收消息(监听组名)
        MessageConsumer consumer = session.createConsumer(topic_groupName_B);
//        7.监听消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
//                8.转换坚挺到的 消息类型:五大消息类型之一
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("Cusumer_2 收到消息->"+textMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//        9.制造一个阻塞,是监听器一直生效
        System.in.read();
//        10.关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
