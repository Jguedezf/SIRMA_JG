/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Main.java (Punto de Entrada)
 * Descripcion: Punto de entrada de la aplicación. Instancia el controlador
 *              y la interfaz gráfica principal.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */

import controlador.ControladorSIRMA;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Se utiliza SwingUtilities.invokeLater para asegurar que la GUI
        // se construya y actualice en el hilo de despacho de eventos (EDT).
        SwingUtilities.invokeLater(() -> {
            ControladorSIRMA controlador = new ControladorSIRMA();
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}