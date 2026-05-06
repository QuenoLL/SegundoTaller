
package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Dominio.Jugador;
import Dominio.Pokemon;

public class Main {

	static List<Pokemon> listaPokemonesPc = new ArrayList<Pokemon>();

	public static void main(String[] args) {
		// Nombre: Eugenio Cortés Egaña; Rut: 22.405.687-7
		// Nombre: Matías Núñez González; Rut: 22.256.666-5

		Scanner entrada = new Scanner(System.in);
		leerPokedex();
		menu(entrada);

		entrada.close();

	}

	// Menu principal

	static void leerPokedex() {
		try {
			File file = new File("txts/Pokedex.txt");
			Scanner lector = new Scanner(file);

			while (lector.hasNextLine()) {// Dentro de este while solo creo los pokemones de manera que creo instancias
											// para hacer el randomizado.

				String linea = lector.nextLine();
				String[] partes = linea.split(";");

				String nombre = partes[0];
				double intervalo = Double.parseDouble(partes[2]);

				int vida = Integer.parseInt(partes[3]);
				int ataque = Integer.parseInt(partes[4]);
				int defensa = Integer.parseInt(partes[5]);
				int ataqueEspecial = Integer.parseInt(partes[6]);
				int defensaEspecial = Integer.parseInt(partes[7]);
				int velocidad = Integer.parseInt(partes[8]);

				int stats = vida + ataque + defensa + ataqueEspecial + defensaEspecial + velocidad;
				String habitats = partes[1];
				String tipo = partes[9];
				Pokemon e = new Pokemon(nombre, habitats, intervalo, stats, tipo, "Vivo");
				listaPokemonesPc.add(e);
			}
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}

	}

	static void menu(Scanner entrada) {
		int opcion = 0;

		System.out.println("1) Continuar\n2) Nueva Partida\n3) Salir");

		try {
			do {
				opcion = Integer.parseInt(entrada.nextLine());

				switch (opcion) {
				case 1:
					continuarMenu(entrada);
					break;

				case 2:
					crearUsuario(entrada);
					break;

				case 3:
					break;
				}
			} while (opcion < 1 || opcion > 3);

		} catch (Exception e) {
			System.out.println("ERROR. Valor Ingresado Invalido");
		}

	}

	// Continuar carga datos y deriva a menú de usuario

	static void continuarMenu(Scanner entrada) {
		try {
			File file = new File("txts/Registros (1).txt");
			Scanner lector = new Scanner(file);
			
			
			String primeralinea = lector.nextLine();
			String[] partes = primeralinea.split(";");
			
			String nombre = partes[0];
			String medallas = partes[1];
			Jugador jugadorCargado = new Jugador(nombre);
			jugadorCargado.setMedallas(medallas);
			
			
			
			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				String[] partes2 = linea.split(";");
				String nombrePokemon = partes2[0];
				String estado = partes2[1];
				for (Pokemon pokemon : listaPokemonesPc) {
					if (pokemon.getNombre().equalsIgnoreCase(nombrePokemon)) {
						pokemon.setEstado(estado);
						
						jugadorCargado.agregarPokemon(pokemon, true);
					}
				}
			}
			
			lector.close();
			menUsuario(entrada, jugadorCargado);
			
			
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}

	// Crear usuario (deriva a menu de usuario)
	static void crearUsuario(Scanner entrada) {
		String apodo;
		System.out.print("Ingrese su Apodo: ");

		do {
			apodo = entrada.nextLine();
		} while (apodo.equals(""));

		Jugador e = new Jugador(apodo);

		try {

			String arch = "txts/Registros (1).txt";
			FileWriter escritor = new FileWriter(arch);
			escritor.write(apodo + ";" + e.getMedallas());
			escritor.close();

		} catch (Exception e2) {
			System.out.println("ERROR. Escritura de archivo mal realizada.");
		}

		System.out.printf("\nBienvenido %s", apodo);
		System.out.println();

		menUsuario(entrada, e);

	}

