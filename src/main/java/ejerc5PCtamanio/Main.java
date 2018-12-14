package ejerc5PCtamanio;

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
	
	public static void inicializarConeccion(int cantidad) {
		try {
			connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		} catch (JMSException e) {
			e.printStackTrace();
		}

		try {
			Queue queue = session.createQueue("colaTamanio");
			MessageProducer producer = session.createProducer(queue);

			// generacion de msj
			for (int i = 0; i < cantidad; i++) {
				Message message = session.createTextMessage("lugar nro " + i);
				producer.send(message);
				System.out.println("se genero el lugar " + i);
			}
			
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
		
		Item item1 = new Item("productoUno", 1);
		Item item2 = new Item("productoDos", 2);
		Item item3 = new Item("productoTres", 3);
		
		inicializarConeccion(5);
		
		Thread productor1  = new Thread(new Producer(item1), "productor  1");
		Thread productor2  = new Thread(new Producer(item2), "productor  2");
		Thread productor3  = new Thread(new Producer(item3), "productor  3");
		Thread productor4  = new Thread(new Producer(item3), "productor  4");
		Thread productor5  = new Thread(new Producer(item2), "productor  5");
		Thread productor6  = new Thread(new Producer(item1), "productor  6");
		
		Thread consumidor1  = new Thread(new Consumer("consumidor uno"), "consumidor  1");
		Thread consumidor2  = new Thread(new Consumer("consumidor dos"), "consumidor  2");
		Thread consumidor3  = new Thread(new Consumer("consumidor tres"), "consumidor  3");
		Thread consumidor4  = new Thread(new Consumer("consumidor cuatro"), "consumidor  4");
		Thread consumidor5  = new Thread(new Consumer("consumidor cinco"), "consumidor  5");
		Thread consumidor6  = new Thread(new Consumer("consumidor seis"), "consumidor  6");

		productor1.start();
		consumidor1.start();

	}

}