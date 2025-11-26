/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Main.java (Punto de Entrada)
 * Descripcion: Clase principal que inicia la aplicación, creando el controlador
 *              y lanzando la interfaz gráfica principal.
 * Fecha: Noviembre 2025
 * Version: 1.1 (Sin dependencias externas)
 * -----------------------------------------------------------------------------
 */
import controlador.ControladorSIRMA;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater es la forma correcta y segura
        // de iniciar una interfaz gráfica en Java.
        SwingUtilities.invokeLater(() -> {
            // 1. Crea una instancia del cerebro del sistema
            ControladorSIRMA controlador = new ControladorSIRMA();

            // 2. Crea la ventana principal
            VentanaPrincipal ventana = new VentanaPrincipal();

            // 3. ¡La hacemos visible!
            ventana.setVisible(true);
        });
    }
}