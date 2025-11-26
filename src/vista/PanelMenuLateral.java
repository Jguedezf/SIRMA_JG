/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelMenuLateral.java
 * Descripcion: Menu lateral actualizado con boton de Inicio.
 * Fecha: Noviembre 2025
 * Version: 2.4
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;

public class PanelMenuLateral extends JPanel {

    public BotonFuturista btnInicio; // NUEVO BOTON
    public BotonFuturista btnRegistrarVehiculo;
    public BotonFuturista btnAgregarMantenimiento;
    public BotonFuturista btnVerHistorial;
    public BotonFuturista btnSalir;

    public PanelMenuLateral() {
        setPreferredSize(new Dimension(250, 0));
        setBackground(new Color(35, 40, 45));
        setLayout(new GridLayout(11, 1, 0, 10)); // Aumentamos filas
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnInicio = new BotonFuturista("Inicio / Dashboard"); // NUEVO
        btnRegistrarVehiculo = new BotonFuturista("Registrar Vehiculo");
        btnAgregarMantenimiento = new BotonFuturista("Agregar Mantenimiento");
        btnVerHistorial = new BotonFuturista("Ver Historial");
        btnSalir = new BotonFuturista("Salir");

        add(btnInicio); // Lo agregamos de primero
        add(new JLabel()); // Espaciador
        add(btnRegistrarVehiculo);
        add(btnAgregarMantenimiento);
        add(btnVerHistorial);
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(btnSalir);

        btnSalir.addActionListener(e -> System.exit(0));
    }
}