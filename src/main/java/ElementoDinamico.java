/**
 * Interfaz que define el comportamiento de los elementos que pueden
 * moverse dentro del nivel del juego.
 */
public interface ElementoDinamico {

    /**
     * Mueve el elemento en la dirección indicada la distancia especificada.
     *
     * @param direccion Dirección del movimiento: "norte", "sur", "este" u "oeste".
     * @param distancia Número de celdas a desplazar.
     */
    void mover(String direccion, int distancia);
}
