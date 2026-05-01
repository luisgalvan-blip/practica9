/**
 * Representa un personaje dentro del juego. Puede moverse por el nivel
 * y ser destruido. Implementa {@link Destruible} y {@link ElementoDinamico}.
 */
public class Personaje implements Destruible, ElementoDinamico {

    /** Nombre del personaje. */
    private String nombre;

    /** Puntos de vida actuales del personaje. */
    private int vida;

    /** Posición horizontal en el mapa. */
    private int posicionX;

    /** Posición vertical en el mapa. */
    private int posicionY;

    /** Vida máxima permitida para cualquier personaje. */
    private static final int VIDA_MAX = 100;

    /**
     * Construye un personaje con los atributos indicados.
     *
     * @param nombre    Nombre del personaje.
     * @param vida      Puntos de vida iniciales.
     * @param posicionX Posición inicial en X.
     * @param posicionY Posición inicial en Y.
     */
    public Personaje(String nombre, int vida, int posicionX, int posicionY) {
        this.nombre    = nombre;
        this.vida      = vida;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    /**
     * Retorna el nombre del personaje.
     *
     * @return nombre del personaje.
     */
    public String getNombre() { return nombre; }

    /**
     * Retorna los puntos de vida actuales.
     *
     * @return vida actual.
     */
    public int getVida() { return vida; }

    /**
     * Retorna la posición horizontal del personaje.
     *
     * @return posición en X.
     */
    public int getPosicionX() { return posicionX; }

    /**
     * Retorna la posición vertical del personaje.
     *
     * @return posición en Y.
     */
    public int getPosicionY() { return posicionY; }

    /**
     * Establece la posición del personaje directamente.
     *
     * @param posicionX Nueva posición en X.
     * @param posicionY Nueva posición en Y.
     */
    public void setPosicion(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    /**
     * Aplica daño al personaje. Si el valor es negativo, cura al personaje.
     * La vida se limita entre 0 y VIDA_MAX (100).
     *
     * @param dano Puntos de daño a restar (negativo para curar).
     */
    public void recibirDano(int dano) {
        vida -= dano;
        if (vida > VIDA_MAX) vida = VIDA_MAX;
        if (vida < 0)        vida = 0;
        if (dano < 0)
            System.out.println(nombre + " se curó. Vida: " + vida);
        else
            System.out.println(nombre + " recibió daño. Vida: " + vida);
    }

    /**
     * Ejecuta la lógica de muerte del personaje.
     */
    @Override
    public void destruye() {
        System.out.println(nombre + " ha muerto.");
    }

    /**
     * Mueve al personaje en la dirección indicada.
     * Norte disminuye Y, sur lo aumenta (coordenadas JavaFX: Y crece hacia abajo).
     *
     * @param direccion Dirección: "norte", "sur", "este" u "oeste".
     * @param distancia Número de celdas a desplazar.
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
     * Retorna una representación textual del personaje.
     *
     * @return cadena con nombre, vida y posición.
     */
    @Override
    public String toString() {
        return "Personaje[" + nombre + " | vida:" + vida + " | pos:(" + posicionX + "," + posicionY + ")]";
    }
}
