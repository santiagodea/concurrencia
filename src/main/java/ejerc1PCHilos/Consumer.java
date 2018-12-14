package ejerc1PCHilos;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable {
	private Connection connection = null;
	private ConnectionFactory connectionFactory = null;
	Session session = null;
	private String name = "";

	public Consumer(String msj) {
		super();
		this.name = msj;
		try {
			this.connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			this.connection = this.connectionFactory.createConnection();
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {

		try {
			Queue queue = session.createQueue("ejemploQueue");
			// Consumer
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			
//			para recibir un  objeto como msj (ej 2), se hace lo siguiente
//			MessageConsumer mc = session.createConsumer(queue);
//			ObjectMessage om = (ObjectMessage) mc.receive();
//			Item i = om.getObject();
			
			//consumo de msj
			System.out.println("esperando mensaje...");
			TextMessage textMsg = (TextMessage) consumer.receive();
			System.out.println(this.name + " consumi un msj " + textMsg);

		} catch (JMSException e) {
		
			e.printStackTrace();

		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
