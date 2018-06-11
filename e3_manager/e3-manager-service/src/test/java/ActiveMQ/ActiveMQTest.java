package ActiveMQ;//package ActiveMQ;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//
//import javax.jms.*;
//import java.io.IOException;
//
//public class ActiveMQTest {
//
//    /**
//     * 生产者：生产消息，发送端
//     * @throws Exception
//     */
//    @Test
//    public void testQueueProducer() throws Exception{
////        第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
////        第二步：使用ConnectionFactory对象创建一个Connection对象。
//        Connection connection = connectionFactory.createConnection();
////        第三步：开启连接，调用Connection对象的start方法。
//        connection.start();
////        第四步：使用Connection对象创建一个Session对象。
//        //第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
//        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
////        第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
//        //参数：队列的名称。
//        Queue queue = session.createQueue("test-queue");
////        第六步：使用Session对象创建一个Producer对象。
//        MessageProducer producer = session.createProducer(queue);
////        第七步：创建一个Message对象，创建一个TextMessage对象。
//        TextMessage textMessage = session.createTextMessage("this is my first test!!!");
////        第八步：使用Producer对象发送消息。
//        producer.send(textMessage);
////        第九步：关闭资源。
//        producer.close();
//        session.close();
//        connection.close();
//    }
//
//
//    /**
//     * 消费者
//     * @throws JMSException
//     * @throws IOException
//     */
//    @Test
//    public void testQueueConsumer() throws JMSException, IOException {
//        //        第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
////        第二步：使用ConnectionFactory对象创建一个Connection对象。
//        Connection connection = connectionFactory.createConnection();
////        第三步：开启连接，调用Connection对象的start方法。
//        connection.start();
////        第四步：使用Connection对象创建一个Session对象。
//        //第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
//        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
////        第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
//        //参数：队列的名称。
//        Queue queue = session.createQueue("spring-queue");
////        第六步：使用Session对象创建一个Consumer对象。
//        MessageConsumer consumer = session.createConsumer(queue);
////         接收消息
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                TextMessage textMessage = (TextMessage) message;
//                String text;
//                try {
//                    text=textMessage.getText();
//                    System.out.println(text);
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
////     等待接收消息
//        System.in.read();
////        关闭资源
//        consumer.close();
//        session.close();
//        connection.close();
//    }
//
//    /**
//     * 发送者
//     * @throws Exception
//     */
//    @Test
//    public void testTopicProducer() throws Exception{
////        第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
////        第二步：使用ConnectionFactory对象创建一个Connection对象。
//        Connection connection = connectionFactory.createConnection();
////        第三步：开启连接，调用Connection对象的start方法。
//        connection.start();
////        第四步：使用Connection对象创建一个Session对象。
//        //第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
//        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
////        第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
//        //参数：队列的名称。
//        Topic topic = session.createTopic("test-topic");
////        第六步：使用Session对象创建一个Producer对象。
//        MessageProducer producer = session.createProducer(topic);
////        第七步：创建一个Message对象，创建一个TextMessage对象。
//        TextMessage textMessage = session.createTextMessage("this is my first test!!!");
////        第八步：使用Producer对象发送消息。
//        producer.send(textMessage);
////        第九步：关闭资源。
//        producer.close();
//        session.close();
//        connection.close();
//    }
//
//    /**
//     * 消费者
//     * @throws JMSException
//     * @throws IOException
//     */
//    @Test
//    public void testTopicConsumer() throws JMSException, IOException {
//        //        第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
////        第二步：使用ConnectionFactory对象创建一个Connection对象。
//        Connection connection = connectionFactory.createConnection();
////        第三步：开启连接，调用Connection对象的start方法。
//        connection.start();
////        第四步：使用Connection对象创建一个Session对象。
//        //第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
//        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
////        第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
//        //参数：队列的名称。
//        Topic topic = session.createTopic("test-topic");
////        第六步：使用Session对象创建一个Consumer对象。
//        MessageConsumer consumer = session.createConsumer(topic);
////         接收消息
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                TextMessage textMessage = (TextMessage) message;
//                String text;
//                try {
//                    text=textMessage.getText();
//                    System.out.println(text);
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
////     等待接收消息
//        System.in.read();
////        关闭资源
//        consumer.close();
//        session.close();
//        connection.close();
//    }
//}
