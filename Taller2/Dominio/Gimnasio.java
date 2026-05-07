package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Gimnasio {
	private int numGimnasio;
	private String lider;
	private boolean estado;
	private int cantidadPokemones;
	private List<Pokemon> listaPokemon = new ArrayList<Pokemon>();
	
	
	public Gimnasio(int numGimnasio, String lider, String estado, int cantidadPokemones, List<Pokemon> listaPokemon) {
		this.numGimnasio = numGimnasio;
		this.lider = lider;
		
		if (estado.equalsIgnoreCase("Sin derrotar")) {
			this.estado = false;
		} 
		else this.estado = true;
		
		this.cantidadPokemones = cantidadPokemones;
		this.listaPokemon = listaPokemon;
	}


	public int getNumGimnasio() {
		return numGimnasio;
	}


	public String getLider() {
		return lider;
	}


	public boolean isEstado() {
		return estado;
	}


	public int getCantidadPokemones() {
		return cantidadPokemones;
	}


	public List<Pokemon> getListaPokemon() {
		return listaPokemon;
		
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	
	
	
	

}
