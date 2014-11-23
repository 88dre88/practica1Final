package tp.pr1;



import java.util.Scanner;

import tp.pr1.control.Controlador;
import tp.pr1.logica.Partida;

public class Main {

	public static void main(String[] args) {
		Scanner c = new Scanner(System.in);
		/*Instanciamos un objeto de la clase controlador
		 * para ejecutar la partida
		 */
		Controlador controlador = new Controlador(new Partida(),c);
		controlador.run();
		c.close();

	}

}
