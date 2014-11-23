package tp.pr1.logica;

/**
 * Almacena la informacion del tablero de juego.
 */

public class Tablero {

	private Ficha[][] tablero;
	private int ancho;
	private int alto;

	// Construye un tablero vacio de las dimensiones 
	public Tablero(int tx, int ty) {
		if(tx <= 0 || ty <= 0){
			ancho = 1;
			alto = 1;
		}else{
			ancho = tx;
			alto = ty;
		}
		/*
		 * Instanciamos un array bidimensional de Fichas
		 * e inicializamos todas sus casillas
		 */
		tablero = new Ficha[ancho][alto];
		reiniciar();//
	}

	/**
	 * Metodo para obtener el alto del tablero
	 * @return Numero de filas del tablero
	 */
	public int getAlto() {
		return alto;
	}

	/**
	 * Metodo para obtener el alto del tablero
	 * @return Numero de columnas del tablero
	 */
	public int getAncho() {
		return ancho;
	}
	
	/**
	 * Comprueba si existe una casilla.
	 * @return true si existe la casilla seleccionada y false en caso contrario.
	 * 
	 */
	public boolean existeCasilla(int x, int y) {
		/*
		 * Si elegimos una coordenada que este
		 * fuera del rango de nuestro tablero,
		 * devolvera false. 
		 */
		return !((x > ancho|| x <= 0) || (y > alto || y <= 0));
	}

	/**
	 * Metodo para acceder a la informacion de una casilla
	 * @param x-Numero de columnas(1...ancho)
	 * @param y-Numero de fila(1...alto)
	 * @return Infomacion de la casilla. Si la casilla no valida, devuelve
	 * una Ficha VACIA
	 */
	public Ficha getCasilla(int x, int y) {
		
		Ficha color = Ficha.VACIA;
		
		//Compruebo que la casilla es validas
		if (existeCasilla(x,y)) {
			// hay que restar uno ya que los indices de un array van de 0 a n-1
			color = tablero[x-1][y-1];
		}
		return color;  
	}
	
	/**
	 * Permite rellenar el contenido de una casilla.
	 * Tambien puede utilizarse para quitar una ficha.
	 * @param x- Numero de colomunas(1...ancho)
	 * @param y-Numero de filas(1...alto)
	 * @param color-Color de casilla.Si es VACIA, celda sin ficha
	 */
	public void setCasilla(int x, int y, Ficha color) {
	
		//Compruebo si la casilla es valida
		if (existeCasilla(x,y)) {
			tablero[x-1][y-1] = color;
		} 
	}
	
	/**
	 * Comprueba si una casilla esta vacia.
	 * @return true si esta vacia y false en caso contrario
	 */
	private boolean casillaVacia(int x,int y){
		return (existeCasilla(x,y) && tablero[x-1][y-1] == Ficha.VACIA);
	}

	/**
	 * Inicializa las casillas del tablero
	 * con fichas vacias
	 */
	public void reiniciar(){
		
		for(int i = 0;i < ancho;i ++){
			for(int j = 0;j < alto;j ++){
				tablero[i][j] = Ficha.VACIA;
			}
		}
	}
	
	
	/**
	 * Busca y devuelve la primera casilla vacia en una columna.Recorre las filas del tablero
	 * de abajo hacia arriba .
	 * @param x- columna desde donde se hace la comprobacion
	 * @return y- posicion de la fila VACIA usando(casilla libre)
	 */
	public int primeraCasillaLibre(int x){
		
		int y = alto;
		
		while(!casillaVacia(x, y) && y >= 0)
			y --;
		
		return y;
	}
	
	
	
	
	
	/**
	 * Muestra una representacion del estado del tablero. 
	 * 
	 */
	public String toString() {
		
		String resultado = "";
		
		//Pintamos las filas desde arriba(alto)
		for(int i = 0;i < alto;i ++){
			resultado += "|";
			for(int j = 0;j < ancho;j ++){
				Ficha ficha = tablero[j][i];
				resultado += tipoDeFicha(ficha);
			}
			resultado += "|\n";
		}
		
		/*Pintamos la parte de abajo.
		 * Empezando con la penultima fila y posteriormente
		 * con la ultima fila que contiene los indices
		 * de las columnas.
		 */	
		resultado += "+";
		for(int j = 0;j < ancho;j ++){
			resultado += '-';
		}
		resultado += "+\n";
		resultado += " ";
		for(int j = 0;j < ancho;j ++){
			resultado += j + 1;
		}
		
		resultado += "\n";
			
		return resultado;
	}
	
	/**
	 * Devuelve el caracter a pintar en la casilla.
	 * Las casillas vacias se representan con un espacio en blanco, las 
	 * fichas negras con una O y las fichas blancas con una X;
	 * @param ficha- De la casilla del tablero
	 * @return- Caracter a pintar segun la ficha de la casilla
	 */
	private char tipoDeFicha(Ficha ficha){
		char resultado;
		switch(ficha){
			case VACIA:
				resultado = ' ';
				break;
			case BLANCA:
				resultado = 'O';
				break;
			case NEGRA:
				resultado = 'X';
				break;
			default:
				resultado = ' ';
				break;
		}
		return resultado;
		
	}
}