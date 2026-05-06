import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria encargada de leer la configuracion inicial del
 * escenario desde un archivo de texto plano y cargarla en un Nivel.
 *
 * Formato esperado de cada linea:
 *   Obstaculo  &lt;nombre&gt; &lt;renglon&gt; &lt;columna&gt;
 *   Utileria   &lt;nombre&gt; &lt;renglon&gt; &lt;columna&gt;
 *   Enemigo    &lt;nombre&gt; &lt;vida&gt; &lt;renglon&gt; &lt;columna&gt; &lt;danoAtaque&gt;
 *   CheckPoint &lt;nombre&gt; &lt;renglon&gt; &lt;columna&gt;
 */
public class EscenarioReader {

    /**
     * Lee el archivo de configuracion y agrega los elementos al nivel indicado.
     * Las lineas que no coincidan con el formato conocido se ignoran.
     *
     * @param nombreArchivo Ruta del archivo de configuracion.
     * @param nivel         Nivel donde se cargaran los elementos leidos.
     */
    public static void leerConfiguracion(String nombreArchivo, Nivel nivel) {
        List<String> lineas = new ArrayList<>();
        BufferedReader reader = null;

        // Leer todas las lineas del archivo
        try {
            reader = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
            System.out.println("Configuracion leida del archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            System.out.println("Se usara la configuracion por defecto.");
            return;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el archivo: " + e.getMessage());
                }
            }
        }

        // Procesar cada linea y agregar el elemento correspondiente al nivel
        for (String linea : lineas) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            String[] partes = linea.split(" ");

            try {
                switch (partes[0]) {
                    case "Obstaculo" -> {
                        // Obstaculo <nombre> <x> <y>
                        String nombre = partes[1];
                        int x = Integer.parseInt(partes[2]);
                        int y = Integer.parseInt(partes[3]);
                        nivel.agregarObstaculo(new Obstaculo(nombre, 10, x, y));
                    }
                    case "Utileria" -> {
                        // Utileria <nombre> <x> <y>
                        String nombre = partes[1];
                        int x = Integer.parseInt(partes[2]);
                        int y = Integer.parseInt(partes[3]);
                        String descripcion = nombre.equals("Pocion") ? "cura +30 vida" : "objeto";
                        nivel.agregarElementoDinamico(new Utileria(nombre, descripcion, x, y));
                    }
                    case "Enemigo" -> {
                        // Enemigo <nombre> <vida> <x> <y> <danoAtaque>
                        String nombre = partes[1];
                        int vida  = Integer.parseInt(partes[2]);
                        int x     = Integer.parseInt(partes[3]);
                        int y     = Integer.parseInt(partes[4]);
                        int dano  = Integer.parseInt(partes[5]);
                        nivel.agregarElementoDinamico(new Enemigo(nombre, vida, x, y, dano));
                    }
                    case "CheckPoint" -> {
                        // CheckPoint <nombre> <x> <y>
                        String nombre = partes[1];
                        int x = Integer.parseInt(partes[2]);
                        int y = Integer.parseInt(partes[3]);
                        nivel.agregarCheckPoint(new CheckPoint(nombre, x, y));
                    }
                    default -> System.out.println("Linea no reconocida: " + linea);
                }
            } catch (Exception e) {
                System.err.println("Error al procesar linea: \"" + linea + "\" -> " + e.getMessage());
            }
        }
    }
}
