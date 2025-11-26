/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelMenuLateral.java (Clase de la Vista)
 * Descripcion: Define el panel de navegación, organizando los botones de
 *              las funcionalidades principales de la aplicación.
 * Fecha: Noviembre 2025
 * Version: 1.1
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;

public class PanelMenuLateral extends JPanel {

    // Declaramos los botones como atributos para poder acceder a ellos más tarde
    public BotonFuturista btnRegistrarVehiculo;
    public BotonFuturista btnBuscarVehiculo;
    public BotonFuturista btnVerHistorial;
    public BotonFuturista btnSalir;

    public PanelMenuLateral() {
        setPreferredSize(new Dimension(250, 0));
        setBackground(new Color(35, 40, 45));

        // Usamos un GridLayout para que los botones se apilen verticalmente
        // (filas, columnas, espacio horizontal, espacio vertical)
        setLayout(new GridLayout(10, 1, 0, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Añade un margen

        // Creamos las instancias de nuestros botones personalizados
        btnRegistrarVehiculo = new BotonFuturista("Registrar Vehículo");
        btnBuscarVehiculo = new BotonFuturista("Buscar Vehículo");
        btnVerHistorial = new BotonFuturista("Ver Historial Completo");
        btnSalir = new BotonFuturista("Salir");

        // Añadimos los botones al panel
        add(new JLabel()); // Un espacio vacío arriba para estética
        add(btnRegistrarVehiculo);
        add(btnBuscarVehiculo);
        add(btnVerHistorial);

        // Espacios vacíos para empujar el botón de Salir hacia abajo
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());

        add(btnSalir);

        // === PRUEBA DE FUNCIONALIDAD ===
        // Un ActionListener es un "oyente" que espera a que ocurra una acción (un clic).
        // Por ahora, solo imprimirán un mensaje en la consola para probar que funcionan.
        btnRegistrarVehiculo.addActionListener(e -> System.out.println("Botón 'Registrar Vehículo' presionado."));
        btnBuscarVehiculo.addActionListener(e -> System.out.println("Botón 'Buscar Vehículo' presionado."));
        btnVerHistorial.addActionListener(e -> System.out.println("Botón 'Ver Historial' presionado."));

        // Hacemos que el botón Salir cierre la aplicación
        btnSalir.addActionListener(e -> System.exit(0));
    }
}