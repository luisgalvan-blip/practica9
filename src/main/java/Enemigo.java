/**
 * Representa un enemigo dentro del juego. Extiende {@link Personaje}
 * añadiendo daño de ataque y comportamiento de patrulla automática.
 */
public class Enemigo extends Personaje {

    /** Daño que inflige el enemigo al atacar al jugador. */
    private int danoAtaque;

    /** Indica si el enemigo está activo (puede moverse y atacar). */
    private boolean activo;

    /** Contador de pasos para alternar dirección de patrulla. */
    private int pasos;

    /**
     * Construye un enemigo con todos sus atributos.
     *
     * @param nombre     Nombre del enemigo.
     * @param vida       Puntos de vida del enemigo.
     * @param posicionX  Posición inicial en X.
     * @param posicionY  Posición inicial en Y.
     * @param danoAtaque Daño que inflige al atacar.
     */
    public Enemigo(String nombre, int vida, int posicionX, int posicionY, int danoAtaque) {
        super(nombre, vida, posicionX, posicionY);
        this.danoAtaque = danoAtaque;
        this.activo     = true;
        this.pasos      = 0;
    }

    /**
     * Retorna el daño de ataque del enemigo.
     *
     * @return daño de ataque.
     */
    public int getDanoAtaque() { return danoAtaque; }

    /**
     * Indica si el enemigo está activo.
     *
     * @return true si el enemigo puede moverse y atacar.
     */
    public boolean isActivo() { return activo; }

    /**
     * Destruye al enemigo marcándolo como inactivo.
     */
    @Override
    public void destruye() {
        activo = false;
        System.out.println("Enemigo " + getNombre() + " fue eliminado!");
    }

    /**
     * Mueve al enemigo alternando entre este y oeste como patrulla,
     * sobreescribiendo el comportamiento base de {@link Personaje}.
     *
     * @param direccion Dirección del movimiento.
     * @param distancia Distancia a mover.
     */
    @Override
    public void mover(String direccion, int distancia) {
        if (!activo) return;
        pasos++;
        String dir = (pasos % 4 < 2) ? "este" : "oeste";
        super.mover(dir, distancia);
        System.out.println("[Enemigo] " + getNombre() + " patrulla hacia el " + dir);
    }

    /**
     * Retorna una representación textual del enemigo.
     *
     * @return cadena con nombre, vida y daño de ataque.
     */
    @Override
    public String toString() {
        return "Enemigo[" + getNombre() + " | vida:" + getVida() + " | daño:" + danoAtaque + "]";
    }
}
