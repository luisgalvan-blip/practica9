import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa un nivel del juego. Contiene obstáculos, checkpoints,
 * elementos dinámicos y el inventario compartido del nivel.
 */
public class Nivel {

    /** Nombre del nivel. */
    private String nombre;

    /** Número de nivel. */
    private int numero;

    /** Dificultad del nivel (ej. "Fácil", "Normal", "Difícil"). */
    private String dificultad;

    /** Lista de obstáculos presentes en el nivel. */
    private List<Obstaculo> obstaculos;

    /** Lista de checkpoints del nivel. */
    private List<CheckPoint> checkPoints;

    /** Lista de elementos dinámicos (personajes, utilería, enemigos). */
    private List<ElementoDinamico> elementosDinamicos;

    /** Inventario asociado al nivel. */
    private Inventario inventario;

    /**
     * Construye un nivel con los atributos indicados e inicializa las listas vacías.
     *
     * @param nombre     Nombre del nivel.
     * @param numero     Número de nivel.
     * @param dificultad Dificultad del nivel.
     * @param inventario Inventario asociado al nivel.
     */
    public Nivel(String nombre, int numero, String dificultad, Inventario inventario) {
        this.nombre    = nombre;
        this.numero    = numero;
        this.dificultad = dificultad;
        this.inventario = inventario;
        this.obstaculos         = new ArrayList<>();
        this.checkPoints        = new ArrayList<>();
        this.elementosDinamicos = new ArrayList<>();
    }

    /**
     * Retorna el nombre del nivel.
     *
     * @return nombre del nivel.
     */
    public String getNombre()     { return nombre; }

    /**
     * Retorna el número del nivel.
     *
     * @return número de nivel.
     */
    public int getNumero()        { return numero; }

    /**
     * Retorna la dificultad del nivel.
     *
     * @return dificultad del nivel.
     */
    public String getDificultad() { return dificultad; }

    /**
     * Retorna la lista de obstáculos del nivel.
     *
     * @return lista de {@link Obstaculo}.
     */
    public List<Obstaculo> getObstaculos()               { return obstaculos; }

    /**
     * Retorna la lista de checkpoints del nivel.
     *
     * @return lista de {@link CheckPoint}.
     */
    public List<CheckPoint> getCheckPoints()             { return checkPoints; }

    /**
     * Retorna la lista de elementos dinámicos del nivel.
     *
     * @return lista de {@link ElementoDinamico}.
     */
    public List<ElementoDinamico> getElementosDinamicos() { return elementosDinamicos; }

    /**
     * Agrega un obstáculo al nivel.
     *
     * @param obstaculo Obstáculo a agregar.
     */
    public void agregarObstaculo(Obstaculo obstaculo) {
        obstaculos.add(obstaculo);
    }

    /**
     * Agrega un checkpoint al nivel.
     *
     * @param checkPoint Checkpoint a agregar.
     */
    public void agregarCheckPoint(CheckPoint checkPoint) {
        checkPoints.add(checkPoint);
    }

    /**
     * Agrega un elemento dinámico al nivel.
     *
     * @param elemento Elemento dinámico a agregar.
     */
    public void agregarElementoDinamico(ElementoDinamico elemento) {
        elementosDinamicos.add(elemento);
    }

    /**
     * Mueve todos los elementos dinámicos del nivel en una dirección y
     * distancia aleatoria. Demuestra polimorfismo: cada elemento implementa
     * mover() de forma diferente (Utileria, Enemigo, etc.).
     */
    public void moverElementosDinamicos() {
        String[] direcciones = {"norte", "sur", "este", "oeste"};
        Random r = new Random();
        for (ElementoDinamico e : elementosDinamicos) {
            String dir  = direcciones[r.nextInt(4)];
            int    dist = r.nextInt(3) + 1;
            e.mover(dir, dist);
        }
    }

    /**
     * Imprime en consola el estado completo del nivel.
     */
    public void mostrarEstado() {
        System.out.println("=== Nivel: " + nombre + " | Dificultad: " + dificultad + " ===");
        System.out.println("Obstáculos:");
        for (Obstaculo o : obstaculos)
            System.out.println("  " + o);
        System.out.println("CheckPoints:");
        for (CheckPoint c : checkPoints)
            System.out.println("  " + c);
        System.out.println("Elementos dinámicos:");
        for (ElementoDinamico e : elementosDinamicos)
            System.out.println("  " + e);
    }

    /**
     * Retorna una representación textual del nivel.
     *
     * @return cadena con nombre y número del nivel.
     */
    @Override
    public String toString() {
        return "Nivel[" + nombre + " #" + numero + " | " + dificultad + "]";
    }
}
