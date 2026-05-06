import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import java.util.HashSet;
import java.util.Set;

/**
 * Controlador principal del juego. Gestiona la logica, el bucle de animacion,
 * la entrada del teclado, el renderizado del canvas JavaFX y la persistencia
 * del escenario en archivo de texto.
 */
public class GameController {

    /** Nombre del archivo de configuracion del escenario. */
    private static final String ARCHIVO_ESCENARIO = "escenario.txt";

    private static final int T    = JuegoApp.TILE;
    private static final int COLS = JuegoApp.COLS;
    private static final int ROWS = JuegoApp.ROWS;

    /** Jugador controlado por el usuario (subclase de Personaje). */
    private Jugador    jugador;
    /** Nivel actual del juego. */
    private Nivel      nivel;
    /** Inventario del jugador. */
    private Inventario inventario;
    /** Tesoro final que activa la condicion de victoria. */
    private Tesoro     tesoroFinal;

    private Canvas          canvas;
    private GraphicsContext gc;
    private Label           lblVida, lblPos, lblInventario, lblMensaje, lblPuntos;

    private boolean juegoTerminado = false;
    private boolean victoria       = false;
    private long    mensajeFin     = 0;

    /** Conjunto de teclas presionadas — permite movimiento diagonal. */
    private final Set<KeyCode> teclasPresionadas = new HashSet<>();
    private long ultimoMovJugador  = 0;
    private long ultimoMovEnemigos = 0;

