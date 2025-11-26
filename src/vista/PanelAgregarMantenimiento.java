/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelAgregarMantenimiento.java
 * Descripcion: Define el formulario para agregar un nuevo registro de mantenimiento
 *              a un vehiculo existente.
 * Fecha: Noviembre 2025
 * Version: 1.8
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PanelAgregarMantenimiento extends JPanel {

    public JComboBox<String> comboVehiculos; // Para seleccionar el vehiculo
    public JTextField txtTipoServicio;
    public JTextArea txtDescripcion;
    public JTextField txtCosto;
    public JTextField txtKilometraje;
    public BotonFuturista btnGuardarMantenimiento;
    public BotonFuturista btnVolver;

    public PanelAgregarMantenimiento() {
        setBackground(new Color(45, 50, 55));
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setLayout(new BorderLayout(20, 20));

        // Titulo del panel
        JLabel lblTitulo = new JLabel("Agregar Nuevo Mantenimiento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // Panel para el formulario
        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 15, 15));
        panelFormulario.setBackground(new Color(45, 50, 55));

        panelFormulario.add(crearLabel("Seleccionar Vehiculo (Placa):"));
        comboVehiculos = new JComboBox<>();
        panelFormulario.add(comboVehiculos);

        panelFormulario.add(crearLabel("Tipo de Servicio:"));
        txtTipoServicio = new JTextField();
        panelFormulario.add(txtTipoServicio);

        panelFormulario.add(crearLabel("Descripcion Detallada:"));
        txtDescripcion = new JTextArea(3, 20);
        panelFormulario.add(new JScrollPane(txtDescripcion));

        panelFormulario.add(crearLabel("Costo (Ej: 50.0):"));
        txtCosto = new JTextField();
        panelFormulario.add(txtCosto);

        panelFormulario.add(crearLabel("Kilometraje Actual:"));
        txtKilometraje = new JTextField();
        panelFormulario.add(txtKilometraje);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel para los botones de accion
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(45, 50, 55));
        btnGuardarMantenimiento = new BotonFuturista("Guardar Mantenimiento");
        btnVolver = new BotonFuturista("Volver al Dashboard");
        panelBotones.add(btnGuardarMantenimiento);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Carga la lista de placas de vehiculos en el JComboBox.
     * @param vehiculos Lista de todos los vehiculos del sistema.
     */
    public void actualizarListaVehiculos(List<Vehiculo> vehiculos) {
        comboVehiculos.removeAllItems(); // Limpia la lista anterior
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
}