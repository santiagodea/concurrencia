package ejerc1PCHilos;


public class Main {

	public static void main(String[] args) {
		
		Thread productor1  = new Thread(new Producer("productor uno"), "productor  1");
		
		Thread consumidor1  = new Thread(new Consumer("consumidor uno"), "consumidor  1");

		productor1.start();
		consumidor1.start();

	}

}