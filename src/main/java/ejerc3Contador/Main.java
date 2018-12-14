package ejerc3Contador;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Main {

	static Connection connection = null;
	static ConnectionFactory connectionFactory = null;
	static Session session = null;

	public static void inicializarConeccion() {
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
			String body = "mensaje de inicializacion";
			Message message = session.createTextMessage(body);

			System.out.println("Enviando mensaje [" + body + "]");
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

		Contador contador = new Contador();
		Thread hilo1 = new Thread(new HiloContador("CONTADOR 1", contador), "hilo  1");
		Thread hilo2 = new Thread(new HiloContador2("CONTADOR 2", contador), "hilo	2");

		inicializarConeccion();

		hilo1.start();
		hilo2.start();

	}

}