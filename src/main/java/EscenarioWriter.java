import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria encargada de guardar la configuracion actual del
 * escenario (Nivel) en un archivo de texto plano.
 *
 */
public class EscenarioWriter {

    /**
     * Escribe la configuracion actual del nivel en el archivo indicado.
     * Sobrescribe el contenido previo del archivo.
     *
     * @param nombreArchivo Ruta del archivo de configuracion.
     * @param nivel         Nivel cuyo estado se va a guardar.
     */
    public static void escribirConfiguracion(String nombreArchivo, Nivel nivel) {
        List<String> lineas = new ArrayList<>();

        // Obstaculos: Obstaculo <nombre> <x> <y>
        for (Obstaculo o : nivel.getObstaculos()) {
            lineas.add("Obstaculo " + o.getNombre() + " " + o.getPosicionX() + " " + o.getPosicionY());
        }

        // Elementos dinamicos: Utileria o Enemigo
        for (ElementoDinamico ed : nivel.getElementosDinamicos()) {
            if (ed instanceof Utileria u) {
                lineas.add("Utileria " + u.getNombre() + " " + u.getPosicionX() + " " + u.getPosicionY());
            } else if (ed instanceof Enemigo en) {
                lineas.add("Enemigo " + en.getNombre() + " " + en.getVida()
                        + " " + en.getPosicionX() + " " + en.getPosicionY()
                        + " " + en.getDanoAtaque());
            }
        }

        // CheckPoints: CheckPoint <nombre> <x> <y>
        for (CheckPoint cp : nivel.getCheckPoints()) {
            lineas.add("CheckPoint " + cp.getNombre() + " " + cp.getPosicionX() + " " + cp.getPosicionY());
        }

        // Escribir al archivo
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(nombreArchivo));
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Configuracion guardada en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el archivo: " + e.getMessage());
                }
            }
        }
    }
}