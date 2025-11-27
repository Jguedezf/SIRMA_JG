/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Panel maestro para la Gestion de Ordenes.
 *              Combina formulario de ingreso detallado (con costos separados),
 *              tabla historica y controles CRUD.
 * Fecha: Noviembre 2025
 * Version: 5.3
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.CatalogoServicios;
import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelGestionMantenimientos extends JPanel {

    // Formulario
    public JComboBox<String> comboVehiculos;
    public JComboBox<String> comboServicios;
    public JComboBox<String> comboEstado;
    public JTextArea txtDescripcion;

    // Campos de Costos Separados (Mejora VALBOR)
    public JTextField txtCostoManoObra;
    public JTextField txtCostoRepuestos;

    public JTextField txtKilometraje;

    // Tabla
    public JTable tablaHistorial;
    public DefaultTableModel modeloTabla;

    // Botones
    public BotonFuturista btnGuardar;
    public BotonFuturista btnLimpiar;
    public BotonFuturista btnVolver;
    public BotonFuturista btnImprimir;
    public BotonFuturista btnEliminar;

    public PanelGestionMantenimientos() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Gestion Integral de Ordenes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central ---
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 20, 0));
        panelCentro.setOpaque(false);

        // 1. FORMULARIO
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(new Color(50, 55, 60));
        panelForm.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Nueva Orden de Servicio"));
        ((javax.swing.border.TitledBorder) panelForm.getBorder()).setTitleColor(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Datos Generales
        gbc.gridx=0; gbc.gridy=0; panelForm.add(crearLabel("Vehiculo:"), gbc);
        gbc.gridx=1; comboVehiculos = new JComboBox<>(); panelForm.add(comboVehiculos, gbc);

        gbc.gridx=0; gbc.gridy++; panelForm.add(crearLabel("Servicio:"), gbc);
        gbc.gridx=1; comboServicios = new JComboBox<>(CatalogoServicios.getServicios()); panelForm.add(comboServicios, gbc);

        gbc.gridx=0; gbc.gridy++; panelForm.add(crearLabel("Estado:"), gbc);
        gbc.gridx=1; comboEstado = new JComboBox<>(new String[]{"Pendiente", "En Proceso", "Finalizado"}); panelForm.add(comboEstado, gbc);

        // Costos Separados
        gbc.gridx=0; gbc.gridy++; panelForm.add(crearLabel("Mano de Obra ($):"), gbc);
        gbc.gridx=1; txtCostoManoObra = new JTextField(); panelForm.add(txtCostoManoObra, gbc);

        gbc.gridx=0; gbc.gridy++; panelForm.add(crearLabel("Repuestos ($):"), gbc);
        gbc.gridx=1; txtCostoRepuestos = new JTextField(); panelForm.add(txtCostoRepuestos, gbc);

        gbc.gridx=0; gbc.gridy++; panelForm.add(crearLabel("Kilometraje:"), gbc);
        gbc.gridx=1; txtKilometraje = new JTextField(); panelForm.add(txtKilometraje, gbc);

        gbc.gridx=0; gbc.gridy++; gbc.anchor = GridBagConstraints.NORTHWEST; panelForm.add(crearLabel("Detalle:"), gbc);
        gbc.gridx=1; txtDescripcion = new JTextArea(3, 15);
        panelForm.add(new JScrollPane(txtDescripcion), gbc);

        // Botones Formulario
        JPanel pnlBtnsForm = new JPanel(new FlowLayout());
        pnlBtnsForm.setOpaque(false);
        btnGuardar = new BotonFuturista("Guardar Orden");
        btnLimpiar = new BotonFuturista("Limpiar");
        pnlBtnsForm.add(btnGuardar);
        pnlBtnsForm.add(btnLimpiar);

        gbc.gridx=0; gbc.gridy++; gbc.gridwidth=2;
        panelForm.add(pnlBtnsForm, gbc);

        panelCentro.add(panelForm);

        // 2. TABLA HISTORIAL
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Historial del Vehiculo"));
        ((javax.swing.border.TitledBorder) panelTabla.getBorder()).setTitleColor(Color.WHITE);

        String[] cols = {"ID", "Fecha", "Servicio", "Estado", "Total"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaHistorial = new JTable(modeloTabla);
        panelTabla.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);

        // Botones Tabla
        JPanel pnlBtnsTabla = new JPanel(new FlowLayout());
        pnlBtnsTabla.setOpaque(false);
        btnImprimir = new BotonFuturista("Imprimir");
        btnImprimir.setBackground(new Color(255, 140, 0)); // Naranja
        btnEliminar = new BotonFuturista("Eliminar");
        btnEliminar.setBackground(new Color(150, 50, 50)); // Rojo

        pnlBtnsTabla.add(btnImprimir);
        pnlBtnsTabla.add(btnEliminar);
        panelTabla.add(pnlBtnsTabla, BorderLayout.SOUTH);

        panelCentro.add(panelTabla);
        add(panelCentro, BorderLayout.CENTER);

        // Boton Volver Global
        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        btnVolver = new BotonFuturista("Volver al Inicio");
        pnlSur.add(btnVolver);
        add(pnlSur, BorderLayout.SOUTH);
    }

    public void cargarVehiculos(List<Vehiculo> lista) {
        comboVehiculos.removeAllItems();
        for(Vehiculo v : lista) comboVehiculos.addItem(v.getPlaca());
        comboVehiculos.setSelectedIndex(-1);
    }

    public void limpiarCampos() {
        if(comboVehiculos.getItemCount()>0) comboVehiculos.setSelectedIndex(-1);
        if(comboServicios.getItemCount()>0) comboServicios.setSelectedIndex(0);
        txtCostoManoObra.setText("");
        txtCostoRepuestos.setText("");
        txtKilometraje.setText("");
        txtDescripcion.setText("");
        modeloTabla.setRowCount(0);
    }

    private JLabel crearLabel(String t) {
        JLabel l = new JLabel(t);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        return l;
    }
}