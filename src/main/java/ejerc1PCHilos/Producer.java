package ejerc1PCHilos;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer implements Runnable {
	private Connection connection = null;
	private ConnectionFactory connectionFactory = null;
	Session session = null;
	
	String name = "";

	public Producer(String msj) {
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
			MessageProducer producer = session.createProducer(queue);

			// generacion de msj
			String body = this.name;
			Message message = session.createTextMessage(body);
			
//			para pasar un  objeto como msj (ej 2), se hace lo siguiente
//			MessageProducer mp = session.createProducer(queue);
//			ObjectMessage om = session.createObjectMessage();
//			om.setObject(item);
//			mp.send(om);
			
			
			System.out.println("Enviando mensaje [" + body + "]");
			producer.send(message);
			//

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

}
