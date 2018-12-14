package ejerc5PCtamanio;

import java.io.Serializable;

public class Item implements Serializable{
	private String descripccion;
	private int cantidad;
	
	public Item(String descripcion, int cantidad) {
		this.descripccion = descripcion;
		this.cantidad = cantidad;
	}
	
	public int getCatidad() {
		return this.cantidad;
	}
	
	public String getDescripcion(){
		return this.descripccion;
	}
}
	