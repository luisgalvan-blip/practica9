/**
 * Representa un arma que puede ser almacenada en el inventario del personaje.
 * Implementa la interfaz {@link Inventariable}.
 */
public class Arma implements Inventariable {

    /** Nombre del arma. */
    private String nombre;

    /** Puntos de daño que inflige el arma. */
    private int dano;

    /** Alcance del arma en unidades de distancia. */
    private double alcance;

    /**
     * Construye un arma con los atributos especificados.
     *
     * @param nombre  Nombre del arma.
     * @param dano    Puntos de daño que inflige.
     * @param alcance Alcance del arma.
     */
    public Arma(String nombre, int dano, double alcance) {
        this.nombre  = nombre;
        this.dano    = dano;
        this.alcance = alcance;
    }

    /**
     * Retorna el nombre del arma.
     *
     * @return nombre del arma.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna los puntos de daño del arma.
     *
     * @return daño del arma.
     */
    public int getDano() {
        return dano;
    }

    /**
     * Retorna el alcance del arma.
     *
     * @return alcance del arma.
     */
    public double getAlcance() {
        return alcance;
    }

    /**
     * Registra el arma imprimiendo su nombre en consola.
     */
    @Override
    public void registrar() {
        System.out.println("Arma registrada: " + nombre);
    }

    /**
     * Elimina el arma imprimiendo su nombre en consola.
     */
    @Override
    public void borrar() {
        System.out.println("Arma eliminada: " + nombre);
    }

    /**
     * Retorna una representación textual del arma.
     *
     * @return cadena con nombre y daño del arma.
     */
    @Override
    public String toString() {
        return "Arma[" + nombre + " | daño:" + dano + " | alcance:" + alcance + "]";
    }
}