    /**
     * Construye la interfaz JavaFX e inicia el bucle de animacion.
     *
     * @return BorderPane raiz con el canvas y el panel lateral.
     */
    public BorderPane buildUI() {
        inicializarModelo();

        canvas = new Canvas(COLS * T, ROWS * T);
        gc     = canvas.getGraphicsContext2D();

        VBox panel = new VBox(10);
        panel.setPrefWidth(210);
        panel.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 14;");

        Label titulo = new Label("JUEGO POO");
        titulo.setFont(Font.font("Monospaced", 15));
        titulo.setTextFill(Color.web("#e94560"));

        lblVida    = styledLabel("Vida: 100");
        lblPos     = styledLabel("Pos: (1, 1)");
        lblPuntos  = styledLabel("Pts: 0");
        lblMensaje = styledLabel("");
        lblMensaje.setTextFill(Color.web("#f5a623"));
        lblMensaje.setWrapText(true);

        Label subInv = new Label("-- Inventario --");
        subInv.setFont(Font.font("Monospaced", 12));
        subInv.setTextFill(Color.web("#aaa"));

        lblInventario = styledLabel(resumenInventario());
        lblInventario.setWrapText(true);

        Label controles = new Label(
                "-- Controles --\n" +
                        "Flechas: Mover\n" +
                        "2 flechas: Diagonal\n" +
                        "SPACE: Destruir\n" +
                        "I: Inventario\n" +
                        "G: Guardar escenario\n\n" +
                        "-- Objetivo --\n" +
                        "Encuentra el tesoro [T]"
        );
        controles.setFont(Font.font("Monospaced", 11));
        controles.setTextFill(Color.web("#888"));
        controles.setWrapText(true);

        panel.getChildren().addAll(titulo, lblVida, lblPos, lblPuntos,
                lblMensaje, subInv, lblInventario, controles);

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setRight(panel);
        root.setBackground(new Background(new BackgroundFill(Color.web("#0f0e17"), null, null)));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!juegoTerminado) {
                    if (now - ultimoMovJugador > 125_000_000L) {
                        procesarMovimientoJugador();
                        ultimoMovJugador = now;
                    }
                    if (now - ultimoMovEnemigos > 2_000_000_000L) {
                        nivel.moverElementosDinamicos();
                        ultimoMovEnemigos = now;
                    }
                    if (mensajeFin > 0 && now > mensajeFin) {
                        mensajeFin = 0;
                        lblMensaje.setText("");
                    }
                }
                dibujar();
            }
        }.start();

        return root;
    }

    /**
     * Inicializa el modelo del juego. Crea el inventario y el jugador,
     * luego intenta cargar el escenario desde el archivo de configuracion.
     * Si el archivo no existe, usa una configuracion por defecto.
     */
    private void inicializarModelo() {
        inventario = new Inventario(12);

        // Armas iniciales en el inventario
        Arma espada = new Arma("Espada", 30, 1.5);
        Arma arco   = new Arma("Arco",   15, 5.0);
        inventario.agregarItem(espada);
        inventario.agregarItem(arco);
        espada.registrar();
        arco.registrar();

        // Jugador — subclase de Personaje
        jugador = new Jugador("Heroe", 1, 1);

        // Nivel base
        nivel = new Nivel("Mazmorra Oscura", 1, "Normal", inventario);

        // R01: Leer configuracion inicial desde archivo de texto
        System.out.println("=== Cargando escenario desde: " + ARCHIVO_ESCENARIO + " ===");
        EscenarioReader.leerConfiguracion(ARCHIVO_ESCENARIO, nivel);

        // Si el archivo no tenia checkpoints ni obstaculos, usar configuracion por defecto
        if (nivel.getObstaculos().isEmpty() && nivel.getCheckPoints().isEmpty()) {
            System.out.println("Usando configuracion por defecto.");
            cargarConfiguracionPorDefecto();
        }

        // Tesoro final — subclase de Recompensa
        tesoroFinal = new Tesoro("Corona Magica", 500, "legendario", "legendaria", true);
    }

    /**
     * Carga una configuracion por defecto cuando el archivo no existe
     * o no contiene datos validos.
     */
    private void cargarConfiguracionPorDefecto() {
        nivel.agregarObstaculo(new Obstaculo("Roca",    10, 5,  3));
        nivel.agregarObstaculo(new Obstaculo("Tonel",   15, 9,  6));
        nivel.agregarObstaculo(new Obstaculo("Muro",    20, 13, 2));
        nivel.agregarObstaculo(new Obstaculo("Barril",  10, 7,  10));
        nivel.agregarObstaculo(new Obstaculo("Trampa",  25, 3,  8));
        nivel.agregarCheckPoint(new CheckPoint("CP-1", 6,  1));
        nivel.agregarCheckPoint(new CheckPoint("CP-2", 14, 12));
        nivel.agregarElementoDinamico(new Utileria("Llave",   "abre puertas",  10, 4));
        nivel.agregarElementoDinamico(new Utileria("Escudo",  "protege",        3, 11));
        nivel.agregarElementoDinamico(new Utileria("Pocion",  "cura +30 vida", 15, 7));
        nivel.agregarElementoDinamico(new Enemigo("Orco",       40, 8,  2, 15));
        nivel.agregarElementoDinamico(new Enemigo("Esqueleto",  25, 12, 8, 10));
    }

    /**
     * Guarda la configuracion actual del escenario en el archivo de texto.
     * Implementa R05: guardar la configuracion actual en el mismo archivo.
     */
    private void guardarEscenario() {
        EscenarioWriter.escribirConfiguracion(ARCHIVO_ESCENARIO, nivel);
        mostrarMensaje("Escenario guardado en " + ARCHIVO_ESCENARIO);
    }

    // ── Dibujo ───────────────────────────────────────────────────────────────

    /**
     * Dibuja todos los elementos del juego en cada frame del bucle de animacion.
     * Implementa R02 y R04: despliega el estado actual del escenario.
     */
    private void dibujar() {
        gc.setFill(Color.web("#0f0e17"));
        gc.fillRect(0, 0, COLS * T, ROWS * T);

        gc.setStroke(Color.web("#1e1e30"));
        gc.setLineWidth(0.5);
        for (int c = 0; c <= COLS; c++) gc.strokeLine(c * T, 0, c * T, ROWS * T);
        for (int r = 0; r <= ROWS; r++) gc.strokeLine(0, r * T, COLS * T, r * T);

        gc.setStroke(Color.web("#e94560"));
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, COLS * T, ROWS * T);

        // Tesoro final
        int tx = COLS - 3, ty = ROWS - 3;
        if (!victoria) {
            gc.setFill(Color.web("#FFD700"));
            gc.fillOval(tx * T + 4, ty * T + 4, T - 8, T - 8);
            gc.setStroke(Color.web("#FFA500"));
            gc.setLineWidth(2);
            gc.strokeOval(tx * T + 4, ty * T + 4, T - 8, T - 8);
            gc.setFill(Color.web("#1a1a2e"));
            gc.setFont(Font.font("Monospaced", 11));
            gc.fillText("T", tx * T + T / 2.0 - 4, ty * T + T / 2.0 + 4);
        }

        // CheckPoints
        for (CheckPoint cp : nivel.getCheckPoints()) {
            double x = cp.getPosicionX() * T, y = cp.getPosicionY() * T;
            gc.setFill(cp.isActivado() ? Color.web("#00ff99") : Color.web("#226644"));
            gc.fillRoundRect(x + 4, y + 4, T - 8, T - 8, 8, 8);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(11));
            gc.fillText("CP", x + 8, y + T - 10);
        }

        // Elementos dinamicos (polimorfismo: Utileria y Enemigo tienen mover() diferente)
        for (ElementoDinamico ed : nivel.getElementosDinamicos()) {
            if (ed instanceof Enemigo en && en.isActivo()) {
                int ex = clamp(en.getPosicionX(), 0, COLS - 1);
                int ey = clamp(en.getPosicionY(), 0, ROWS - 1);
                gc.setFill(Color.web("#cc2222"));
                gc.fillRect(ex * T + 4, ey * T + 4, T - 8, T - 8);
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font(10));
                gc.fillText("EN", ex * T + 5, ey * T + T - 9);
            } else if (ed instanceof Utileria u) {
                int ux = clamp(u.getPosicionX(), 0, COLS - 1);
                int uy = clamp(u.getPosicionY(), 0, ROWS - 1);
                gc.setFill(Color.web("#f5a623"));
                dibujarDiamante(ux * T + T / 2.0, uy * T + T / 2.0, T / 3.0);
                gc.setFill(Color.web("#1a1a2e"));
                gc.setFont(Font.font(9));
                gc.fillText(u.getNombre().substring(0, 1), ux * T + T / 2.0 - 3, uy * T + T / 2.0 + 4);
            }
        }

        // Obstaculos
        for (Obstaculo obs : nivel.getObstaculos()) {
            double ox = obs.getPosicionX() * T, oy = obs.getPosicionY() * T;
            gc.setFill(Color.web("#5a3e2b"));
            gc.fillRect(ox + 2, oy + 2, T - 4, T - 4);
            gc.setStroke(Color.web("#8b6543"));
            gc.setLineWidth(1.5);
            gc.strokeRect(ox + 2, oy + 2, T - 4, T - 4);
        }

        // Jugador
        double px = jugador.getPosicionX() * T, py = jugador.getPosicionY() * T;
        gc.setFill(Color.web("#00000055"));
        gc.fillOval(px + 6, py + T - 8, T - 12, 6);
        gc.setFill(Color.web("#e94560"));
        gc.fillOval(px + 6, py + 6, T - 12, T - 12);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeOval(px + 6, py + 6, T - 12, T - 12);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(11));
        gc.fillText("JG", px + 7, py + T - 10);

        actualizarPanel();

        if (juegoTerminado) {
            if (victoria) dibujarVictoria();
            else          dibujarGameOver();
        }
    }

    private void dibujarDiamante(double cx, double cy, double r) {
        gc.fillPolygon(
                new double[]{cx, cx + r, cx, cx - r},
                new double[]{cy - r, cy, cy + r, cy}, 4
        );
    }

    private void dibujarGameOver() {
        gc.setFill(Color.web("#00000099"));
        gc.fillRect(0, 0, COLS * T, ROWS * T);
        gc.setFill(Color.web("#e94560"));
        gc.setFont(Font.font("Monospaced", 48));
        gc.fillText("GAME OVER", COLS * T / 2.0 - 145, ROWS * T / 2.0);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Monospaced", 16));
        gc.fillText("Reinicia el programa para volver a jugar", COLS * T / 2.0 - 200, ROWS * T / 2.0 + 40);
    }

    private void dibujarVictoria() {
        gc.setFill(Color.web("#00000099"));
        gc.fillRect(0, 0, COLS * T, ROWS * T);
        gc.setFill(Color.web("#FFD700"));
        gc.setFont(Font.font("Monospaced", 44));
        gc.fillText("VICTORIA!", COLS * T / 2.0 - 125, ROWS * T / 2.0);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Monospaced", 16));
        gc.fillText("Encontraste la Corona Magica!", COLS * T / 2.0 - 165, ROWS * T / 2.0 + 40);
        gc.fillText("Puntos finales: " + jugador.getPuntuacion(), COLS * T / 2.0 - 90, ROWS * T / 2.0 + 70);
    }

    // ── Controles ────────────────────────────────────────────────────────────

    /**
     * Registra una tecla como presionada. Maneja acciones inmediatas
     * como destruir obstaculos (SPACE), listar inventario (I) y guardar (G).
     *
     * @param e Evento de teclado recibido.
     */
    public void handleKeyPressed(KeyEvent e) {
        teclasPresionadas.add(e.getCode());
        if (e.getCode() == KeyCode.SPACE) destruirObstaculoAdyacente();
        if (e.getCode() == KeyCode.I)     listarInventario();
        if (e.getCode() == KeyCode.G)     guardarEscenario();
    }

    /**
     * Elimina una tecla del conjunto al soltarla.
     *
     * @param e Evento de teclado recibido.
     */
    public void handleKeyReleased(KeyEvent e) {
        teclasPresionadas.remove(e.getCode());
    }

    /**
     * Procesa el movimiento del jugador usando el conjunto de teclas activas.
     * Permite movimiento diagonal al combinar dos teclas de direccion.
     */
    private void procesarMovimientoJugador() {
        if (juegoTerminado) return;
        boolean arriba = teclasPresionadas.contains(KeyCode.UP);
        boolean abajo  = teclasPresionadas.contains(KeyCode.DOWN);
        boolean izq    = teclasPresionadas.contains(KeyCode.LEFT);
        boolean der    = teclasPresionadas.contains(KeyCode.RIGHT);
        if (!arriba && !abajo && !izq && !der) return;

        int nx = jugador.getPosicionX();
        int ny = jugador.getPosicionY();
        if (arriba) ny--;
        if (abajo)  ny++;
        if (izq)    nx--;
        if (der)    nx++;

        nx = clamp(nx, 0, COLS - 1);
        ny = clamp(ny, 0, ROWS - 1);

        final int fnx = nx, fny = ny;
        boolean bloqueado = nivel.getObstaculos().stream()
                .anyMatch(o -> o.getPosicionX() == fnx && o.getPosicionY() == fny);

        if (!bloqueado) {
            jugador.setPosicion(nx, ny);

            // Verificar CheckPoints
            for (CheckPoint cp : nivel.getCheckPoints()) {
                if (!cp.isActivado()
                        && cp.getPosicionX() == jugador.getPosicionX()
                        && cp.getPosicionY() == jugador.getPosicionY()) {
                    cp.activar();
                    jugador.ganarExperiencia(50);
                    jugador.agregarPuntuacion(100);
                    mostrarMensaje("CheckPoint " + cp.getNombre() + " activado! +100 pts");
                }
            }

            // Recoger Utileria
            ElementoDinamico recogido = null;
            for (ElementoDinamico ed : nivel.getElementosDinamicos()) {
                if (ed instanceof Utileria u
                        && u.getPosicionX() == jugador.getPosicionX()
                        && u.getPosicionY() == jugador.getPosicionY()) {
                    recogido = ed;
                    if (u.getNombre().equals("Pocion")) {
                        jugador.recibirDano(-30);
                        mostrarMensaje("Pocion usada! +30 vida");
                    } else {
                        Recompensa r = new Recompensa(u.getNombre(), 10, "utileria");
                        if (inventario.agregarItem(r)) {
                            r.registrar();
                            jugador.agregarPuntuacion(10);
                            mostrarMensaje("Recogiste: " + u.getNombre());
                        } else {
                            mostrarMensaje("Inventario lleno.");
                        }
                    }
                    break;
                }
            }
            if (recogido != null) nivel.getElementosDinamicos().remove(recogido);

            // Colision con Enemigos
            for (ElementoDinamico ed : nivel.getElementosDinamicos()) {
                if (ed instanceof Enemigo en && en.isActivo()
                        && en.getPosicionX() == jugador.getPosicionX()
                        && en.getPosicionY() == jugador.getPosicionY()) {
                    jugador.recibirDano(en.getDanoAtaque());
                    mostrarMensaje(en.getNombre() + " te ataco! -" + en.getDanoAtaque() + " vida");
                    if (jugador.getVida() <= 0) { gameOver(); return; }
                }
            }

            // Llego al tesoro final?
            int tx = COLS - 3, ty = ROWS - 3;
            if (jugador.getPosicionX() == tx && jugador.getPosicionY() == ty) {
                inventario.agregarItem(tesoroFinal);
                tesoroFinal.registrar();
                jugador.agregarPuntuacion(tesoroFinal.getValor());
                ganarJuego();
            }
        }
    }

    /**
     * Destruye el obstaculo mas cercano al jugador (distancia Manhattan menor o igual a 1).
     */
    private void destruirObstaculoAdyacente() {
        if (juegoTerminado) return;
        int px = jugador.getPosicionX(), py = jugador.getPosicionY();
        Obstaculo objetivo = null;
        for (Obstaculo o : nivel.getObstaculos()) {
            if (Math.abs(o.getPosicionX() - px) + Math.abs(o.getPosicionY() - py) <= 1) {
                objetivo = o; break;
            }
        }
        if (objetivo != null) {
            objetivo.destruye();
            nivel.getObstaculos().remove(objetivo);
            jugador.recibirDano(objetivo.getDano());
            jugador.agregarPuntuacion(20);
            mostrarMensaje(objetivo.getNombre() + " destruido! -" + objetivo.getDano() + " vida");
            if (jugador.getVida() <= 0) { gameOver(); return; }
            if (nivel.getObstaculos().isEmpty()) regenerarObstaculos();
        } else {
            mostrarMensaje("Sin obstaculos cerca.");
        }
    }

    /**
     * Regenera 5 obstaculos aleatorios cuando el mapa queda sin ninguno.
     */
    private void regenerarObstaculos() {
        java.util.Random r = new java.util.Random();
        String[] nombres = {"Roca", "Tonel", "Muro", "Barril", "Trampa"};
        for (int i = 0; i < 5; i++) {
            int ox, oy;
            do {
                ox = r.nextInt(COLS - 2) + 1;
                oy = r.nextInt(ROWS - 2) + 1;
            } while (ox == jugador.getPosicionX() && oy == jugador.getPosicionY());
            nivel.agregarObstaculo(new Obstaculo(nombres[i], r.nextInt(20) + 5, ox, oy));
        }
        mostrarMensaje("Nuevos obstaculos aparecieron!");
    }

    /** Imprime el inventario completo en consola. */
    private void listarInventario() {
        inventario.listarItems();
        mostrarMensaje("Ver consola para inventario");
    }

    /**
     * Activa el estado de derrota y guarda el escenario.
     */
    private void gameOver() {
        juegoTerminado = true;
        victoria = false;
        jugador.destruye();
        guardarEscenario(); // R05: guardar estado al terminar
    }

    /**
     * Activa el estado de victoria y guarda el escenario.
     */
    private void ganarJuego() {
        juegoTerminado = true;
        victoria = true;
        guardarEscenario(); // R05: guardar estado al terminar
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private int clamp(int v, int min, int max) { return Math.max(min, Math.min(max, v)); }

    private void mostrarMensaje(String msg) {
        mensajeFin = System.nanoTime() + 2_500_000_000L;
        lblMensaje.setText(msg);
    }

    private void actualizarPanel() {
        lblVida.setText("Vida: " + jugador.getVida());
        lblPos.setText("Pos: (" + jugador.getPosicionX() + ", " + jugador.getPosicionY() + ")");
        lblPuntos.setText("Pts: " + jugador.getPuntuacion());
        lblInventario.setText(resumenInventario());
    }

    private String resumenInventario() {
        StringBuilder sb = new StringBuilder();
        for (Inventariable item : inventario.getItems()) {
            if (item instanceof Tesoro t)
                sb.append("[Tesoro] ").append(t.getNombre()).append("\n");
            else if (item instanceof Arma a)
                sb.append("[Arma] ").append(a.getNombre()).append(" dmg:").append(a.getDano()).append("\n");
            else if (item instanceof Recompensa r2)
                sb.append("[Item] ").append(r2.getNombre()).append(" +").append(r2.getValor()).append("\n");
        }
        return sb.isEmpty() ? "(vacio)" : sb.toString().trim();
    }

    private Label styledLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Monospaced", 12));
        l.setTextFill(Color.web("#e0e0e0"));
        return l;
    }
}
