
//Abrimos el paquete
package app;

//Importamos funcion
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Dominio.AltoMando;
import Dominio.Gimnasio;
import Dominio.Jugador;
import Dominio.Pokemon;
import Dominio.TablaTipos;

//Nombramos la clase
public class Main {
	// Nombre: Eugenio Cortés Egaña; Rut: 22.405.687-7; Carrera: Ingeniería Civil en Computación e Informática
	// Nombre: Matías Núñez González; Rut: 22.256.666-5; Carrera: Ingeniería Civil en Computación e Informática
	
	// Declaracion de listas a utilizar
	static List<Pokemon> listaPokemonesPc = new ArrayList<Pokemon>();
	static List<Gimnasio> listaGimnasios = new ArrayList<Gimnasio>();
	static List<AltoMando> listaAltoMando = new ArrayList<AltoMando>();

	// Inicializamos el Main
	public static void main(String[] args) {
		// Nombre: Eugenio Cortés Egaña; Rut: 22.405.687-7
		// Nombre: Matías Núñez González; Rut: 22.256.666-5

		Scanner entrada = new Scanner(System.in);
		leerPokedex();
		leerGimnasios();
		leerAltoMando();
		menu(entrada);

		entrada.close();

	}

	// Los siguientes 3 métodos son para leer nuestros archivos .txt y llenar las
	// listas
	static void leerAltoMando() {
		try {
			File file = new File("txts/Alto Mando.txt");
			Scanner lector = new Scanner(file);

			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				String[] partes = linea.split(";");

				int numAltoMando = Integer.parseInt(partes[0]);
				String nombre = partes[1];
				List<Pokemon> listaPokemon = new ArrayList<Pokemon>();

				for (int i = 2; i < 8; i++) {
					for (Pokemon pokemon : listaPokemonesPc) {
						if (pokemon.getNombre().equalsIgnoreCase(partes[i])) {
							listaPokemon.add(pokemon);

						}
					}
				}

				AltoMando a = new AltoMando(numAltoMando, nombre, listaPokemon);
				listaAltoMando.add(a);
			}
			lector.close();

		} catch (Exception e) {
			System.out.println("ERROR. " + e.getMessage());
		}
	}

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
			lector.close();
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}

	}

	static void leerGimnasios() {
		try {
			File file = new File("txts/Gimnasios.txt");
			Scanner lector = new Scanner(file);

			while (lector.hasNextLine()) {

				String linea = lector.nextLine();
				String[] partes = linea.split(";");

				int numGimnasio = Integer.parseInt(partes[0]);
				String lider = partes[1];
				String estado = partes[2];
				int cantidadPokemones = Integer.parseInt(partes[3]);

				List<Pokemon> pokemones = new ArrayList<Pokemon>();

				for (int i = 0; i < cantidadPokemones; i++) {
					for (Pokemon pokemon : listaPokemonesPc) {

						if (pokemon.getNombre().equalsIgnoreCase(partes[i + 4])) {
							pokemones.add(pokemon);
						}
					}

				}
				Gimnasio gym = new Gimnasio(numGimnasio, lider, estado, cantidadPokemones, pokemones);
				listaGimnasios.add(gym);

			}
			lector.close();

		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}

	// Menu principal
	static void menu(Scanner entrada) {
		int opcion = 0;

		System.out.println("1) Continuar\n2) Nueva Partida\n3) Salir");

		do {
			try {
				System.out.print("> ");
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

			} catch (Exception e) {
				System.out.println("ERROR. Valor Ingresado Invalido");
			}
		} while (opcion < 1 || opcion > 3);

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

			if (!medallas.equalsIgnoreCase("none")) {
				for (Gimnasio gym : listaGimnasios) {
					if (gym.getLider().equalsIgnoreCase(medallas)) {
						gym.setEstado(true);
						break;
					} else
						gym.setEstado(true);
				}
			}

			lector.close();
			menUsuario(entrada, jugadorCargado);

		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}
	}

	// Crear usuario (deriva a menu de usuario)
	static void crearUsuario(Scanner entrada) {
		String apodo;

		do {
			System.out.print("Ingrese su Apodo: ");
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
		boolean indicador = false;
		while (!indicador) {
			try {
				do {
					System.out.println("\n" + user.getUser() + ", que deseas hacer?");
					System.out.println(
							"\n1) Revisar equipo.\n2) Salir a capturar.\n3) Acceso al PC (cambiar Pokemon del equipo).\n4) Retar un gimnasio\n5) Desafío al Alto Mando.\n6) Curar Pokémon.\n7) Guardar.\n8) Guardar y Salir.");
					System.out.print("> ");
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

					case 4:
						retarGimnasio(entrada, user);
						break;
					case 5:
						altoMando(user, entrada);
						break;
					case 6:
						curarPokemon(user);
						break;
					case 7:
						guardarPartida(user);
						System.out.println("\nPARTIDA GUARDADA EXITOSAMENTE!!!!!!!!!!!!!");
						break;
					case 8:
						guardarPartida(user);
						System.out.println("\nSALISTE Y GUARDASTE EXITOSAMENTE LA PARTIDA");
						return;
					}

				} while (opcion < 1 || opcion > 8);
			} catch (Exception e) {
				System.out.println("ERROR. " + e.getMessage());
			}
		}
	}

	// Condiciones para verificar si podemos enfrentar o no
	static boolean justificacionEnfrentamiento(int opcion, String medalla) {
		if (listaGimnasios.get(opcion - 1).isEstado()) {
			System.out.println("\nYa derrotaste este gimnasio");
			return false;
		}
		if (medalla.equalsIgnoreCase("none") && opcion == 1) {
			System.out.println("\nDesafiando a " + listaGimnasios.get(opcion - 1).getLider());
			return true;
		}
		if (medalla.equalsIgnoreCase(listaGimnasios.get(opcion - 2).getLider())) {
			System.out.println("\nDesafiando a " + listaGimnasios.get(opcion - 1).getLider());
			return true;
		} else if (listaGimnasios.get(opcion - 1).isEstado() == true) {
			System.out.println("\nYa derrotaste este gimnasio");
		} else {
			System.out.println("\nCalmado Entrenador!!! No puedes retar a " + listaGimnasios.get(opcion - 1).getLider()
					+ " sin haber derrotado a los lideres anteriores!!");
		}

		return false;
	}

	// Conseguimos indices de Pokemones nuestros y enemigos para enfrentarlos
	static void dueloGimnasio(Jugador user, Gimnasio gym) {
		List<Integer> indicesPokemonesVivos = new ArrayList<Integer>();
		List<Integer> indicesPokemonesGym = new ArrayList<Integer>();
		boolean indicador = true;

		for (int i = 0; i < gym.getListaPokemon().size(); i++) {
			indicesPokemonesGym.add(i);
		}

		Scanner entrada = new Scanner(System.in);

		for (int i = 0; i < user.getEquipo().size() && i < 6; i++) {
			if (user.getEquipo().get(i).getEstado().equalsIgnoreCase("vivo")) {
				indicesPokemonesVivos.add(i);
			}
		}

		if (indicesPokemonesVivos.size() == 0) {
			System.out.println("Tu equipo entero esta debilitado, ve a curarlos ya!!!.");
		} else {

			// Comienzo del match ciclico hasta que haya un ganador...
			while (indicador) {
				boolean derrota = false;
				int accion = 0;

				System.out.println("\nQue deseas hacer?\n");
				System.out.println("1) Atacar");
				System.out.println("2) Cambiar de pokemon");
				System.out.println("3) Rendirse");

				do {
					try {
						System.out.println("Ingrese opción: ");
						accion = Integer.parseInt(entrada.nextLine());
					} catch (Exception e) {
						System.out.println("ERROR " + e.getMessage());
					}
				} while (accion < 1 || accion > 3);

				switch (accion) {
				case 1:
					int indiceActualGym = indicesPokemonesGym.getFirst();

					String namePokemon = user.getEquipo().get(indicesPokemonesVivos.getFirst()).getNombre();
					String gymPokemon = gym.getListaPokemon().get(indiceActualGym).getNombre();

					int statsMyPokemon = user.getEquipo().get(indicesPokemonesVivos.getFirst()).getStats();
					int statsGymPokemon = gym.getListaPokemon().get(indiceActualGym).getStats();

					System.out.println(namePokemon + " -> " + statsMyPokemon);
					System.out.println(gymPokemon + " -> " + statsGymPokemon);
					System.out.println();

					double efectividad = TablaTipos.getEfectividad(
							user.getEquipo().get(indicesPokemonesVivos.getFirst()).getTipo(),
							gym.getListaPokemon().get(indiceActualGym).getTipo());

					if (efectividad == 0.5) {
						statsMyPokemon *= efectividad;
						System.out.println(namePokemon + " no es efectivo contra " + gymPokemon);
						System.out.println("Nuevo puntaje: ");
						System.out.println(namePokemon + " -> " + statsMyPokemon);
						System.out.println(gymPokemon + " -> " + statsGymPokemon);
						if (statsMyPokemon < statsGymPokemon) {
							derrota = true;
						}
					}
					if (efectividad == 1.0) {
						statsMyPokemon *= efectividad;
						System.out.println(namePokemon + " no tiene efectos adicionales a " + gymPokemon);
						System.out.println("Nuevo puntaje: ");
						System.out.println(namePokemon + " -> " + statsMyPokemon);
						System.out.println(gymPokemon + " -> " + statsGymPokemon);
						if (statsMyPokemon < statsGymPokemon) {
							derrota = true;
						}
					}
					if (efectividad == 2.0) {
						statsMyPokemon *= efectividad;
						System.out.println(namePokemon + "es muy efectivo contra " + gymPokemon);
						System.out.println("Nuevo puntaje: ");
						System.out.println(namePokemon + " -> " + statsMyPokemon);
						System.out.println(gymPokemon + " -> " + statsGymPokemon);
						if (statsMyPokemon < statsGymPokemon) {
							derrota = true;
						}
					}
					System.out.println();

					if (derrota) {
						System.out.println("Ha ganado " + gymPokemon + "! " + namePokemon + " ha sido derrotado");
						user.getEquipo().get(indicesPokemonesVivos.getFirst()).setEstado("Debilitado");
						indicesPokemonesVivos.removeFirst();

					} else {
						System.out.println("Ha ganado " + namePokemon + "! " + gymPokemon + " ha sido derrotado");
						indicesPokemonesGym.removeFirst();

					}

					if (indicesPokemonesVivos.size() != 0 && indicesPokemonesGym.size() == 0) {
						gym.setEstado(true);
						indicador = false;
						System.out.println("Has derrotado a " + gym.getLider());
						user.setMedallas(gym.getLider());
						agregarMedalla(user, gym, true);

					} else if (indicesPokemonesVivos.size() == 0 && indicesPokemonesGym.size() != 0) {
						System.out.println("Te ha derrotado " + gym.getLider());
						indicador = false;
						agregarMedalla(user, gym, false);
					}
					guardarPartida(user);
					break;

				case 2:
					int opcion;

					System.out.println("----POKEMONES VIVOS DE TU EQUIPO----");
					for (int i = 0; i < indicesPokemonesVivos.size(); i++) {
						int indice = indicesPokemonesVivos.get(i);
						System.out.println((i + 1) + ") " + user.getEquipo().get(indice).getNombre());
					}

					do {
						System.out.print("Ingresa el numero del pokemon que quieres cambiar: ");
						opcion = Integer.parseInt(entrada.nextLine());
					} while (opcion < 0 || opcion > indicesPokemonesVivos.size());

					int aux = indicesPokemonesVivos.get(0);
					indicesPokemonesVivos.set(0, indicesPokemonesVivos.get(opcion - 1));
					indicesPokemonesVivos.set(opcion - 1, aux);

					break;
				case 3:
					System.out.println("Sales hecho una bala!!!");
					indicador = false;
					guardarPartida(user);
					break;

				}

			}
		}
	}

	// Sobreescribir medallas, está incompleto, requiere continuación y revisión
	static void agregarMedalla(Jugador user, Gimnasio gym, boolean indicador) {
		try {

			// Lectura del archivo y llenado del array
			File file = new File("txts/Registros (1).txt");
			Scanner lector = new Scanner(file);
			String lineaSaltada = lector.nextLine();

			List<String> escrituraArchivo = new ArrayList<String>();

			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				escrituraArchivo.add(linea);
			}

			lector.close();

			// Sobrescritura de archivo;
			String arch = "txts/Registros (1).txt";
			FileWriter escritor = new FileWriter(arch);
			String[] partes = lineaSaltada.split(";");
			String nombre = partes[0];
			String nuevaLinea = null;

			if (indicador) {
				nuevaLinea = nombre + ";" + gym.getLider();
			} else
				nuevaLinea = nombre + ";" + user.getMedallas();

			escritor.write(nuevaLinea);

			for (int i = 0; i < escrituraArchivo.size(); i++) {
				escritor.write("\n" + user.getEquipo().get(i).getNombre() + ";" + user.getEquipo().get(i).getEstado());
			}
			escritor.close();

		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		}
	}

	// Formato de reto a gimnasio
	static void retarGimnasio(Scanner entrada, Jugador user) {
		try {
			if (user.getEquipo().size() == 0) {
				System.out.println("\nDebes de capturar Pokemones antes de enfrentarte en duelos!.");
				return;
			}

			int contador = 1;
			int opcion = 0;
			String estado = null;

			System.out.println();

			// Printeo Gimnasios en relacion al contador...
			for (Gimnasio gimnasio : listaGimnasios) {
				estado = "Sin derrotar";
				if (gimnasio.isEstado() == true) {
					estado = "Derrotado";
				}

				System.out.println(contador + ") " + gimnasio.getLider() + " - Estado: " + estado);
				contador++;

			}
			System.out.println("9) Volver al menu.");

			do {
				try {
					System.out.print("Ingrese opcion: ");
					opcion = Integer.parseInt(entrada.nextLine());
				} catch (Exception e) {
					System.out.println("ERROR " + e.getMessage());
				}

			} while (opcion < 1 || opcion > 9);

			// Condicion enfrentamiento o salida...
			if (opcion == 9) {
				return;
			} else {
				if (justificacionEnfrentamiento(opcion, user.getMedallas()) == true) {
					dueloGimnasio(user, listaGimnasios.get(opcion - 1));
				} else {
					return;
				}
			}

		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}
	}

	// Guardar Partida
	static void guardarPartida(Jugador user) {
		try {
			// Lectura del archivo y llenado del array

			File file = new File("txts/Registros (1).txt");
			Scanner lector = new Scanner(file);
			String lineaSaltada = lector.nextLine();

			List<String> escrituraArchivo = new ArrayList<String>();
			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				escrituraArchivo.add(linea);
			}

			String arch = "txts/Registros (1).txt";
			FileWriter escritor = new FileWriter(arch);
			escritor.write(lineaSaltada);
			for (int i = 0; i < escrituraArchivo.size(); i++) {
				escritor.write("\n" + user.getEquipo().get(i).getNombre() + ";" + user.getEquipo().get(i).getEstado());
			}
			escritor.close();

			lector.close();
		} catch (Exception e) {
			System.out.println("ERROR. " + e.getMessage());
		}
	}

	// Curar Pokemones.
	static void curarPokemon(Jugador user) {
		for (Pokemon pokemon : user.getEquipo()) {
			pokemon.setEstado("Vivo");
		}
		System.out.println("\n Has curado exitosamente a todos tus pokemones "); // Podriamos indicar a cuales se curó

		guardarPartida(user);
	}

	// Capturar pokemon
	static void salirCapturar(Scanner entrada, Jugador user) {
		String habitat = elegirHabitat(entrada);
		boolean indicador = captura(entrada, habitat, user);

		if (indicador) {

			try {
				String arch = "txts/Registros (1).txt";
				FileWriter escritor = new FileWriter(arch, true);
				Pokemon ultimo = user.getEquipo().get(user.getEquipo().size() - 1);
				escritor.write("\n" + ultimo.getNombre() + ";" + ultimo.getEstado());// Agrega la linea con salto en
																						// linea
																						// "\n" de modo que llamo a los
																						// atrubutos del pokemon.
				escritor.close();

				System.out.println("\n" + ultimo.getNombre() + " ha sido agregado a tu equipo! XD");

			} catch (Exception e) {
				System.out.println("ERROR. " + e.getMessage());
			}

		}

	}

	// Derivada de Captura
	static String elegirHabitat(Scanner entrada) {
		int opcion = 0;
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
				try {
					System.out.print("Ingrese Zona: ");
					opcion = Integer.parseInt(entrada.nextLine());

				} catch (Exception e) {
					System.out.println("ERROR" + e.getMessage());
				}
			} while (opcion < 1 || opcion > c);

			String eleccion = habitats.get(opcion - 1);

			return eleccion;

		} catch (Exception e) {
			System.out.println("ERROR. " + e.getMessage());
			return null;
		}

	}

	// Captura Pokemom (ejercicio)
	static boolean captura(Scanner entrada, String habitat, Jugador user) {
		Random r = new Random();
		double minimo = 0;
		double maximo = 1.0;

		double num = minimo + (maximo - minimo) * r.nextDouble();

		List<Double> probabilidades = new ArrayList<>();
		List<Pokemon> pokemonParalela = new ArrayList<Pokemon>();

		double suma = 0;
		int opcion = 0;
		;

		for (Pokemon pokemon : listaPokemonesPc) {

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

		System.out.printf("\nOH!!! Ha aparecido un increible %s salvaje!!!", pokemonParalela.get(indice).getNombre());

		System.out.println("\n¿Que deseas hacer?\n");
		System.out.println("1) Capturar\n2) Huir");

		// Control de error entrada de archivo.
		do {
			try {

				System.out.println("Ingrese opcion: ");
				opcion = Integer.parseInt(entrada.nextLine());

			} catch (Exception e) {
				System.out.println("ERROR. " + e.getMessage());
			}
		} while (opcion < 1 || opcion > 2);

		switch (opcion) {// Arreglar manera que si esta en el equipo no se agregue, lo cual pasa pero en
							// el return de la funcion mayor se sobrescribe en el registro.
		case 1:

			if (user.getEquipo().size() > 0) {
				boolean yaLoTiene = false;
				for (Pokemon pokemon : user.getEquipo()) {
					if (pokemonParalela.get(indice).getNombre().equalsIgnoreCase(pokemon.getNombre())) {
						yaLoTiene = true;
						break;
					}
				}

				if (yaLoTiene) {
					System.out.println("Ya tienes a este pokemon!!!.");
					return false;
				} else {
					user.agregarPokemon(pokemonParalela.get(indice), false);
				}
			} else
				user.agregarPokemon(pokemonParalela.get(indice), false);

			break;

		case 2:
			return false;

		}

		return true;

	}

	// Printeo del equipo del jugador, los primeros 6 de la pc...
	static void revisarEquipo(Jugador user) {
		if (user.getEquipo().size() == 0) {
			System.out.println("\nAun no capturas ningun Pokemon...");
			return;
		}

		int c = 1;
		System.out.println();
		for (Pokemon i : user.getEquipo()) {
			if (c == 7) {
				return;
			} else {
				System.out.println(c + "|" + i.getNombre() + "|" + i.getTipo() + "|Stats totales: " + i.getStats()
						+ "| Estado: " + i.getEstado());
			}
			c++;
		}
	}

	// Revisamos los pokemones totales que tenemos en nuestro Registros.txt
	static void revisarPc(Scanner entrada, Jugador user) {
		int opcion = 0;
		if (user.getEquipo().size() == 0) {
			System.out.println("\nAun no tienes pokemones en el inventario. SAL A CAPTURAR!");
			return;
		}
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

			do {
				try {
					System.out.println("1) Cambiar Pokemon\n2) Salir");
					System.out.print("> ");
					opcion = Integer.parseInt(entrada.nextLine());

				} catch (Exception e) {
					System.out.println("ERROR " + e.getMessage());
				}
			} while (opcion < 1 || opcion > 2);

			switch (opcion) {
			case 1:
				cambiarPokemon(entrada, user, user.getEquipo().size());
				break;
			case 2:
				break;
			}

		} catch (Exception e) {
			System.out.println("ERROR. " + e.getMessage());
		}
	}

	// Cambiamos Pokemones que tenemos guardados en nuestro pc
	static void cambiarPokemon(Scanner entrada, Jugador user, int cantidadP) {
		int posicion1 = 0, posicion2 = 0;
		if (user.getEquipo().size() < 2) {
			System.out.println("Para cambiar pokemones necesitas más de 1");
			return;
		}

		try {
			
			do {
				try {
					System.out.println("Ingrese su posicion 1 a intercambiar:");
					System.out.print("> ");
					posicion1 = Integer.parseInt(entrada.nextLine());
				}catch (Exception e) {
					System.out.println("ERROR " + e.getMessage());
				}
			} while (posicion1 < 1 || posicion1 > cantidadP);

			do {
				try {
					System.out.println("Ingrese su posicion 2 a intercambiar:");
					System.out.print("> ");
					posicion2 = Integer.parseInt(entrada.nextLine());
				}catch (Exception e) {
					System.out.println("ERROR " + e.getMessage());
				}
			} while (posicion2 < 1 || posicion2 > cantidadP);

			// Lineas a cambiar...

			String linea1 = conseguirLinea(posicion1);

			String linea2 = conseguirLinea(posicion2);

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

				} else if (linea.equalsIgnoreCase(linea2)) {
					escrituraArchivo.add(linea1);

				} else {
					escrituraArchivo.add(linea);
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
			System.out.println("\n ARCHIVO CARGADO EXITOSAMENTE!!!!!!!!!!!!!");

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

	// Control de acceso a combate de AltoMando
	static void altoMando(Jugador user, Scanner entrada) {
		if (!user.getMedallas().equalsIgnoreCase("Maxi")) {
			System.out.println("\nAun no derrotas a todos los gimnasios, que haces aca???");
			return;
		} else {
			dueloAltoMando(user, entrada);
		}
	}

	// Este es todo el desarrollo de el duelo de los Altos Mandos
	private static void dueloAltoMando(Jugador user, Scanner entrada) {
		boolean acabado = false;

		for (AltoMando altoMando : listaAltoMando) {
			boolean indicador = true;
			List<Integer> indicesPokemonesVivos = new ArrayList<Integer>();
			List<Integer> indicesPokemonesAlto = new ArrayList<Integer>();

			for (int i = 0; i < user.getEquipo().size() && i < 6; i++) {
				if (user.getEquipo().get(i).getEstado().equalsIgnoreCase("vivo")) {
					indicesPokemonesVivos.add(i);
				}
			}

			for (int i = 0; i < altoMando.getListaPokemon().size(); i++) {
				indicesPokemonesAlto.add(i);
			}

			if (indicesPokemonesVivos.size() == 0) {
				System.out.println("Tu equipo entero esta debilitado, ve a curarlos ya!!!.");
				return;
			} else {
				System.out.println("\nDesafiando a: " + altoMando.getNombre());

				while (indicador) {
					boolean derrota = false;

					int accion = 0;

					System.out.println("\nQue deseas hacer?\n");
					System.out.println("1) Atacar");
					System.out.println("2) Cambiar de pokemon");
					System.out.println("3) Rendirse");

					do {
						try {
							System.out.println("Ingrese opción: ");
							accion = Integer.parseInt(entrada.nextLine());
						} catch (Exception e) {
							System.out.println("ERROR " + e.getMessage());
						}
					} while (accion < 1 || accion > 3);

					switch (accion) {
					case 1:
						int indiceActualAlto = indicesPokemonesAlto.getFirst();

						String namePokemon = user.getEquipo().get(indicesPokemonesVivos.getFirst()).getNombre();
						String altoPokemon = altoMando.getListaPokemon().get(indiceActualAlto).getNombre();

						int statsMyPokemon = user.getEquipo().get(indicesPokemonesVivos.getFirst()).getStats();
						int statsAltoPokemon = altoMando.getListaPokemon().get(indiceActualAlto).getStats();

						System.out.println(namePokemon + " -> " + statsMyPokemon);
						System.out.println(altoPokemon + " -> " + statsAltoPokemon);
						System.out.println();

						double efectividad = TablaTipos.getEfectividad(
								user.getEquipo().get(indicesPokemonesVivos.getFirst()).getTipo(),
								altoMando.getListaPokemon().get(indiceActualAlto).getTipo());

						if (efectividad == 0.5) {
							statsMyPokemon *= efectividad;
							System.out.println(namePokemon + " no es efectivo contra " + altoPokemon);
							System.out.println("Nuevo puntaje: ");
							System.out.println(namePokemon + " -> " + statsMyPokemon);
							System.out.println(altoPokemon + " -> " + statsAltoPokemon);
							if (statsMyPokemon < statsAltoPokemon) {
								derrota = true;
							}
						}

						if (efectividad == 1.0) {
							statsMyPokemon *= efectividad;
							System.out.println(namePokemon + " no tiene efectos adicionales a " + altoPokemon);
							System.out.println("Nuevo puntaje: ");
							System.out.println(namePokemon + " -> " + statsMyPokemon);
							System.out.println(altoPokemon + " -> " + statsAltoPokemon);
							if (statsMyPokemon < statsAltoPokemon) {
								derrota = true;
							}
						}

						if (efectividad == 2.0) {
							statsMyPokemon *= efectividad;
							System.out.println(namePokemon + "es muy efectivo contra " + altoPokemon);
							System.out.println("Nuevo puntaje: ");
							System.out.println(namePokemon + " -> " + statsMyPokemon);
							System.out.println(altoPokemon + " -> " + statsAltoPokemon);
							if (statsMyPokemon < statsAltoPokemon) {
								derrota = true;
							}
						}

						System.out.println();

						if (derrota) {
							System.out.println("Ha ganado " + altoPokemon + "! " + namePokemon + " ha sido derrotado");
							user.getEquipo().get(indicesPokemonesVivos.getFirst()).setEstado("Debilitado");
							indicesPokemonesVivos.removeFirst();

						} else {
							System.out.println("Ha ganado " + namePokemon + "! " + altoPokemon + " ha sido derrotado");
							indicesPokemonesAlto.removeFirst();
						}

						System.out.println();

						if (indicesPokemonesVivos.size() != 0 && indicesPokemonesAlto.size() == 0) {
							indicador = false;
							System.out.println("Has derrotado a " + altoMando.getNombre());

						} else if (indicesPokemonesVivos.size() == 0 && indicesPokemonesAlto.size() != 0) {
							System.out.println("Te ha derrotado " + altoMando.getNombre());
							indicador = false;
							acabado = true;
						}
						guardarPartida(user);
						break;

					case 2:
						int opcion;

						System.out.println("----POKEMONES VIVOS DE TU EQUIPO----");
						for (int i = 0; i < indicesPokemonesVivos.size(); i++) {
							int indice = indicesPokemonesVivos.get(i);
							System.out.println((i + 1) + ") " + user.getEquipo().get(indice).getNombre());
						}

						do {
							System.out.print("Ingresa el numero del pokemon que quieres cambiar: ");
							opcion = Integer.parseInt(entrada.nextLine());
						} while (opcion < 0 || opcion > indicesPokemonesVivos.size());

						int aux = indicesPokemonesVivos.get(0);
						indicesPokemonesVivos.set(0, indicesPokemonesVivos.get(opcion - 1));
						indicesPokemonesVivos.set(opcion - 1, aux);

						break;

					case 3:
						System.out.println("Sales hecho una bala!!!");
						indicador = false;
						guardarPartida(user);
						return;

					}

				}

			}
			if (acabado) {
				System.out.println("\nNo te quedan pokemones vivos has sido derrotado y pisado...");
				return;
			}

		}
		if (!acabado) {
			System.out.println("Felicidades eres el jugador Experto EASY, ganaste la copa coca cola.");
		}
	}

}

//Nico ponenos el 7