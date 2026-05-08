package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

	private String user;
	private String medallas = "none";
	private List<Pokemon> equipo;

	public Jugador(String user) {
		this.user = user;
		this.equipo = new ArrayList<Pokemon>();
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

	public void agregarPokemon(Pokemon e, boolean interruptor) {
		this.equipo.add(e);
		if (!interruptor) {
			System.out.println("\n"+equipo.get(equipo.size() - 1).getNombre() + " Capturado con exito!!! XD");
		}
	}

	public void cambiarEquipo(int posicion1, int posicion2) {
		Pokemon aux = equipo.get(posicion1 - 1);
		equipo.set(posicion1 - 1, equipo.get(posicion2 - 1));
		equipo.set(posicion2 - 1, aux);
		for (int i = 0; i < equipo.size(); i++) {
			System.out.println(equipo.get(i).getNombre());
		}
	}

}
