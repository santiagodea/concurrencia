package ejerc4Tenores;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TenorA implements Runnable {
	private Connection connection = null;
	private ConnectionFactory connectionFactory = null;
	Session session = null;

	private String name;

	public TenorA() {
		super();
		this.name = Thread.currentThread().getName();
		try {
			this.connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			this.connection = this.connectionFactory.createConnection();
			this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			this.connection.start();

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			Queue queue = session.createQueue("ejemploQueue");
			MessageProducer producer = session.createProducer(queue);
			MessageConsumer consumer = session.createConsumer(queue);

			// generacion de msj

			TextMessage textMsg = (TextMessage) consumer.receive();
			//System.out.println(this.name + " recibi el msj " + textMsg);

			String mensajito = textMsg.getText();

			if (mensajito.equals(Thread.currentThread().getName())) {
				
				System.out.println("Inicio Himno");
				// accion del tenor
				System.out.println(Thread.currentThread().getName() + ": " + "Oid mortales - A");

				String msj = "Tenor B";
				Message message = session.createTextMessage(msj);
				producer.send(message);
			} else {
				producer.send(textMsg);
			}

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
