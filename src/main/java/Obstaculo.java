/**
 * Representa un obstáculo estático en el nivel que puede ser destruido.
 * Implementa la interfaz {@link Destruible}.
 */
public class Obstaculo implements Destruible {

    /** Nombre del obstáculo. */
    private String nombre;

    /** Daño que inflige al personaje al ser destruido. */
    private int dano;

    /** Posición horizontal en el mapa. */
    private int posicionX;

    /** Posición vertical en el mapa. */
    private int posicionY;

    /** Indica si el obstáculo ya fue destruido. */
    private boolean destruido;

    /**
     * Construye un obstáculo con los atributos indicados.
     *
     * @param nombre    Nombre del obstáculo.
     * @param dano      Daño que inflige al ser destruido.
     * @param posicionX Posición inicial en X.
     * @param posicionY Posición inicial en Y.
     */
    public Obstaculo(String nombre, int dano, int posicionX, int posicionY) {
        this.nombre    = nombre;
        this.dano      = dano;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.destruido = false;
    }

    /**
     * Retorna el nombre del obstáculo.
     *
     * @return nombre del obstáculo.
     */
    public String getNombre()   { return nombre; }

    /**
     * Retorna el daño que inflige el obstáculo.
     *
     * @return daño del obstáculo.
     */
    public int getDano()        { return dano; }

    /**
     * Retorna la posición horizontal del obstáculo.
     *
     * @return posición en X.
     */
    public int getPosicionX()   { return posicionX; }

    /**
     * Retorna la posición vertical del obstáculo.
     *
     * @return posición en Y.
     */
    public int getPosicionY()   { return posicionY; }

    /**
     * Indica si el obstáculo fue destruido.
     *
     * @return true si ya fue destruido.
     */
    public boolean isDestruido() { return destruido; }

    /**
     * Destruye el obstáculo marcándolo como destruido.
     */
    @Override
    public void destruye() {
        destruido = true;
        System.out.println(nombre + " fue destruido.");
    }

    /**
     * Retorna una representación textual del obstáculo.
     *
     * @return cadena con nombre y posición.
     */
    @Override
    public String toString() {
        return "Obstaculo[" + nombre + " | pos:(" + posicionX + "," + posicionY + ")]";
    }
}
