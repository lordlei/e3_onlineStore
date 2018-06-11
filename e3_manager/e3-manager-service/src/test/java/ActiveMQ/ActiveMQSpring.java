package ActiveMQ;//package ActiveMQ;
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//
//import javax.jms.Destination;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.Session;
//
//
///**
// * activeMq与spring的整合
// */
//public class ActiveMQSpring {
//
//
//    @Test
//    public void testSpringActivemq() {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
//        //从spring容器中获得JmsTemplate对象
//        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
//        //从spring容器中获得Destination对象
//        Destination destination = (Destination) applicationContext.getBean("queueDestination");
//        //使用JmsTemplate对象发送消息。
//        jmsTemplate.send(destination, new MessageCreator() {
//            @Override
////            创建一个消息对象并返回
//            public Message createMessage(Session session) throws JMSException {
//                //得到session ,并创建message返回
//                return session.createTextMessage("spring activemq queue message");
//            }
//        });
//
//    }
//
//
//}
