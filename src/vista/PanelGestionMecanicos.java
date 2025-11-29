/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelGestionMecanicos.java
 * Descripcion: Panel de interfaz de usuario (UI) para la gestion CRUD de
 *              mecanicos. Permite al usuario crear, editar, visualizar y
 *              eliminar al personal tecnico del sistema.
 * Fecha: Noviembre 2025
 * Version: 2.0 (CRUD Completo)
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.Mecanico;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelGestionMecanicos extends JPanel {

    // --- Atributos de Componentes UI ---
    public JTextField txtNombre, txtEspecialidad;
    public BotonFuturista btnGuardar, btnLimpiar, btnEliminar;
    public JTable tablaMecanicos;
    public DefaultTableModel modeloTabla;

    // --- Atributo de Estado ---
    public Mecanico mecanicoEnEdicion;

    /**
     * Constructor del panel.
     */
    public PanelGestionMecanicos() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Gestión de Personal Técnico (Mecánicos)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // El panel combina el formulario arriba y la tabla abajo.
        add(crearPanelFormulario(), BorderLayout.CENTER);
        add(crearPanelTabla(), BorderLayout.SOUTH);
    }

    /**
     * Metodo de fabrica para crear el panel del formulario.
     * @return JPanel con los campos de texto y botones.
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                null, "Datos del Mecánico", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16), Color.WHITE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Fila de Nombre ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        panel.add(crearLabel("Nombre Completo:"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.9;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        // --- Fila de Especialidad ---
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(crearLabel("Especialidad:"), gbc);

        gbc.gridx = 1;
        txtEspecialidad = new JTextField(20);
        panel.add(txtEspecialidad, gbc);

        // --- Fila de Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);
        btnGuardar = new BotonFuturista("Guardar");
        btnLimpiar = new BotonFuturista("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        panel.add(panelBotones, gbc);

        return panel;
    }

    /**
     * Metodo de fabrica para crear el panel de la tabla.
     * @return JPanel con la tabla y el boton de eliminar.
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                null, "Mecánicos Registrados", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16), Color.WHITE));

        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Especialidad"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaMecanicos = new JTable(modeloTabla);
        tablaMecanicos.setRowHeight(28);
        tablaMecanicos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaMecanicos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaMecanicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tablaMecanicos);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getWidth(), 200)); // Altura fija para la tabla
        panel.add(scrollPane, BorderLayout.CENTER);

        btnEliminar = new BotonFuturista("Eliminar Seleccionado");
        btnEliminar.setEnabled(false);
        JPanel pSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pSur.setOpaque(false);
        pSur.add(btnEliminar);
        panel.add(pSur, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Metodo publico para limpiar el formulario y resetear el estado.
     */
    public void limpiarFormulario() {
        txtNombre.setText("");
        txtEspecialidad.setText("");
        tablaMecanicos.clearSelection();
        mecanicoEnEdicion = null; // Indica que no se esta editando.
        btnGuardar.setText("Guardar");
        txtNombre.setEditable(true); // El nombre es la clave, solo editable al crear.
        txtNombre.setBackground(Color.WHITE);
    }

    /**
     * Metodo de utilidad para crear JLabels con estilo consistente.
     * @param texto El texto del label.
     * @return JLabel estilizado.
     */
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }
}