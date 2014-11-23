package tp.pr1.logica;

public class Registro {
	public static final int MAX = 10;
	
	// el indice de la pila 0 a N
	private int[] undoStack;
	private int numUndo;

	/**
	 * Inicializa los valores por defecto del registro
	 * que almacena los movimientos de columna en la partida
	 */
	public Registro() {
		// en este array se guardan los 10 ultimos movimientos
		undoStack = new int[MAX];
		resetRegistro();
	}
	/**
	 * Permite saber el numero de movimientos almacenados en el registro
	 * @return numUndo- posiciones ocupadas en la pila
	 */
	public int getNumUndo() {
		return numUndo;
	}
	
	/**
	 * Elimina un movimiento del registro de movimientos, decremenentando
	 * numUndo
	 */
	public void eliminarMovimiento(){
		numUndo --;
	}
	
	/**  Permite saber en que columna se ha realizado el ultimo movimiento
	 * @return columna que ha sido almacenada en la ultima ultima posicion 
	 * del registro
	 */
	public int getUltimoMovimiento(){
		if(numUndo == -1)
			return numUndo;
		else
			return undoStack[numUndo];
	}
	
	/**
	 * Reinicia el registro. Solo afecta al contador de 
	 * elementos del registro : numUndo
	 */
	public void resetRegistro(){
		
		numUndo = -1;
	}
	
	/**
	 * Desplaza el contenido del array de movimientos a la izquierda
	 */
	private void desplazarArray(){
	
		for(int i = 0;i < MAX- 1;i ++)
			undoStack[i] = undoStack[i+1];
	}
	
	/**Permite almacenar la columna donde se realizo el movimiento
	 * en el registro de movimientos
	 * @param columna donde se ha realizado el movimiento
	 */
	public void guardarMovimiento(int columna){
		/*
		 * Mientras no haya 10 movimientos registrados, se
		 *  incrementa numUndo y se guarda la columna en el array.
		 *  En caso de array completo, se desplaza su contenido
		 *  una posicion a la izquierda y se procede despues 
		 *  a registrar el movimiento.
		 */
		if(numUndo < MAX - 1){
			numUndo++;
		}else{
			desplazarArray();
		}
		
		//Guardamos el movimiento
		undoStack[numUndo]= columna;
	}
	
	/**
	 * Se utiliza para saber si el registro esta vacio
	 * @return true si registro vacio
	 */
	public boolean vacio(){
		return (numUndo == -1);
	}
}