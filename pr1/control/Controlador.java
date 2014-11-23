package tp.pr1.control;

import java.util.Scanner;

import tp.pr1.logica.Ficha;
import tp.pr1.logica.Partida;

/**
 * Clase que controla la ejecución de la partida, pidiendo 
 * al usuario qué quiere ir haciendo, hasta que la partida termina.
 *
 */

public class Controlador {
	
	private Partida partida;
	private Scanner in;

	
	public Controlador(Partida p,Scanner in){
		partida = p;
		this.in  = in;
		
	}
	
	/**
	 * Metodo que realiza la simulacion del juego. Mientras el usuario no 
	 * seleccione la opcion de salida, el metodo seguira mostrando el menu
	 *  y accediendo a las diferentes opciones.
	 */
	public void run(){
		
		boolean salir = false;
		
		do{
			salir = menu();
		}while(!salir);

	}
	
	/**
	 * Realiza la gestion de poner una ficha del color del turno
	 * en el tablero de la partida
	 * @param turno- Color de ficha que le toca poner
	 * @return true si se ha conseguido poner la ficha. En caso 
	 * contrario false.
	 */
	private boolean poner(Ficha turno){
		
		System.out.print("Introduce la columna: ");
		int columna = in.nextInt();
		in.nextLine();//limpieza del buffer de entrada
		boolean aux = partida.ejecutaMovimiento(turno, columna),salir = false;
		
		if(!aux)
			System.err.println("Movimiento incorrecto");
		else {
			
			
			if(partida.isTerminada()){
				
				if((partida.getGanador() != Ficha.VACIA)){
					
					System.out.println(partida.getTablero().toString());
					System.out.println("Ganan las " + partida.getGanador().toString().toLowerCase() + "s");
					salir = true;
					
				}else if(partida.getGanador() == Ficha.VACIA ){
					
					System.out.print(partida.getTablero().toString());
					System.out.println("Partida terminada en tablas.");
					salir = true;
					
				}
			}
		}
		
		return salir;
	}
	
	/**
	 * Nos permite seleccionar las distintas opciones disponibles
	 * en el juego conecta4: poner, deshacer, reiniciar y salir.
	 * @return false si se elige salir del menu para poder salir de la aplicacion.
	 * true en caso contrario
	 */
	private boolean menu(){
		
		boolean salir = false;
	
		Ficha turno = partida.getTurno();
		System.out.println(partida.getTablero().toString());
		System.out.println("Juegan " + turno.toString().toLowerCase()+ "s");
		System.out.print("Qué quieres hacer? ");
		//conviertimos todo lo escrito por el usuario a minusculas para no hacer distincion
		String opcion = in.nextLine().toLowerCase();
		
		switch(opcion){
			case "poner":{
					salir = poner(turno);
				break;
			}
			case "deshacer":{
				boolean aux = partida.undo();
				if(!aux)
					System.err.println("Imposible deshacer.");
				break;
			}
			case "reiniciar":{
				partida.reset();
				System.out.println("Partida reiniciada.");
				break;
			}
			case "salir":{
				salir = true;
				break;
			}
			default:{
				System.err.println("No te entiendo.");
				break;
			}
			
		}
		
		return salir;
	}


	
}