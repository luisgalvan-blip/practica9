/**
 * Representa un punto de control dentro del nivel.
 * Al ser alcanzado por el personaje, se activa y registra el progreso.
 */
public class CheckPoint {

    /** Nombre identificador del checkpoint. */
    private String nombre;

    /** Posición horizontal en el mapa. */
    private int posicionX;

    /** Posición vertical en el mapa. */
    private int posicionY;

    /** Indica si el checkpoint ha sido activado. */
    private boolean activado;

    /**
     * Construye un checkpoint en la posición indicada, inicialmente inactivo.
     *
     * @param nombre    Nombre del checkpoint.
     * @param posicionX Posición en X.
     * @param posicionY Posición en Y.
     */
    public CheckPoint(String nombre, int posicionX, int posicionY) {
        this.nombre    = nombre;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.activado  = false;
    }

    /**
     * Retorna el nombre del checkpoint.
     *
     * @return nombre del checkpoint.
     */
    public String getNombre()   { return nombre; }

    /**
     * Retorna la posición horizontal del checkpoint.
     *
     * @return posición en X.
     */
    public int getPosicionX()   { return posicionX; }

    /**
     * Retorna la posición vertical del checkpoint.
     *
     * @return posición en Y.
     */
    public int getPosicionY()   { return posicionY; }

    /**
     * Indica si el checkpoint ya fue activado.
     *
     * @return true si fue activado.
     */
    public boolean isActivado() { return activado; }

    /**
     * Activa el checkpoint si aún no lo estaba.
     */
    public void activar() {
        activado = true;
        System.out.println(nombre + " activado.");
    }

    /**
     * Retorna una representación textual del checkpoint.
     *
     * @return cadena con nombre, posición y estado.
     */
    @Override
    public String toString() {
        return "CheckPoint[" + nombre + " | pos:(" + posicionX + "," + posicionY + ") | " + (activado ? "activo" : "inactivo") + "]";
    }
}
