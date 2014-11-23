package tp.pr1.logica;

/**
* Clase para representar la información de una partida. Se
* encarga de almacenar el estado del tablero, a quién le toca,
* si ya hay un ganador, etc., así como la lista de movimientos que 
*se han ido realizando para poder deshacerlos. La partida guarda al menos
*  los 10 últimos movimientos
*
*/
public class Partida {
	
	private static final int TAM_COL = 7,TAM_FILA = 6;
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada;
	private Ficha ganador;
	private Registro registro;
	
	
	/*  Al crear una partida inicializamos todos los atributos.En principio 
	 *  el tablero sera de 7x6  pero posteriormente sera aplicable a 
	 * cualquier tamaño.
	 */
	public Partida(){
		tablero = new Tablero(TAM_COL,TAM_FILA);
		terminada = false;
		turno = Ficha.BLANCA;
		ganador = null;
		registro = new Registro();
	}
	
	/**Método de acceso al tablero. Dependiendo de cómo se haga la 
	 * implementación del resto de clases (principalmente de la clase
	 *  Controlador), es posible que no se utilice para nada en la práctica. 
	 *  Sin embargo, es necesario implementarlo para poder ejecutar los test 
	 *  de unidad.
	 * 
	 * @return Estado del tablero actual
	 */
	public Tablero getTablero(){
		return tablero;
	}
	
	/**
	 * Devuelve el color del jugador al que le toca poner.
	 * @return Color del jugador, o Ficha.VACIA si la partida ha terminado
	 */
	public Ficha getTurno(){
		return turno;
	}
	
	
	/**
	 * Ejecuta el movimiento indicado.
	 * @param color- Color del jugador que pone.
	 * @param col - Columna donde colocar la ficha (1..7)
	 * @return true si se puede efectuar el movimiento. Es un error
	 *  intentar colocar una ficha del jugador que no tiene el turno, cuando
	 *   la partida está terminada, columna llena, etc, en cuyo caso devuelve false
	 */
	public boolean ejecutaMovimiento(Ficha color, int col){
		boolean mov = false;
	
		if(terminada || color != turno || col > tablero.getAncho() || col < 1){
			return mov;
		}
		
		/*Buscamos casillla libre(vacia) y colocamos la ficha en la columna
		 * En caso contrario,no permite el movimiento
		 */
		int fila = tablero.primeraCasillaLibre(col);
		if(fila > 0){

			tablero.setCasilla(col,fila,color);
			registro.guardarMovimiento(col);
			mov = true;
			
			/*
			 * Comprobamos si la partida no ha terminado
			 * para cambiar el turno
			 */
			if(!cuatroEnRaya() && !empate()){
				cambiarTurno();
			}
		}

		return mov;
	}
	
	/**
	 * Metodo que deshace el ultimo movimiento realizado.
	 * @return true si el movimiento se deshizo correctamente y false en 
	 * caso contrario
	 */
	public boolean undo() {
		boolean deshacer = false;
		
		if (!registro.vacio()) {
			
			int col = registro.getUltimoMovimiento();
			int fila = obtenerFilaUltimoMovimiento();
			Ficha color = Ficha.VACIA;
			
			tablero.setCasilla(col, fila, color);
			registro.eliminarMovimiento();
			deshacer = true;
			cambiarTurno();
		}
		return deshacer;
	}
	
	/**
	 * Reinicia la partida. El tablero se vacia y se borran todos los movimientos 
	 * guardados.
	 */
	public void reset(){
		tablero.reiniciar();
		// elimina todo movimiento guardado
		registro.resetRegistro();
		turno = Ficha.BLANCA;
	}
	
	/**
	 * Se encarga de comprobar los colores iguales de las casillas 
	 * segun la direccion proporcionada apartir de una casilla
	 *  origen(columna, fila)
	 * @param columna - De la casilla origen
	 * @param fila- De la casilla origen 
	 * @param dx- Direccion de la columna donde nos dirigimos
	 * @param dy- Direccion de la fila donde nos dirigimos
	 * @param color- De la ficha que buscamos
	 * @return- Devuelve el numero de fichas del mismo color juntas
	 *
	 */
	private int compruebaFilaColumnaDiagonal(int columna,int fila,int dx,int dy,Ficha color){
		//Ira aumentando cada vez que comprueba una casilla
		int contador = -1;
		
		//Compruebo si la columna y fila de origen estan en tablero
		if(!tablero.existeCasilla(columna, fila)){
			return contador = 0; //No ha encontrado ninguna ficha
		}
		/*
		 *Busco las casillas con el color de la casilla actual.
		 *Se suma a la fila y la columna las direcciones para acceder
		 *a las casillas correspondientes, siempre y cuando
		 *las casillas enten en el tablero
		 */
		do{
			columna = columna + dx;
			fila = fila + dy;
			contador++;

		}while(tablero.existeCasilla(columna, fila) && tablero.getCasilla(columna, fila) == color) ;



		return contador;
	}
	
