package ejerc3Contador;

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

public class HiloContador2 implements Runnable {
	private Connection connection = null;
	private ConnectionFactory connectionFactory = null;
	Session session = null;
	
	
	String name = "";
	Contador contador;

	public HiloContador2(String msj, Contador contador) {
		super();
		this.name = msj;
		this.contador = contador;
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
			String body = this.name;
			Message message = session.createTextMessage(body);
			
			for (int i = 0; i < 20; i++) {
				
				TextMessage textMsg = (TextMessage) consumer.receive();
				System.out.println(this.name + " consumi un msj " + textMsg);
				
				
				this.contador.sumar();
				System.out.println(this.contador.getCantidad()); 
				
				System.out.println("Enviando mensaje [" + body + "]");
				producer.send(message);
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

