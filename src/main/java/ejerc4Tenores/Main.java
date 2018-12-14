package ejerc4Tenores;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Main {
	
	static Connection connection = null;
	static ConnectionFactory connectionFactory = null;
	static Session session = null;

	public static void inicializarConeccion(String mensaje) {
		try {
			connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		} catch (JMSException e) {
			e.printStackTrace();
		}

		try {
			Queue queue = session.createQueue("ejemploQueue");
			MessageProducer producer = session.createProducer(queue);

			// generacion de msj
			Message message = session.createTextMessage(mensaje);
			producer.send(message);
			session.close();
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
	
			Thread ca = new Thread(new TenorA(),"Tenor A");
			Thread cb = new Thread(new TenorB(),"Tenor B");
			Thread cc = new Thread(new TenorC(),"Tenor C");
			
			inicializarConeccion("Tenor A");
			
			cc.start();
			ca.start();
			cb.start();
			
			
	}

}
