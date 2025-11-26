/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Formulario para registrar un nuevo mantenimiento. Utiliza
 *              listas desplegables para mejorar la usabilidad y consistencia.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.CatalogoServicios;
import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Clase PanelAgregarMantenimiento
 * Interfaz grafica para la captura de datos de un nuevo servicio.
 */
public class PanelAgregarMantenimiento extends JPanel {

    public JComboBox<String> comboVehiculos;
    public JComboBox<String> comboServicios;
    public JTextArea txtDescripcion;
    public JTextField txtCosto;
    public JTextField txtKilometraje;
    public BotonFuturista btnGuardarMantenimiento;
    public BotonFuturista btnVolver;

    /**
     * Constructor del panel.
     * Configura los componentes del formulario usando GridBagLayout.
     */
    public PanelAgregarMantenimiento() {
        setBackground(new Color(45, 50, 55));
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setLayout(new BorderLayout(20, 20));

        JLabel lblTitulo = new JLabel("Agregar Nuevo Mantenimiento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // Panel central con GridBagLayout para alineacion precisa
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(45, 50, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Seleccion de Vehiculo
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(crearLabel("Vehiculo (Placa):"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1.0;
        comboVehiculos = new JComboBox<>();
        panelFormulario.add(comboVehiculos, gbc);

        // Fila 1: Seleccion de Servicio
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Tipo de Servicio:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        comboServicios = new JComboBox<>(CatalogoServicios.getServicios());
        panelFormulario.add(comboServicios, gbc);

        // Fila 2: Descripcion
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.NORTHEAST;
        panelFormulario.add(crearLabel("Descripcion Detallada:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtDescripcion = new JTextArea(4, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panelFormulario.add(scrollDescripcion, gbc);

        // Fila 3: Costo
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(crearLabel("Costo (Ej: 50.0):"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtCosto = new JTextField(10);
        panelFormulario.add(txtCosto, gbc);

        // Fila 4: Kilometraje
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(crearLabel("Kilometraje Actual:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        txtKilometraje = new JTextField(10);
        panelFormulario.add(txtKilometraje, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel inferior de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(45, 50, 55));
        btnGuardarMantenimiento = new BotonFuturista("Guardar Mantenimiento");
        btnVolver = new BotonFuturista("Volver al Dashboard");
        panelBotones.add(btnGuardarMantenimiento);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Metodo: actualizarListaVehiculos
     * Refresca el ComboBox con las placas de los vehiculos actuales.
     * @param vehiculos Lista de vehiculos obtenida del controlador.
     */
    public void actualizarListaVehiculos(List<Vehiculo> vehiculos) {
        comboVehiculos.removeAllItems();
        for (Vehiculo v : vehiculos) {
            comboVehiculos.addItem(v.getPlaca());
        }
        // CORRECCION: Deseleccionar cualquier opcion para evitar errores
        comboVehiculos.setSelectedIndex(-1);
    }

    /**
     * Metodo: limpiarCampos
     * Restablece el formulario a su estado inicial.
     */
    public void limpiarCampos() {
        if (comboServicios.getItemCount() > 0) comboServicios.setSelectedIndex(0);
        txtDescripcion.setText("");
        txtCosto.setText("");
        txtKilometraje.setText("");
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }
}