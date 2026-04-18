
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
	public static void main(String[] args) {
		//Nombre: Eugenio Cortés Egaña; Rut: 22.405.687-7
		//Nombre: Matías Núñez González; Rut: 22.256.666-5
		
		Scanner entrada = new Scanner(System.in);
		menu(entrada);
		
		
		entrada.close();
		
		
	}
	
	//Menu principal 
	static void menu(Scanner entrada) {
		int opcion = 0;
		
		System.out.println("1) Continuar\n2) Nueva Partida\n3) Salir");
		
		try {
			do {
				opcion = Integer.parseInt(entrada.nextLine());
				
				switch(opcion) {
				case 1: 
					    break;
					   
				case 2: crearUsuario(entrada);
				        break;
				        
				case 3: break;
				}
			}while(opcion < 1 || opcion > 3);
			
		}catch(Exception e){
			System.out.println("ERROR. Valor Ingresado Invalido");
		}
		
	}
	
	//Crear usuario (deriva a menu de usuario)
	static void crearUsuario(Scanner entrada) {
		String apodo;
		System.out.print("Ingrese su Apodo: ");
		
		do {
			apodo = entrada.nextLine();
		}while(apodo.equals(""));
		
		Jugador e = new Jugador(apodo);
		
		try {
			
			String arch = "txts/Registros (1).txt";
			FileWriter escritor = new FileWriter(arch);
			escritor.write(apodo+";"+e.getMedallas());
			escritor.close();
			
		}catch (Exception e2) {
			System.out.println("ERROR. Escritura de archivo mal realizada.");
		}
		
		System.out.printf("\nBienvenido %s",apodo);
		System.out.println();
		
		menUsuario(entrada, e);
		
	}
	
	//Menu de usuario
	static void menUsuario(Scanner entrada, Jugador user) {
		int opcion = 0;
		
		while(true) {
			try {
				do {
					System.out.println("\n"+user.getUser()+", que deseas hacer?");
					System.out.println("\n1) Revisar equipo.\n2) Salir a capturar.\n3) Acceso al PC (cambiar Pokemon del equipo).\n4) Retar un gimnasio\n5) Desafío al Alto Mando.\n6) Curar Pokémon.\n7) Guardar.\n8) Guardar y Salir.");
					opcion = Integer.parseInt(entrada.nextLine());
					
					switch(opcion) {
					case 1: revisarEquipo(user);
						    break;
				    case 2: salirCapturar(entrada,user);
				            break;
					}
					
				}while(opcion < 1 || opcion > 8);
			}catch(Exception e) {
				System.out.println("ERROR. Valor Ingresado erroneo "+e.getMessage());
			}
		}
	}
	
	//Capturar pokemon
	static void salirCapturar(Scanner entrada, Jugador user) {
		String habitat = eligirHabitat(entrada);
		captura(entrada, habitat, user);
		
		try {
			String arch = "txts/Registros (1).txt";
			FileWriter escritor = new FileWriter(arch,true);
			Pokemon ultimo = user.getEquipo().get(user.getEquipo().size()-1);
			escritor.write("\n"+ultimo.getNombre()+";"+ultimo.getEstado());//Agrega la linea con salto en linea "\n" de modo que llamo a los atrubutos del pokemon.
			escritor.close();
			
			System.out.println("\n"+ultimo.getNombre()+" ha sido agregado a tu equipo! XD");
			
			
		}catch(Exception e) {
			System.out.println("ERROR. Registro fallido "+e.getMessage());
		}
		
	}
	
	//Derivada de Captura
	static String eligirHabitat(Scanner entrada) {
		int opcion;
		int c = 0;
		List<String> habitats = new ArrayList<String>();
		
		try {
			
			File file = new File("txts/Habitats.txt");
			Scanner lector = new Scanner(file);
			System.out.println("¿Donde deseas ir a explorar?\n");
			System.out.println("Zonas disponibles:\n");
			
			while(lector.hasNextLine()) {
				c++;
				String linea = lector.nextLine();
				System.out.println(c+")"+linea);
				habitats.add(linea);
			}
			
			System.out.println();
			lector.close();
			
			do {
				System.out.println("Ingrese Zona: ");
				opcion = Integer.parseInt(entrada.nextLine());
			}while(opcion < 1 || opcion > c);
			
			String eleccion = habitats.get(opcion-1);
			
			return eleccion;
			
		}catch(Exception e) {
			System.out.println("ERROR. No se pudo hayar el archivo "+e.getMessage());
			return null;
		}
		
	}
	
	//Captura Pokemom (ejercicio)
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
			File file = new File("txts/Pokedex.txt");
			Scanner lector = new Scanner(file);
			
			while(lector.hasNextLine()) {//Dentro de este while solo creo los pokemones de manera que creo instancias para hacer el randomizado.
				
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
				
				if(habitat.equalsIgnoreCase(partes[1])) {
					suma += e.getPorcAparicion();
					probabilidades.add(suma);
					pokemonParalela.add(e);	
				}	
			}
			
			lector.close();
			
			int indice = 0;
			for(int i = 1; i < probabilidades.size(); i++) {
				if(num < probabilidades.get(i) && num > probabilidades.get(i-1)) {
					indice = i;
					break;
				}else if(num < probabilidades.get(i) && num > 0){
					indice = 0;
					break;
				}
			}

			System.out.printf("\nOH!!! Ha aparecido un increible %s salvaje!!!", pokemonParalela.get(indice).getNombre());
			
		    System.out.println("\n¿Que deseas hacer?\n");
		    System.out.println("1) Capturar\n2) Huir");
		    System.out.println("Ingrese opcion: ");
			int opcion = Integer.parseInt(entrada.nextLine());
			
			switch(opcion) {
			
			case 1: user.agregarPokemon(pokemonParalela.get(indice));
					break;
					
			case 2: menUsuario(entrada, user);
			       
			}
			
		}catch(Exception e) {
			System.out.println("ERROR. No se encontro el archivo "+e.getMessage());
		}
		
	}
	
	static void revisarEquipo(Jugador user) {
		int c = 1;
		System.out.println();
		for(Pokemon i : user.getEquipo()) {
			System.out.println(c+"|"+i.getNombre()+"|"+i.getTipo()+"|Stats totales: "+i.getStats());
			c++;
		}
	}
	

}
