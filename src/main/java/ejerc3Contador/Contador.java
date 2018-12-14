package ejerc3Contador;

public class  Contador {
	private static int cantidad = 0;
	
	public Contador() {
		
	}
	
	public void sumar() {
		this.setCantidad(this.getCantidad() + 1);
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		Contador.cantidad = cantidad;
	}
	
}
