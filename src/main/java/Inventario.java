import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la colección de objetos {@link Inventariable} del personaje.
 * Tiene una capacidad máxima configurable.
 */
public class Inventario {

    /** Número máximo de items que puede contener el inventario. */
    private int capacidadMaxima;

    /** Lista de items almacenados en el inventario. */
    private List<Inventariable> items;

    /**
     * Construye un inventario vacío con la capacidad indicada.
     *
     * @param capacidadMaxima Número máximo de items permitidos.
     */
    public Inventario(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.items           = new ArrayList<>();
    }

    /**
     * Retorna la capacidad máxima del inventario.
     *
     * @return capacidad máxima.
     */
    public int getCapacidadMaxima() { return capacidadMaxima; }

    /**
     * Retorna la lista de items almacenados.
     *
     * @return lista de {@link Inventariable}.
     */
    public List<Inventariable> getItems() { return items; }

    /**
     * Agrega un item al inventario si no se ha alcanzado la capacidad máxima.
     *
     * @param item Item a agregar.
     * @return true si se agregó correctamente, false si el inventario está lleno.
     */
    public boolean agregarItem(Inventariable item) {
        if (items.size() < capacidadMaxima) {
            items.add(item);
            return true;
        }
        System.out.println("Inventario lleno. No se puede agregar: " + item);
        return false;
    }

    /**
     * Elimina un item del inventario.
     *
     * @param item Item a eliminar.
     * @return true si el item existía y fue eliminado, false si no se encontró.
     */
    public boolean eliminarItem(Inventariable item) {
        boolean eliminado = items.remove(item);
        if (eliminado) item.borrar();
        return eliminado;
    }

    /**
     * Imprime en consola todos los items del inventario usando su toString().
     */
    public void listarItems() {
        if (items.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        System.out.println("=== Inventario (" + items.size() + "/" + capacidadMaxima + ") ===");
        for (Inventariable i : items) {
            System.out.println("  - " + i.toString());
        }
    }

    /**
     * Retorna una representación textual del inventario.
     *
     * @return cadena con el número de items y la capacidad.
     */
    @Override
    public String toString() {
        return "Inventario[" + items.size() + "/" + capacidadMaxima + " items]";
    }
}
