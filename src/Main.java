/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Punto de entrada de la aplicacion (Entry Point).
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
     * @param args Argumentos de la linea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        // Se asegura que la GUI se inicie en el hilo de despacho de eventos de Swing.
        SwingUtilities.invokeLater(() -> {
            ControladorSIRMA controlador = new ControladorSIRMA();
            VentanaPrincipal ventana = new VentanaPrincipal(controlador);
            ventana.setVisible(true);
        });
    }
}