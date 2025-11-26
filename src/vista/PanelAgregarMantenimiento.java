/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelAgregarMantenimiento.java
 * Descripcion: Redisenado para usar un JComboBox para los tipos de servicio
 *              y mejorar la distribucion de los componentes.
 * Fecha: Noviembre 2025
 * Version: 1.9
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.CatalogoServicios;
import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PanelAgregarMantenimiento extends JPanel {

    public JComboBox<String> comboVehiculos;
    public JComboBox<String> comboServicios; // <-- REEMPLAZO DEL CAMPO DE TEXTO
    public JTextArea txtDescripcion;
    public JTextField txtCosto;
    public JTextField txtKilometraje;
    public BotonFuturista btnGuardarMantenimiento;
    public BotonFuturista btnVolver;

    public PanelAgregarMantenimiento() {
        setBackground(new Color(45, 50, 55));
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setLayout(new BorderLayout(20, 20));

        JLabel lblTitulo = new JLabel("Agregar Nuevo Mantenimiento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Panel de Formulario Mejorado ---
        // Usaremos GridBagLayout para un control mas preciso del tamano
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(45, 50, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Seleccionar Vehiculo
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(crearLabel("Vehiculo (Placa):"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        comboVehiculos = new JComboBox<>();
        panelFormulario.add(comboVehiculos, gbc);

        // Fila 1: Tipo de Servicio (Ahora es un ComboBox)
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Tipo de Servicio:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        comboServicios = new JComboBox<>(CatalogoServicios.getServicios()); // Cargamos los servicios
        panelFormulario.add(comboServicios, gbc);

        // Fila 2: Descripcion
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.NORTHEAST;
        panelFormulario.add(crearLabel("Descripcion Detallada:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtDescripcion = new JTextArea(4, 20); // Mas grande
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panelFormulario.add(scrollDescripcion, gbc);

        // Fila 3: Costo
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(crearLabel("Costo (Ej: 50.0):"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtCosto = new JTextField(10); // Tamano mas reducido
        panelFormulario.add(txtCosto, gbc);

        // Fila 4: Kilometraje
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(crearLabel("Kilometraje Actual:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtKilometraje = new JTextField(10); // Tamano mas reducido
        panelFormulario.add(txtKilometraje, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // --- Panel de Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(45, 50, 55));
        btnGuardarMantenimiento = new BotonFuturista("Guardar Mantenimiento");
        btnVolver = new BotonFuturista("Volver al Dashboard");
        panelBotones.add(btnGuardarMantenimiento);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void actualizarListaVehiculos(List<Vehiculo> vehiculos) {
        comboVehiculos.removeAllItems();
        for (Vehiculo v : vehiculos) {
            comboVehiculos.addItem(v.getPlaca());
        }
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Limpia todos los campos del formulario despues de guardar.
     */
    public void limpiarCampos() {
        comboServicios.setSelectedIndex(0); // Vuelve a "--- Seleccione ---"
        txtDescripcion.setText("");
        txtCosto.setText("");
        txtKilometraje.setText("");
    }
}