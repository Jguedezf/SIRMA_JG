/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: VentanaPrincipal.java (Clase de la Vista)
 * Descripcion: Define la ventana principal (JFrame) de la aplicación,
 *              que servirá como contenedor para los demás componentes de la GUI.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    /**
     * Constructor de la VentanaPrincipal.
     * Configura las propiedades iniciales de la ventana.
     */
    public VentanaPrincipal() {
        setTitle("SIRMA-JG: Sistema de Registro de Mantenimiento de Vehículos");
        setSize(1200, 700);
        setLocationRelativeTo(null); // Centra la ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termina el programa al cerrar.
    }
}