/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelGestionMantenimientos.java
 * Descripcion: Panel de formulario con interfaz rediseñada para crear y editar
 *              ordenes de servicio. Incluye mejoras de usabilidad en campos.
 * Fecha: Noviembre 2025
 * Version: 8.2 (Mejora de Foco en Campos de Costo)
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.CatalogoServicios;
import modelo.Mantenimiento;
import modelo.Vehiculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class PanelGestionMantenimientos extends JPanel {

    public JLabel lblTituloFormulario;
    public JComboBox<String> comboVehiculos;
    public JComboBox<String> comboServicios;
    public JComboBox<String> comboEstado;
    public JTextArea areaDescripcion;
    public JTextField campoCostoManoObra;
    public JTextField campoCostoRepuestos;
    public JTextField campoKilometraje;
    public JTextField campoMecanicoAsignado;
    public JButton btnGuardar, btnImprimirSolicitud, btnLimpiar, btnVolver;

    private Mantenimiento mantenimientoEnEdicion;

    public PanelGestionMantenimientos() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 40, 40, 40));

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);

        lblTituloFormulario = new JLabel("Nueva Orden de Servicio", SwingConstants.CENTER);
        lblTituloFormulario.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTituloFormulario.setForeground(Color.WHITE);
        panelNorte.add(lblTituloFormulario, BorderLayout.NORTH);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelAcciones.setOpaque(false);

        btnGuardar = crearBotonEstiloOriginal("Guardar", "fondo/icono_guardar.png");
        btnImprimirSolicitud = crearBotonEstiloOriginal("Imprimir Solicitud", "fondo/icono_imprimir_solicitud.png");
        btnLimpiar = crearBotonEstiloOriginal("Limpiar", "fondo/limpiar_formulario.png");
        btnVolver = crearBotonEstiloOriginal("Volver", "fondo/icono_volver.png");

        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnImprimirSolicitud);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(btnVolver);
        panelNorte.add(panelAcciones, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.weightx = 0.5;
        gbc.gridy = 0; panelCampos.add(crearLabel("Vehículo (Placa):"), gbc);
        gbc.gridy = 1; comboVehiculos = crearComboBox(); panelCampos.add(comboVehiculos, gbc);
        gbc.gridy = 2; panelCampos.add(crearLabel("Tipo de Servicio:"), gbc);
        gbc.gridy = 3; comboServicios = crearComboBox(); comboServicios.setModel(new DefaultComboBoxModel<>(CatalogoServicios.getServicios())); panelCampos.add(comboServicios, gbc);
        gbc.gridy = 4; panelCampos.add(crearLabel("Estado de la Orden:"), gbc);
        gbc.gridy = 5; comboEstado = crearComboBox(); comboEstado.setModel(new DefaultComboBoxModel<>(new String[]{"Pendiente", "En Proceso", "Finalizado"})); panelCampos.add(comboEstado, gbc);

        gbc.gridx = 1; gbc.weightx = 0.5;
        gbc.gridy = 0; panelCampos.add(crearLabel("Kilometraje Actual:"), gbc);
        gbc.gridy = 1; campoKilometraje = crearTextField(); panelCampos.add(campoKilometraje, gbc);
        gbc.gridy = 2; panelCampos.add(crearLabel("Costo Mano de Obra ($):"), gbc);
        gbc.gridy = 3; campoCostoManoObra = crearTextField(); panelCampos.add(campoCostoManoObra, gbc);
        gbc.gridy = 4; panelCampos.add(crearLabel("Costo Repuestos ($):"), gbc);
        gbc.gridy = 5; campoCostoRepuestos = crearTextField(); panelCampos.add(campoCostoRepuestos, gbc);

        // <<-- MEJORA DE USABILIDAD: SELECCION AUTOMATICA -->>
        FocusAdapter focusSelector = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (e.getSource() instanceof JTextField) {
                    JTextField campo = (JTextField) e.getSource();
                    // Selecciona todo el texto del campo para facilitar la edicion.
                    SwingUtilities.invokeLater(campo::selectAll);
                }
            }
        };
        campoCostoManoObra.addFocusListener(focusSelector);
        campoCostoRepuestos.addFocusListener(focusSelector);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panelCampos.add(crearLabel("Mecánico Asignado (Automático):"), gbc);
        gbc.gridy = 7;
        campoMecanicoAsignado = crearTextField();
        campoMecanicoAsignado.setEditable(false);
        campoMecanicoAsignado.setBackground(new Color(60, 65, 70));
        panelCampos.add(campoMecanicoAsignado, gbc);

        gbc.gridy = 8;
        panelCampos.add(crearLabel("Descripción Detallada del Servicio:"), gbc);
        gbc.gridy = 9; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        areaDescripcion = new JTextArea(4, 30);
        areaDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        panelCampos.add(new JScrollPane(areaDescripcion), gbc);

        add(panelCampos, BorderLayout.CENTER);

        btnImprimirSolicitud.setEnabled(false);
    }

    private JButton crearBotonEstiloOriginal(String texto, String rutaIcono) {
        JButton boton = new JButton();
        try {
            ImageIcon icono = new ImageIcon(new ImageIcon(rutaIcono).getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH));
            boton.setIcon(icono);
        } catch (Exception e) { System.err.println("Error cargando icono: " + rutaIcono); boton.setText(texto); }
        JPanel panelTexto = new JPanel(); panelTexto.setOpaque(false);
        JLabel lblTexto = new JLabel(texto, SwingConstants.CENTER);
        lblTexto.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTexto.setForeground(Color.WHITE);
        panelTexto.add(lblTexto);
        boton.setLayout(new BorderLayout());
        boton.add(panelTexto, BorderLayout.SOUTH);
        boton.setPreferredSize(new Dimension(140, 100));
        boton.setBackground(new Color(60, 63, 65));
        boton.setBorder(BorderFactory.createLineBorder(new Color(85, 85, 85)));
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { if (boton.isEnabled()) { boton.setBackground(new Color(90, 93, 95)); } }
            public void mouseExited(java.awt.event.MouseEvent evt) { boton.setBackground(new Color(60, 63, 65)); }
        });
        return boton;
    }

    public void prepararParaNuevaOrden(List<Vehiculo> vehiculos) {
        mantenimientoEnEdicion = null;
        lblTituloFormulario.setText("Registrar Nueva Orden de Servicio");
        comboVehiculos.removeAllItems();
        comboVehiculos.addItem("--- Seleccione un Vehículo ---");
        for (Vehiculo v : vehiculos) {
            comboVehiculos.addItem(v.getPlaca() + " (" + v.getMarca() + " " + v.getModelo() + ")");
        }
        limpiarCamposFormulario();
        comboVehiculos.setEnabled(true);
        btnImprimirSolicitud.setEnabled(false);
    }

    public void prepararParaEditarOrden(Mantenimiento mantenimiento, Vehiculo vehiculo) {
        this.mantenimientoEnEdicion = mantenimiento;
        lblTituloFormulario.setText("Editando Orden: " + mantenimiento.getIdOrden());
        comboVehiculos.removeAllItems();
        comboVehiculos.addItem(vehiculo.getPlaca() + " (" + vehiculo.getMarca() + " " + vehiculo.getModelo() + ")");
        comboVehiculos.setSelectedIndex(0);
        comboVehiculos.setEnabled(false);
        comboServicios.setSelectedItem(mantenimiento.getTipoServicio());
        comboEstado.setSelectedItem(mantenimiento.getEstado());
        campoKilometraje.setText(String.valueOf(mantenimiento.getKilometrajeActual()));
        campoCostoManoObra.setText(String.format("%.2f", mantenimiento.getCostoManoObra()).replace(",","."));
        campoCostoRepuestos.setText(String.format("%.2f", mantenimiento.getCostoRepuestos()).replace(",","."));
        areaDescripcion.setText(mantenimiento.getDescripcionDetallada());
        btnImprimirSolicitud.setEnabled(true);
    }

    public void limpiarCamposFormulario() {
        if (comboVehiculos.isEnabled()) {
            comboVehiculos.setSelectedIndex(0);
        }
        comboServicios.setSelectedIndex(0);
        comboEstado.setSelectedItem("Pendiente");
        campoKilometraje.setText("");
        campoCostoManoObra.setText("0.00");
        campoCostoRepuestos.setText("0.00");
        campoMecanicoAsignado.setText("");
        areaDescripcion.setText("");
    }

    public Mantenimiento getMantenimientoEnEdicion() { return mantenimientoEnEdicion; }
    public String getPlacaSeleccionada() { if (comboVehiculos.getSelectedIndex() <= 0) return null; return ((String) comboVehiculos.getSelectedItem()).split(" ")[0]; }
    private JLabel crearLabel(String texto) { JLabel l = new JLabel(texto); l.setForeground(Color.WHITE); l.setFont(new Font("Segoe UI", Font.BOLD, 16)); return l; }
    private JTextField crearTextField() { JTextField tf = new JTextField(); tf.setFont(new Font("Segoe UI", Font.PLAIN, 16)); return tf; }
    private JComboBox<String> crearComboBox() { JComboBox<String> cb = new JComboBox<>(); cb.setFont(new Font("Segoe UI", Font.PLAIN, 16)); return cb; }
}