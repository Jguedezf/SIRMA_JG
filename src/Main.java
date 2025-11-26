/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Punto de entrada de la aplicacion (Entry Point).
 *              Se encarga de inicializar el controlador y lanzar la interfaz grafica.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */

import controlador.ControladorSIRMA;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Clase Main
 * Contiene el metodo main que inicia la ejecucion del programa.
 */
public class Main {

    /**
     * Metodo principal.
     * Crea las instancias del Controlador y la Vista, y hace visible la GUI.
     * Se utiliza SwingUtilities.invokeLater para asegurar que la creacion de la interfaz
     * se realice en el hilo de despacho de eventos (EDT), siguiendo las buenas practicas de Swing.
     *
     * @param args Argumentos de la linea de comandos (no se utilizan en esta version).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Instanciacion del controlador (Logica de Negocio)
            ControladorSIRMA controlador = new ControladorSIRMA();

            // Instanciacion de la ventana principal (Vista), inyectando el controlador
            VentanaPrincipal ventana = new VentanaPrincipal(controlador);

            // Visualizacion de la aplicacion
            ventana.setVisible(true);
        });
    }
}