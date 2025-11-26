/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: VentanaPrincipal.java (Clase de la Vista)
 * Descripcion: Define la ventana principal (JFrame) y organiza los paneles
 *              principales de la interfaz usando un BorderLayout.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*; // Importamos BorderLayout

public class VentanaPrincipal extends JFrame {

    // Declaramos los paneles como atributos de la clase
    private PanelMenuLateral panelMenu;
    private PanelContenidoPrincipal panelContenido;

    public VentanaPrincipal() {
        setTitle("SIRMA-JG: Sistema de Registro de Mantenimiento de Vehículos");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Usamos un BorderLayout para organizar los paneles
        setLayout(new BorderLayout());

        // 1. Creamos las instancias de nuestros paneles
        panelMenu = new PanelMenuLateral();
        panelContenido = new PanelContenidoPrincipal();

        // 2. Añadimos los paneles a la ventana en las posiciones deseadas
        add(panelMenu, BorderLayout.WEST); // El menú va al Oeste (izquierda)
        add(panelContenido, BorderLayout.CENTER); // El contenido va en el Centro
    }
}