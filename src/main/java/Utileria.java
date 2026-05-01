/**
 * Representa un objeto de utilería que se mueve dinámicamente por el nivel.
 * Implementa {@link ElementoDinamico} y puede ser recogido por el jugador.
 */
public class Utileria implements ElementoDinamico {

    /** Nombre del objeto de utilería. */
    private String nombre;

    /** Descripción del efecto o uso del objeto. */
    private String descripcion;

    /** Posición horizontal en el mapa. */
    private int posicionX;

    /** Posición vertical en el mapa. */
    private int posicionY;

    /**
     * Construye un objeto de utilería con todos sus atributos.
     *
     * @param nombre      Nombre del objeto.
     * @param descripcion Descripción del objeto.
     * @param posicionX   Posición inicial en X.
     * @param posicionY   Posición inicial en Y.
     */
    public Utileria(String nombre, String descripcion, int posicionX, int posicionY) {
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.posicionX   = posicionX;
        this.posicionY   = posicionY;
    }

    /**
     * Retorna el nombre del objeto.
     *
     * @return nombre de la utilería.
     */
    public String getNombre()      { return nombre; }

    /**
     * Retorna la descripción del objeto.
     *
     * @return descripción de la utilería.
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Retorna la posición horizontal del objeto.
     *
     * @return posición en X.
     */
    public int getPosicionX()      { return posicionX; }

    /**
     * Retorna la posición vertical del objeto.
     *
     * @return posición en Y.
     */
    public int getPosicionY()      { return posicionY; }

    /**
     * Ejecuta el efecto del objeto de utilería.
     * Las subclases pueden sobreescribir este método para efectos específicos.
     */
    public void usar() {
        System.out.println("Usando " + nombre + ": " + descripcion);
    }

    /**
     * Mueve la utilería en la dirección indicada.
     * Norte disminuye Y, sur lo aumenta (coordenadas JavaFX).
     *
     * @param direccion Dirección del movimiento.
     * @param distancia Distancia a mover.
     */
    @Override
    public void mover(String direccion, int distancia) {
        switch (direccion.toLowerCase()) {
            case "norte": posicionY -= distancia; break;
            case "sur":   posicionY += distancia; break;
            case "este":  posicionX += distancia; break;
            case "oeste": posicionX -= distancia; break;
        }
        System.out.println(nombre + " se movió a (" + posicionX + "," + posicionY + ")");
    }

    /**
     * Retorna una representación textual de la utilería.
     *
     * @return cadena con nombre y posición.
     */
    @Override
    public String toString() {
        return "Utileria[" + nombre + " | " + descripcion + " | pos:(" + posicionX + "," + posicionY + ")]";
    }
}
