package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Dominio.Jugador;

public class Main {
	public static void main(String[] args) {
		//Nombre: Eugenio Cortés Egaña; Rut: 22.405.687-7
		//Nombre: Matías Nuñez Gonzales; Rut:
		
		Scanner entrada = new Scanner(System.in);
		menu(entrada);
		
		
		entrada.close();
		
		
	}
	
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
		
		System.out.printf("\nBienvenido %s\n",apodo);
		System.out.println();
		
		menUsuario(entrada, e);
		
	}
	
	static void menUsuario(Scanner entrada, Jugador user) {
		System.out.println(user.getUser()+", que deseas hacer?");
		int opcion = 0;
		
		try {
			do {
				System.out.println("\n1) Revisar equipo.\n2) Salir a capturar.\n3) Acceso al PC (cambiar Pokemon del equipo).\n4) Retar un gimnasio\n5) Desafío al Alto Mando.\n6) Curar Pokémon.\n7) Guardar.\n8) Guardar y Salir.");
				opcion = Integer.parseInt(entrada.nextLine());
				
				switch(opcion) {
				case 1: 
					   break;
			    case 2: salirCapturar(entrada);
				}
				
			}while(opcion < 1 || opcion > 8);
		}catch(Exception e) {
			System.out.println("ERROR. Valor Ingresado erroneo "+e.getMessage());
		}
	}
	
	static void salirCapturar(Scanner entrada) {
		String habitat = eligirHabitat(entrada);
		captura(entrada, habitat);
		
	}
	
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
	
	static void captura(Scanner entrada, String habitat) {
		Random r = new Random();
		double num = r.nextDouble();
		System.out.println(num);
		
	}

}
