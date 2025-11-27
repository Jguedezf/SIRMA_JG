/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelDashboard.java
 * Descripcion: Visualiza la Bitacora de Operaciones. Incluye barra de busqueda
 *              en tiempo real y tabla de ordenes.
 * Fecha: Noviembre 2025
 * Version: 3.0 (Bitacora Inteligente)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase PanelDashboard
 * Panel central de monitoreo de ordenes y servicios.
 */
public class PanelDashboard extends JPanel {

    public JTable tablaOrdenes; // Nombre actualizado
    public DefaultTableModel modeloTabla;
    public JTextField txtBuscador;
    public BotonFuturista btnVolverInicio;
    public BotonFuturista btnRefrescar;
    public BotonFuturista btnEliminarVehiculo; // Mantenemos este para compatibilidad o lo quitamos si ya no se usa

    /**
     * Constructor. Configura la interfaz de bitacora.
     */
    public PanelDashboard() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- Header: Titulo y Buscador ---
        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setBackground(new Color(45, 50, 55));

        JLabel lblTitulo = new JLabel("Bitacora de Operaciones y Servicios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        // Barra de busqueda
        JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBuscador.setBackground(new Color(45, 50, 55));
        JLabel lblBus = new JLabel("Buscar (Placa / ID Orden):");
        lblBus.setFont(new Font("Arial", Font.BOLD, 14));
        lblBus.setForeground(new Color(200, 200, 200));
        txtBuscador = new JTextField(15);

        panelBuscador.add(lblBus);
        panelBuscador.add(txtBuscador);
        panelNorte.add(panelBuscador, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);

        // --- Tabla de Ordenes ---
        String[] columnas = {"N Orden", "Fecha", "Placa", "Vehiculo", "Servicio", "Estado", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaOrdenes = new JTable(modeloTabla);
        tablaOrdenes.setRowHeight(25);
        tablaOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaOrdenes);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones de Accion ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(45, 50, 55));

        btnRefrescar = new BotonFuturista("Refrescar / Buscar");
        btnEliminarVehiculo = new BotonFuturista("Eliminar Orden Seleccionada"); // Renombrado para el usuario
        btnVolverInicio = new BotonFuturista("Volver al Inicio");

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnEliminarVehiculo);
        panelBotones.add(btnVolverInicio);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Metodo: llenarTablaOrdenes
     * Renderiza la matriz de datos recibida en la tabla visual.
     */
    public void llenarTablaOrdenes(Object[][] datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    // Metodo de compatibilidad (si VentanaPrincipal lo llama con el nombre viejo)
    public void actualizarTabla(java.util.List<modelo.Vehiculo> lista) {
        // Este metodo ya no se deberia usar en la version 3.0,
        // pero lo dejamos vacio o redirigimos si fuera necesario para evitar errores de compilacion
        // mientras actualizas VentanaPrincipal.
    }
}