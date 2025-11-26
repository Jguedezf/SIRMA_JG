/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Panel principal que muestra una tabla con la lista de vehículos.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.Vehiculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelDashboard extends JPanel {

    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;

    public PanelDashboard() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Vehículos Registrados en el Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // Configuración de la tabla
        String[] columnas = {"Placa", "Marca", "Modelo", "Año", "Propietario"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaVehiculos = new JTable(modeloTabla);

        // Añadir la tabla a un JScrollPane para poder hacer scroll
        JScrollPane scrollPane = new JScrollPane(tablaVehiculos);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Actualiza la tabla con la lista de vehículos más reciente.
     * @param vehiculos La lista de vehículos a mostrar.
     */
    public void actualizarTabla(List<Vehiculo> vehiculos) {
        // Limpia la tabla
        modeloTabla.setRowCount(0);

        // Llena la tabla con los datos de los vehículos
        for (Vehiculo v : vehiculos) {
            Object[] fila = {
                    v.getPlaca(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getAnio(),
                    v.getPropietario().getNombreCompleto()
            };
            modeloTabla.addRow(fila);
        }
    }
}