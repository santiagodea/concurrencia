package ar.com.ciu.jms.async.broker;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MainQueueBrowser {

	public static void main(String[] args) throws JMSException {
		Connection connection = null;
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("ejemploQueue");
			connection.start();
			System.out.println("Mensajes en la cola:");
			QueueBrowser browser = session.createBrowser(queue);
			@SuppressWarnings("rawtypes")
			Enumeration e = browser.getEnumeration();
			while (e.hasMoreElements()) {
				TextMessage message = (TextMessage) e.nextElement();
				System.out.println("Get [" + message.getText() + "]");
			}
			System.out.println("Fin");
			browser.close();
			session.close();
		} catch (JMSException e1) {
			e1.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}
