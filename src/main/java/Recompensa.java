/**
 * Representa una recompensa que el personaje puede recolectar y almacenar
 * en su inventario. Implementa la interfaz {@link Inventariable}.
 */
public class Recompensa implements Inventariable {

    /** Nombre de la recompensa. */
    private String nombre;

    /** Valor en puntos de la recompensa. */
    private int valor;

    /** Tipo de recompensa (ej. "oro", "joya", "utileria"). */
    private String tipo;

    /**
     * Construye una recompensa con los atributos especificados.
     *
     * @param nombre Nombre de la recompensa.
     * @param valor  Valor en puntos.
     * @param tipo   Tipo de recompensa.
     */
    public Recompensa(String nombre, int valor, String tipo) {
        this.nombre = nombre;
        this.valor  = valor;
        this.tipo   = tipo;
    }

    /**
     * Retorna el nombre de la recompensa.
     *
     * @return nombre de la recompensa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el valor en puntos de la recompensa.
     *
     * @return valor de la recompensa.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Retorna el tipo de la recompensa.
     *
     * @return tipo de la recompensa.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Registra la recompensa imprimiendo su nombre en consola.
     */
    @Override
    public void registrar() {
        System.out.println("Recompensa registrada: " + nombre);
    }

    /**
     * Elimina la recompensa imprimiendo su nombre en consola.
     */
    @Override
    public void borrar() {
        System.out.println("Recompensa eliminada: " + nombre);
    }

    /**
     * Retorna una representación textual de la recompensa.
     *
     * @return cadena con nombre, valor y tipo.
     */
    @Override
    public String toString() {
        return "Recompensa[" + nombre + " | valor:" + valor + " | tipo:" + tipo + "]";
    }
}