	// Menu de usuario
	static void menUsuario(Scanner entrada, Jugador user) {
		int opcion = 0;

		while (true) {
			try {
				do {
					System.out.println("\n" + user.getUser() + ", que deseas hacer?");
					System.out.println(
							"\n1) Revisar equipo.\n2) Salir a capturar.\n3) Acceso al PC (cambiar Pokemon del equipo).\n4) Retar un gimnasio\n5) Desafío al Alto Mando.\n6) Curar Pokémon.\n7) Guardar.\n8) Guardar y Salir.");
					opcion = Integer.parseInt(entrada.nextLine());

					switch (opcion) {
					case 1:
						revisarEquipo(user);
						break;
					case 2:
						salirCapturar(entrada, user);
						break;
					case 3:
						revisarPc(entrada, user);
						break;
					case 7: 
						guardarPartida();
					}

				} while (opcion < 1 || opcion > 8);
			} catch (Exception e) {
				System.out.println("ERROR. Valor Ingresado erroneo " + e.getMessage());
			}
		}
	}
	//Guardar Partida
	static void guardarPartida() {
		
	}
	
	// Capturar pokemon
	static void salirCapturar(Scanner entrada, Jugador user) {
		String habitat = eligirHabitat(entrada);
		captura(entrada, habitat, user);

		try {
			String arch = "txts/Registros (1).txt";
			FileWriter escritor = new FileWriter(arch, true);
			Pokemon ultimo = user.getEquipo().get(user.getEquipo().size() - 1);
			escritor.write("\n" + ultimo.getNombre() + ";" + ultimo.getEstado());// Agrega la linea con salto en linea
																					// "\n" de modo que llamo a los
																					// atrubutos del pokemon.
			escritor.close();

			System.out.println("\n" + ultimo.getNombre() + " ha sido agregado a tu equipo! XD");

		} catch (Exception e) {
			System.out.println("ERROR. Registro fallido " + e.getMessage());
		}

	}

	// Derivada de Captura
	static String eligirHabitat(Scanner entrada) {
		int opcion;
		int c = 0;
		List<String> habitats = new ArrayList<String>();

		try {

			File file = new File("txts/Habitats.txt");
			Scanner lector = new Scanner(file);
			System.out.println("¿Donde deseas ir a explorar?\n");
			System.out.println("Zonas disponibles:\n");

			while (lector.hasNextLine()) {
				c++;
				String linea = lector.nextLine();
				System.out.println(c + ")" + linea);
				habitats.add(linea);
			}

			System.out.println();
			lector.close();

			do {
				System.out.println("Ingrese Zona: ");
				opcion = Integer.parseInt(entrada.nextLine());
			} while (opcion < 1 || opcion > c);

			String eleccion = habitats.get(opcion - 1);

			return eleccion;

		} catch (Exception e) {
			System.out.println("ERROR. No se pudo hayar el archivo " + e.getMessage());
			return null;
		}

	}

	// Captura Pokemom (ejercicio)
	static void captura(Scanner entrada, String habitat, Jugador user) {
		Random r = new Random();
		double minimo = 0;
		double maximo = 1.0;

		double num = minimo + (maximo - minimo) * r.nextDouble();
		System.out.println(num);

		List<Double> probabilidades = new ArrayList<>();
		List<Pokemon> pokemonParalela = new ArrayList<Pokemon>();

		double suma = 0;

		try {
			for (Pokemon pokemon:listaPokemonesPc) {
				
				if (habitat.equalsIgnoreCase(pokemon.getHabitat())) {
					suma += pokemon.getPorcAparicion();
					probabilidades.add(suma);
					pokemonParalela.add(pokemon);
				}
			}


			int indice = 0;
			for (int i = 1; i < probabilidades.size(); i++) {
				if (num < probabilidades.get(i) && num > probabilidades.get(i - 1)) {
					indice = i;
					break;
				} else if (num < probabilidades.get(i) && num > 0) {
					indice = 0;
					break;
				}
			}

			System.out.printf("\nOH!!! Ha aparecido un increible %s salvaje!!!",
					pokemonParalela.get(indice).getNombre());

			System.out.println("\n¿Que deseas hacer?\n");
			System.out.println("1) Capturar\n2) Huir");
			System.out.println("Ingrese opcion: ");
			int opcion = Integer.parseInt(entrada.nextLine());

			switch (opcion) {
			case 1:
				if (user.getEquipo().size() < 7) {
					user.agregarPokemon(pokemonParalela.get(indice), false);
				}
				break;

			case 2:
				menUsuario(entrada, user);

			}

		} catch (

		Exception e) {
			System.out.println("ERROR. No se encontro el archivo " + e.getMessage());
		}

	}

