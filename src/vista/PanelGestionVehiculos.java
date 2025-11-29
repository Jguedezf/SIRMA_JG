/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelGestionVehiculos.java
 * Descripcion: Modulo de interfaz de usuario (UI) para la gestion CRUD de la
 *              flota de vehiculos.
 * Fecha: Noviembre 2025
 * Version: 8.1 (Correccion de titulo y carga de placa)
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PanelGestionVehiculos extends JPanel {

    public JTextField txtPlaca, txtMarca, txtModelo, txtAnio, txtColor;
    public JTextField txtNombrePropietario, txtCedulaPropietario, txtTelefonoPropietario;
    public BotonFuturista btnGuardar, btnLimpiar, btnEliminar;
    public JTable tablaVehiculos;
    public DefaultTableModel modeloTabla;
    public Vehiculo vehiculoEnEdicion;

    public PanelGestionVehiculos() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // <<-- CAMBIO: Titulo principal actualizado -->>
        JLabel lblTitulo = new JLabel("Registro de Vehículos para Mantenimiento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(10, 20));
        panelCentral.setOpaque(false);
        panelCentral.add(crearPanelFormulario(), BorderLayout.NORTH);
        panelCentral.add(crearPanelTabla(), BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        // <<-- CAMBIO: Titulo del borde actualizado -->>
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                null, "Datos del Vehículo y Propietario", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16), Color.WHITE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        gbc.gridx = 0; panelFormulario.add(crearLabel("Placa:"), gbc);
        gbc.gridx = 1; txtPlaca = new JTextField(10); panelFormulario.add(txtPlaca, gbc);
        gbc.gridx = 2; panelFormulario.add(crearLabel("Marca:"), gbc);
        gbc.gridx = 3; txtMarca = new JTextField(15); panelFormulario.add(txtMarca, gbc);
        gbc.gridx = 4; panelFormulario.add(crearLabel("Modelo:"), gbc);
        gbc.gridx = 5; txtModelo = new JTextField(15); panelFormulario.add(txtModelo, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; panelFormulario.add(crearLabel("Año:"), gbc);
        gbc.gridx = 1; txtAnio = new JTextField(10); panelFormulario.add(txtAnio, gbc);
        gbc.gridx = 2; panelFormulario.add(crearLabel("Color:"), gbc);
        gbc.gridx = 3; txtColor = new JTextField(15); panelFormulario.add(txtColor, gbc);

        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 6; gbc.insets = new Insets(10, 0, 5, 0);
        panelFormulario.add(new JSeparator(), gbc);
        gbc.insets = new Insets(5, 10, 5, 10); gbc.gridwidth = 1;

        gbc.gridy = 3;
        gbc.gridx = 0; panelFormulario.add(crearLabel("Propietario:"), gbc);
        gbc.gridx = 1; txtNombrePropietario = new JTextField(10); panelFormulario.add(txtNombrePropietario, gbc);
        gbc.gridx = 2; panelFormulario.add(crearLabel("Cédula:"), gbc);
        gbc.gridx = 3; txtCedulaPropietario = new JTextField(15); panelFormulario.add(txtCedulaPropietario, gbc);
        gbc.gridx = 4; panelFormulario.add(crearLabel("Teléfono:"), gbc);
        gbc.gridx = 5; txtTelefonoPropietario = new JTextField(15); panelFormulario.add(txtTelefonoPropietario, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelBotones.setOpaque(false);
        btnGuardar = new BotonFuturista("Guardar");
        btnLimpiar = new BotonFuturista("Limpiar Formulario");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 6;
        panelFormulario.add(panelBotones, gbc);

        return panelFormulario;
    }

    private JPanel crearPanelTabla() {
        JPanel panelTabla = new JPanel(new BorderLayout(10, 10));
        panelTabla.setOpaque(false);
        // <<-- CAMBIO: Titulo de la tabla actualizado -->>
        panelTabla.setBorder(BorderFactory.createTitledBorder(null, "Flota de Vehículos Registrados", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 16), Color.WHITE));

        modeloTabla = new DefaultTableModel(new String[]{"Placa", "Marca", "Modelo", "Año", "Propietario"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaVehiculos = new JTable(modeloTabla);
        tablaVehiculos.setRowHeight(36);
        tablaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaVehiculos.getColumnModel().getColumn(1).setCellRenderer(new LogoMarcaRenderer());

        panelTabla.add(new JScrollPane(tablaVehiculos), BorderLayout.CENTER);

        btnEliminar = new BotonFuturista("Eliminar Seleccionado");
        btnEliminar.setEnabled(false);
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setOpaque(false);
        panelSur.add(btnEliminar);
        panelTabla.add(panelSur, BorderLayout.SOUTH);

        return panelTabla;
    }

    public void limpiarFormulario() {
        vehiculoEnEdicion = null;
        txtPlaca.setText("");
        txtPlaca.setEditable(true);
        txtPlaca.setBackground(Color.WHITE);
        txtMarca.setText("");
        txtModelo.setText("");
        txtAnio.setText("");
        txtColor.setText("");
        txtNombrePropietario.setText("");
        txtCedulaPropietario.setText("");
        txtTelefonoPropietario.setText("");
        tablaVehiculos.clearSelection();
        btnGuardar.setText("Guardar");
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private static class LogoMarcaRenderer extends DefaultTableCellRenderer {
        private final Map<String, ImageIcon> cacheDeIconos = new HashMap<>();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String marca = (value == null) ? "" : value.toString().toLowerCase().trim();

            if (!cacheDeIconos.containsKey(marca)) {
                ImageIcon icon = new ImageIcon("fondo/" + marca + ".png");
                if (icon.getImage().getWidth(null) > 0) {
                    Image img = icon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                    cacheDeIconos.put(marca, new ImageIcon(img));
                } else {
                    cacheDeIconos.put(marca, null);
                }
            }

            ImageIcon iconoEnCache = cacheDeIconos.get(marca);
            if (iconoEnCache != null) {
                label.setIcon(iconoEnCache);
                label.setText(" " + value.toString());
                label.setIconTextGap(15);
            } else {
                label.setIcon(null);
                label.setText(value.toString());
            }

            return label;
        }
    }
}