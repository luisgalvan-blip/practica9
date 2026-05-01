/**
 * Representa un tesoro especial que extiende {@link Recompensa}.
 * Al ser recolectado, activa la condición de victoria del juego.
 * Añade el atributo rareza para distinguir tesoros especiales de recompensas comunes.
 */
public class Tesoro extends Recompensa {

    /** Nivel de rareza del tesoro (ej. "legendario", "épico", "raro"). */
    private String rareza;

    /** Indica si este tesoro activa la condición de victoria. */
    private boolean esFinal;

    /**
     * Construye un tesoro con todos sus atributos.
     *
     * @param nombre   Nombre del tesoro.
     * @param valor    Valor en puntos.
     * @param tipo     Tipo de recompensa.
     * @param rareza   Nivel de rareza del tesoro.
     * @param esFinal  Si es true, al recolectarlo se gana el juego.
     */
    public Tesoro(String nombre, int valor, String tipo, String rareza, boolean esFinal) {
        super(nombre, valor, tipo);
        this.rareza  = rareza;
        this.esFinal = esFinal;
    }

    /**
     * Retorna el nivel de rareza del tesoro.
     *
     * @return rareza del tesoro.
     */
    public String getRareza() {
        return rareza;
    }

    /**
     * Indica si este tesoro activa la condición de victoria.
     *
     * @return true si es el tesoro final.
     */
    public boolean isEsFinal() {
        return esFinal;
    }

    /**
     * Registra el tesoro con información adicional sobre su rareza.
     */
    @Override
    public void registrar() {
        System.out.println("¡Tesoro " + rareza + " registrado!: " + getNombre());
    }

    /**
     * Retorna una representación textual del tesoro incluyendo su rareza.
     *
     * @return cadena descriptiva del tesoro.
     */
    @Override
    public String toString() {
        return "Tesoro[" + getNombre() + " | valor:" + getValor() + " | rareza:" + rareza + (esFinal ? " | FINAL" : "") + "]";
    }
}
