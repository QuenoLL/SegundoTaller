package Dominio;

public class Jugador {
	
	private String user;
	private String medallas;
	private Pokemon[] equipo;
	
	public Jugador(String user) {
		this.user = user;
	}

	public Pokemon[] getEquipo() {
		return equipo;
	}

	public void setEquipo(Pokemon[] equipo) {
		this.equipo = equipo;
	}

	public String getUser() {
		return user;
	}

	public String getMedallas() {
		return medallas;
	}

	public void setMedallas(String medallas) {
		this.medallas = medallas;
	}

	
	
	
	
	
	
	

}
