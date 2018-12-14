package ar.com.ciu.jms.async.producer;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProducerQueueClient {

	public static void main(String[] args) throws URISyntaxException, Exception {
		Connection connection = null;
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("ejemploQueue");
			MessageProducer producer = session.createProducer(queue);
			String preBody = "Mensaje";
			for (int i = 0; i < 10; i++) {
				String body = preBody + i;
				Message message = session.createTextMessage(body);
				System.out.println("Enviando mensaje [" + body + "]");
				producer.send(message);
			}
			producer.send(session.createTextMessage("END"));
			session.close();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}
