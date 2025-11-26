/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Panel que visualiza el listado general de la flota en una tabla.
 *              Incluye controles para eliminacion y navegacion de retorno.
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

/**
 * Clase PanelDashboard
 * Vista principal de datos. Muestra la tabla de vehiculos registrados.
 */
public class PanelDashboard extends JPanel {

    public JTable tablaVehiculos;
    public DefaultTableModel modeloTabla;
    public BotonFuturista btnEliminarVehiculo;
    public BotonFuturista btnVolverInicio; // Boton de navegacion

    /**
     * Constructor del PanelDashboard.
     * Configura la tabla, el scroll y los botones de accion.
     */
    public PanelDashboard() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Flota de Vehiculos Registrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // Configuracion del modelo de tabla (no editable)
        String[] columnas = {"Placa", "Marca", "Modelo", "Anio", "Propietario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaVehiculos = new JTable(modeloTabla);
        tablaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaVehiculos);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(45, 50, 55));

        btnEliminarVehiculo = new BotonFuturista("Eliminar Vehiculo Seleccionado");
        btnVolverInicio = new BotonFuturista("Volver al Inicio");

        panelBotones.add(btnEliminarVehiculo);
        panelBotones.add(btnVolverInicio);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Metodo: actualizarTabla
     * Refresca la vista de la tabla con la lista de vehiculos mas reciente.
     * @param vehiculos Lista de objetos Vehiculo a mostrar.
     */
    public void actualizarTabla(List<Vehiculo> vehiculos) {
        modeloTabla.setRowCount(0); // Limpia la tabla
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