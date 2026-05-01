import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicación JavaFX.
 * Configura la ventana principal y conecta los eventos de teclado
 * con el controlador del juego.
 */
public class JuegoApp extends Application {

    /** Tamaño en píxeles de cada celda del mapa. */
    public static final int TILE = 40;

    /** Número de columnas del mapa. */
    public static final int COLS = 18;

    /** Número de filas del mapa. */
    public static final int ROWS = 14;

    /**
     * Inicia la aplicación JavaFX, crea la escena y configura los eventos.
     *
     * @param stage Ventana principal de la aplicación.
     */
    @Override
    public void start(Stage stage) {
        GameController controller = new GameController();
        BorderPane root = controller.buildUI();

        Scene scene = new Scene(root, COLS * TILE + 220, ROWS * TILE);

        // Dos listeners: keyPressed para detectar tecla activa, keyReleased para soltarla
        scene.setOnKeyPressed(controller::handleKeyPressed);
        scene.setOnKeyReleased(controller::handleKeyReleased);

        root.setFocusTraversable(true);
        root.requestFocus();

        stage.setTitle("Juego POO – JavaFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
