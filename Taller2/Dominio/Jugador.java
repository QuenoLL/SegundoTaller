package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
	
	private String user;
	private String medallas;
	private List<Pokemon> equipo = new ArrayList<Pokemon>();
	
	public Jugador(String user) {
		this.user = user;
	}
	
	public List<Pokemon> getEquipo() {
		return equipo;
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
	
	public void agregarPokemon(Pokemon e) {
		this.equipo.add(e);
		System.out.println(equipo.get(equipo.size()-1).getNombre()+" Capturado con exito!!! XD");
	}
	

	
	
	
	
	
	
	

}
