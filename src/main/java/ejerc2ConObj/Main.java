package ejerc2ConObj;


public class Main {

	public static void main(String[] args) {
		
		Item item1 = new Item("productoUno", 1);
		Item item2 = new Item("productoDos", 2);
		Item item3 = new Item("productoTres", 3);
		
		
		Thread productor1  = new Thread(new Producer(item1), "productor  1");
		
		Thread consumidor1  = new Thread(new Consumer("consumidor uno"), "consumidor  1");

		productor1.start();
		consumidor1.start();

	}

}