	// Printeo del equipo del jugador, los primeros 6 de la pc...
	static void revisarEquipo(Jugador user) {
		int c = 1;
		System.out.println();
		for (Pokemon i : user.getEquipo()) {
			System.out.println(c + "|" + i.getNombre() + "|" + i.getTipo() + "|Stats totales: " + i.getStats());
			c++;
		}
	}

	static void revisarPc(Scanner entrada, Jugador user) {

		try {
			File file = new File("txts/Registros (1).txt");
			Scanner lector = new Scanner(file);
			int c = 1;

			String linea = lector.nextLine();

			while (lector.hasNextLine()) {
				linea = lector.nextLine();
				String[] partes = linea.split(";");
				String nombre = partes[0];

				System.out.println(c + ".- " + nombre);

				c++;
			}
			lector.close();

			System.out.println("1) Cambiar Pokemon\n2) Salir");
			System.out.print("> ");
			int opcion = Integer.parseInt(entrada.nextLine());

			switch (opcion) {
			case 1:
				cambiarPokemon(entrada, user, c);
				break;
			case 2:
				break;
			}

		} catch (Exception e) {
			System.out.println("ERROR. " + e.getMessage());
		}
	}

	static void cambiarPokemon(Scanner entrada, Jugador user, int cantidadP) {
		int posicion1, posicion2;

		try {
			do {
				System.out.println("Ingrese su posicion 1 a intercambiar:");
				System.out.print("> ");
				posicion1 = Integer.parseInt(entrada.nextLine());
			} while (posicion1 < 1 || posicion1 > cantidadP);

			do {
				System.out.println("Ingrese su posicion 2 a intercambiar:");
				System.out.print("> ");
				posicion2 = Integer.parseInt(entrada.nextLine());
			} while (posicion2 < 1 || posicion2 > cantidadP);

			// Lineas a cambiar...
			String linea1 = conseguirLinea(posicion1);
			System.out.println(linea1);
			String linea2 = conseguirLinea(posicion2);
			System.out.println(linea2);
			user.cambiarEquipo(posicion1, posicion2);
			// Nuevamente leemos el archivo
			File file = new File("txts/Registros (1).txt");
			Scanner lector = new Scanner(file);
			String linea = lector.nextLine();
			String lineaSaltada = linea;
			List<String> escrituraArchivo = new ArrayList<String>();
			while (lector.hasNextLine()) {
				linea = lector.nextLine();

				if (linea.equalsIgnoreCase(linea1)) {
					escrituraArchivo.add(linea2);
					System.out.println("CAMBIÉ");
				} else if (linea.equalsIgnoreCase(linea2)) {
					escrituraArchivo.add(linea1);
					System.out.println("CAMBIÉ");
				} else {
					escrituraArchivo.add(linea);
					System.out.println("ESTOY CAMBIANDO");
				}
			}
			lector.close();

			// Sobreescribimos el archivo
			String arch = "txts/Registros (1).txt";
			FileWriter escritor = new FileWriter(arch);
			escritor.write(lineaSaltada);
			for (int i = 0; i < escrituraArchivo.size(); i++) {
				escritor.write("\n" + escrituraArchivo.get(i));
			}
			escritor.close();
			System.out.println("ARCHIVO CARGADO EXITOSAMENTE!!!!!!!!!!!!!");

		} catch (Exception e) {
			System.out.println("ERROR. " + e.getMessage());
		}
	}

	// Obtener las lineas a intercambiar...
	static String conseguirLinea(int n) {
		Scanner lector = null;
		try {
			File file = new File("txts/Registros (1).txt");
			lector = new Scanner(file);
			String linea = null;

			for (int i = 0; i <= n; i++) {
				linea = lector.nextLine();
			}
			return linea;
		} catch (Exception e) {
			System.out.println("ERROR. " + e.getMessage());
		} finally {
			if (lector != null)
				lector.close();
		}

		return null;

	}

}

//Habrá que cambiar los registros??