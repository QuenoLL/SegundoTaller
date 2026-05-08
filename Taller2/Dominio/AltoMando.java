package Dominio;

import java.util.List;

public class AltoMando {
	
	private int numAltoMando;
	private String nombre;
	private List<Pokemon> listaPokemon;
	
	public AltoMando(int numAltoMando, String nombre, List<Pokemon> listaPokemon) {
		this.numAltoMando = numAltoMando;
		this.nombre = nombre;
		this.listaPokemon = listaPokemon;
	}

	public String getNombre() {
		return nombre;
	}

	public List<Pokemon> getListaPokemon() {
		return listaPokemon;
	}

	public int getNumAltoMando() {
		return numAltoMando;
	}
	
	
	
	
	
	

}
