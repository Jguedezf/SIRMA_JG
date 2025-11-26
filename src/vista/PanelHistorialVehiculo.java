/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Panel de la interfaz que muestra una tabla detallada con el
 *              historial de servicios de un vehiculo y permite gestionar registros.
 * Fecha: Noviembre 2025
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

/**
 * Clase PanelHistorialVehiculo
 * Componente visual encargado de renderizar la tabla de mantenimientos
 * asociados a un vehiculo especifico.
 */
public class PanelHistorialVehiculo extends JPanel {

    // Componentes de la interfaz
    public JTable tablaHistorial;
    public DefaultTableModel modeloTabla;
    public BotonFuturista btnEliminarRegistro;
    public BotonFuturista btnVolver;
    public JLabel lblTituloVehiculo;

    // Referencias al estado actual para la logica de eliminacion
    private Vehiculo vehiculoActual;
    private List<Mantenimiento> listaActual;

    /**
     * Constructor del panel.
     * Inicializa el diseño (Layout), configura la tabla para que no sea editable
     * y organiza los botones de accion.
     */
    public PanelHistorialVehiculo() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Titulo dinamico
        lblTituloVehiculo = new JLabel("Historial de Mantenimiento", SwingConstants.CENTER);
        lblTituloVehiculo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloVehiculo.setForeground(Color.WHITE);
        add(lblTituloVehiculo, BorderLayout.NORTH);

        // Configuracion de columnas y modelo de datos
        String[] columnas = {"Fecha", "Servicio", "Descripcion", "Costo ($)", "Km", "Prox. Servicio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea la edicion directa en las celdas
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(45, 50, 55));

        btnEliminarRegistro = new BotonFuturista("Eliminar Registro Seleccionado");
        btnVolver = new BotonFuturista("Volver al Dashboard");

        panelBotones.add(btnEliminarRegistro);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Metodo: cargarHistorial
     * Recibe un vehiculo, actualiza el titulo y llena la tabla con sus mantenimientos.
     * @param v El objeto Vehiculo a visualizar.
     */
    public void cargarHistorial(Vehiculo v) {
        this.vehiculoActual = v;
        this.listaActual = v.getHistorialMantenimientos();

        lblTituloVehiculo.setText("Historial del Vehiculo: " + v.getPlaca() + " (" + v.getMarca() + ")");
        modeloTabla.setRowCount(0); // Limpia la tabla anterior

        for (Mantenimiento m : listaActual) {
            Object[] fila = {
                    m.getFechaRealizacion(),
                    m.getTipoServicio(),
                    m.getDescripcionDetallada(),
                    m.getCosto(),
                    m.getKilometrajeActual(),
                    m.getFechaProximoServicio()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Metodo: getVehiculoActual
     * Permite al controlador saber que vehiculo se esta visualizando.
     * @return Objeto Vehiculo actual.
     */
    public Vehiculo getVehiculoActual() {
        return vehiculoActual;
    }

    /**
     * Metodo: getMantenimientoSeleccionado
     * Obtiene el objeto Mantenimiento correspondiente a la fila seleccionada en la tabla.
     * @return El objeto Mantenimiento seleccionado o null si no hay seleccion.
     */
    public Mantenimiento getMantenimientoSeleccionado() {
        int fila = tablaHistorial.getSelectedRow();
        if (fila != -1) {
            return listaActual.get(fila);
        }
        return null;
    }
}