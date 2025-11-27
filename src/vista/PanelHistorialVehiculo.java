/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Panel de la interfaz que muestra una tabla detallada con el
 *              historial de servicios de un vehiculo.
 * Fecha: Noviembre 2025
 * Version: 5.3
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.Mantenimiento;
import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelHistorialVehiculo extends JPanel {

    public JTable tablaHistorial;
    public DefaultTableModel modeloTabla;
    public BotonFuturista btnEliminarRegistro;
    public BotonFuturista btnVolver;
    public JLabel lblTituloVehiculo;

    private Vehiculo vehiculoActual;
    private List<Mantenimiento> listaActual;

    public PanelHistorialVehiculo() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        lblTituloVehiculo = new JLabel("Historial de Mantenimiento", SwingConstants.CENTER);
        lblTituloVehiculo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloVehiculo.setForeground(Color.WHITE);
        add(lblTituloVehiculo, BorderLayout.NORTH);

        String[] columnas = {"Fecha", "Servicio", "Descripcion", "Total ($)", "Km", "Prox. Servicio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(45, 50, 55));

        btnEliminarRegistro = new BotonFuturista("Eliminar Registro");
        btnVolver = new BotonFuturista("Volver al Dashboard");

        panelBotones.add(btnEliminarRegistro);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void cargarHistorial(Vehiculo v) {
        this.vehiculoActual = v;
        this.listaActual = v.getHistorialMantenimientos();

        lblTituloVehiculo.setText("Historial: " + v.getPlaca() + " (" + v.getMarca() + ")");
        modeloTabla.setRowCount(0);

        for (Mantenimiento m : listaActual) {
            Object[] fila = {
                    m.getFechaRealizacion(),
                    m.getTipoServicio(),
                    m.getDescripcionDetallada(),
                    "$" + m.getCostoTotal(), // USO DEL METODO NUEVO
                    m.getKilometrajeActual(),
                    m.getFechaProximoServicio()
            };
            modeloTabla.addRow(fila);
        }
    }

    public Vehiculo getVehiculoActual() { return vehiculoActual; }

    public Mantenimiento getMantenimientoSeleccionado() {
        int fila = tablaHistorial.getSelectedRow();
        if (fila != -1) return listaActual.get(fila);
        return null;
    }
}