	/**
	 * Comprueba la ultima casilla que ha tenido movimiento.
	 * Recorre la columna de arriba hacia abajo hasta encontrar
	 * la primera casilla no vacia.
	 * @return fila- no VACIA(ULTIMO MOVIMIENTO)
	 */
	private int obtenerFilaUltimoMovimiento(){
	
		int fila =1 ;
		int columna = registro.getUltimoMovimiento();
		
		if(columna == -1){
			return -1;
		}else{
		
			while(tablero.getCasilla(columna, fila) == Ficha.VACIA && fila <= tablero.getAlto())
				fila ++;
			
			return fila;
		}
	}
	
	/**
	 * Método para saber si la partida ha conluído ya o no
	 * @return terminada para saber el estado del tablero
	 */
	public boolean isTerminada() {
		return terminada;
		
	}
	
	/**
	 * Nos dice si hay cuatro colores en linea.Se comprueba en diagonal,
	 * horizontal y vertical
	 * @return true si han encontrado cuatro en linea. false en caso contrario
	 */
	private boolean cuatroEnRaya(){
		

		boolean conectaOk = false;
		
		//Obtenego fila y columna del ultimo movimiento
		int col = registro.getUltimoMovimiento();
		int fila = obtenerFilaUltimoMovimiento();
		
		if( fila == -1 || col == -1){
			return conectaOk;
		}
		
		//Se realizan 4 comprobaciones(diagona iz/der, horizonta y vertical
		int comprobaciones = 1;
		//Primera direccion de comprobacion(diagonal izquierda inferior)
		int dx = -1, dy=-1;
		int contador = 1;
		
		//Si no se encuentra 4 en lina y no se supera las 4 comprobaciones
		while( !conectaOk && comprobaciones <=4){
			

			//Direcciones invertidas a las actuales
			int dxx , dyy;
			
			/*Se comprueba segun direccion actual y luego se invierte la direccion actual
			 * para obtener el numero de fichas iguales en ambos sentidos
			 * Si hay 4 o mas, no hacemos mas comprobaciones
			 */
			contador += compruebaFilaColumnaDiagonal(col, fila, dx, dy, turno);
			dxx = dx *(-1);
			dyy = dy *(-1);
			contador += compruebaFilaColumnaDiagonal(col, fila, dxx, dyy, turno);
			if(contador >= 4){
				
				ganador = turno;
				terminada = true;	
				return conectaOk = true;
			}
			
			/*En las dos siguiente comprobaciones, la direccion
			 * de dx seguira siendo -1. Solo va cambiando dy, se le va sumando 1
			 * en las siguientes comprobaciones
			 * -1 1, -1 0, -1 1.
			 */
			dy +=1;
			
			/*Aumento el numero de comprobaciones la ultima comprobacion(cuarta)
			 * es hacia abajo de la ficha origen.
			 * La direccion cambia de patron.
			 */
			comprobaciones++;
			if(comprobaciones == 4){
				dx= 0;
				dy = 1;
			}
			if(comprobaciones < 5)
				//Se reinicia en cada comprobacion(diagonal,vertical u horizontal)
				 contador = 1;
			
		}
		
		if(contador == 4)
			terminada = true;
		
		
		return terminada;
		
	}
	
	/**Comprueba la fila superior del tablero en busca de tablero lleno.
	 * Si hay alguna casilla vacia en dicha fila, no se habra alcanzado 
	 * tablas en la partida.
	 * 
	 * @return Si no hay ninguna casilla vacia, ya no se puede meter mas fichas
	 * por lo tanto el juego esta empate(terminada = true). En caso contrario false.
	 */
	private boolean empate() {
		
		int fila = 1; 
		boolean empate = false;
		/*Comprobamos la fila superior del tablero a ver si esta 
		 * completamente llena,en cuyo caso la partida habra
		 * acabado en tablas
		 */
		for(int columna = 1; columna <= tablero.getAncho(); columna ++){
			if(tablero.getCasilla(columna, fila) == Ficha.VACIA){
				return empate;
			}else if(columna == tablero.getAncho()){
				terminada = true;
				empate = true;
			}
		}
		
		return empate;
	}
	
	/**
	 * Cambia el turno de la partida segun el color del actual turno
	 */
	private void cambiarTurno() {
		
		if (turno == Ficha.BLANCA)
			turno = Ficha.NEGRA;
		else
			turno = Ficha.BLANCA;
	}
	
	/**
	 * Devuelve el color del ganador. Sólo válido si la
	 *  partida ya ha terminado (isTerminada() == true).
	 * @return Color del ganador. Si la partida terminó 
	 * en tablas, Ficha.VACIA. Si la partida no ha 
	 * terminado aún, el resultado es indeterminado
	 */
	public Ficha getGanador() {
		
		if (isTerminada() && !empate()) {
			
			return ganador;
			
		} else if (isTerminada() && empate()) {
			
			return Ficha.VACIA;
			
		} else
			//Color indeterminado(no hay ganador)
			return ganador;
	}
}
