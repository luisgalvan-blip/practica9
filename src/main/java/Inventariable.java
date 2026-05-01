/**
 * Interfaz que define el comportamiento de los objetos que pueden
 * ser almacenados en el inventario del juego.
 */
public interface Inventariable {

    /**
     * Registra el item en el sistema (lo agrega al inventario).
     */
    void registrar();

    /**
     * Elimina el item del sistema (lo quita del inventario).
     */
    void borrar();
}