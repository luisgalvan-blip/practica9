/**
 * Representa al jugador controlado por el usuario. Extiende {@link Personaje}
 * añadiendo experiencia, nivel de jugador y puntuación acumulada.
 */
public class Jugador extends Personaje {

    /** Puntos de experiencia acumulados por el jugador. */
    private int experiencia;

    /** Nivel actual del jugador (sube al acumular experiencia). */
    private int nivelJugador;

    /** Puntuación total acumulada al recolectar recompensas. */
    private int puntuacion;

    /**
     * Construye un jugador en la posición indicada con valores iniciales.
     *
     * @param nombre    Nombre del jugador.
     * @param posicionX Posición inicial en X.
     * @param posicionY Posición inicial en Y.
     */
    public Jugador(String nombre, int posicionX, int posicionY) {
        super(nombre, 100, posicionX, posicionY);
        this.experiencia  = 0;
        this.nivelJugador = 1;
        this.puntuacion   = 0;
    }

    /**
     * Retorna los puntos de experiencia del jugador.
     *
     * @return experiencia acumulada.
     */
    public int getExperiencia() { return experiencia; }

    /**
     * Retorna el nivel actual del jugador.
     *
     * @return nivel del jugador.
     */
    public int getNivelJugador() { return nivelJugador; }

    /**
     * Retorna la puntuación acumulada del jugador.
     *
     * @return puntuación total.
     */
    public int getPuntuacion() { return puntuacion; }

    /**
     * Agrega puntos de experiencia y sube de nivel cada 100 puntos.
     *
     * @param puntos Puntos de experiencia a agregar.
     */
    public void ganarExperiencia(int puntos) {
        experiencia += puntos;
        if (experiencia >= nivelJugador * 100) {
            nivelJugador++;
            System.out.println(getNombre() + " subió al nivel " + nivelJugador + "!");
        }
    }

    /**
     * Agrega puntos a la puntuación total del jugador.
     *
     * @param puntos Puntos a sumar.
     */
    public void agregarPuntuacion(int puntos) {
        puntuacion += puntos;
    }

    /**
     * Sobreescribe mover() para registrar el movimiento como acción del jugador.
     *
     * @param direccion Dirección del movimiento.
     * @param distancia Distancia a mover.
     */
    @Override
    public void mover(String direccion, int distancia) {
        super.mover(direccion, distancia);
        System.out.println("[Jugador] " + getNombre() + " avanza hacia el " + direccion);
    }

    /**
     * Retorna una representación textual del jugador incluyendo nivel y puntuación.
     *
     * @return cadena descriptiva del jugador.
     */
    @Override
    public String toString() {
        return "Jugador[" + getNombre() + " | vida:" + getVida()
                + " | nivel:" + nivelJugador + " | pts:" + puntuacion + "]";
    }
}
