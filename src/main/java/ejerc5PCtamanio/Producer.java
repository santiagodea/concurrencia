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

public class Producer implements Runnable {
	private Connection connection = null;
	private ConnectionFactory connectionFactory = null;
	Session session = null;
	
	Item item;

	public Producer(Item item) {
		super();
		this.item = item;
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
			Queue queue = session.createQueue("colaProductos");
			Queue queueT = session.createQueue("colaTamanio");
			
			//MessageProducer producer = session.createProducer(queue);
			
			//ACA SACO UN MENSAJE DE LA COLA DE TAMANIO, PARA AVISAR QUE OCUPE UN LUGAR
			//aca consumo el producto
			MessageConsumer mc = session.createConsumer(queueT);
			Message omT = (Message) mc.receive();
			//Item i = (Item) omT.getObject();
			System.out.println("un productor elimino un lugar en la cola de tamanio");
			
//			para pasar un  objeto como msj (ej 2), se hace lo siguiente
			//ACA AGREGO UN PRODUCTO A LA COLA DE PRODUCTOS
			MessageProducer mp = session.createProducer(queue);
			ObjectMessage om = session.createObjectMessage();
			om.setObject(item);
			mp.send(om);
						
			
			
			System.out.println("Enviando mensaje [" + this.item.getDescripcion() + "]");
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
