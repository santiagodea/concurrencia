package ejerc5PCtamanio;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

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
			Queue queue = session.createQueue("colaProductos");
			Queue queueT = session.createQueue("colaTamanio");
			
			// Consumer
			connection.start();
			
			//para recibir un  objeto como msj (ej 2), se hace lo siguiente
			
			//consumo de msj
			System.out.println("esperando mensaje...");
			
			//aca consumo el producto
			MessageConsumer mc = session.createConsumer(queue);
			ObjectMessage om = (ObjectMessage) mc.receive();
			Item i = (Item) om.getObject();
			System.out.println(this.name + " consumi un msj " + i.getDescripcion() + " con cantidad " + i.getCatidad());

			//aca le aviso a la cola con el tamanio que hay un lugar
			MessageProducer mp = session.createProducer(queueT);
			Message message = session.createTextMessage("unLugar");
			mp.send(message);
			System.out.println(this.name + "agrego un lugar en la cola de tamanio");
			
			
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
