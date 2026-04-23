package Dominio;

import java.util.List;

public class AltoMando {
	
	private String nombre;
	private List<Pokemon> listaPokemon;
	
	public AltoMando(String nombre, List<Pokemon> listaPokemon) {
		this.nombre = nombre;
		this.listaPokemon = listaPokemon;
	}

	public String getNombre() {
		return nombre;
	}

	public List<Pokemon> getListaPokemon() {
		return listaPokemon;
	}
	
	
	
	

}
