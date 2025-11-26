/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelDashboard.java
 * Descripcion: Muestra la tabla de vehiculos y anade un boton para eliminacion.
 * Fecha: Noviembre 2025
 * Version: 1.8
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

    public JTable tablaVehiculos;
    public DefaultTableModel modeloTabla;
    public BotonFuturista btnEliminarVehiculo; // NUEVO BOTON

    public PanelDashboard() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Vehiculos Registrados en el Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Placa", "Marca", "Modelo", "Anio", "Propietario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            // Hacemos que la tabla no sea editable directamente
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVehiculos = new JTable(modeloTabla);
        tablaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo se puede seleccionar una fila

        JScrollPane scrollPane = new JScrollPane(tablaVehiculos);
        add(scrollPane, BorderLayout.CENTER);

        // Anadimos el boton de eliminar en la parte de abajo
        btnEliminarVehiculo = new BotonFuturista("Eliminar Vehiculo Seleccionado");
        add(btnEliminarVehiculo, BorderLayout.SOUTH);
    }

    public void actualizarTabla(List<Vehiculo> vehiculos) {
        modeloTabla.setRowCount(0);
        for (Vehiculo v : vehiculos) {
            Object[] fila = {
                    v.getPlaca(), v.getMarca(), v.getModelo(),
                    v.getAnio(), v.getPropietario().getNombreCompleto()
            };
            modeloTabla.addRow(fila);
        }
    }
}