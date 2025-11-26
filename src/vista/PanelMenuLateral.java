/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelMenuLateral.java (Clase de la Vista)
 * Descripcion: Define el panel de navegación lateral de la aplicación.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;

// JPanel es un contenedor que se usa para agrupar componentes.
public class PanelMenuLateral extends JPanel {

    public PanelMenuLateral() {
        // Establecemos un tamaño preferido para este panel
        setPreferredSize(new Dimension(250, 0));

        // Le damos un color de fondo oscuro para el estilo "Dark Mode"
        setBackground(new Color(35, 40, 45));

        // Aquí, más adelante, agregaremos los botones del menú.
    }
}