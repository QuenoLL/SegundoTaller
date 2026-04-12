package Dominio;

public class Pokemon {
	
	private String nombre;
	private String habitat;
	private Double porcAparicion;
	private int stats;
	private String tipo;
	private String estado;
	
	//Constructor...
	public Pokemon(String nombre, String habitat, Double porcAparicion, int stats, String tipo, String estado) {
		this.nombre = nombre;
		this.habitat = habitat;
		this.porcAparicion = porcAparicion;
		this.stats = stats;
		this.tipo = tipo;
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	//Tetracristalizacion...
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}
	
	//Pensar....Puede que no lo ocupe...
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public String getHabitat() {
		return habitat;
	}

	public Double getPorcAparicion() {
		return porcAparicion;
	}

	public int getStats() {
		return stats;
	}
	
	
	
	

}
