/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Define el formulario para registrar un nuevo vehículo y su propietario.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelRegistroVehiculo extends JPanel {

    // Campos de texto para los datos del Vehículo
    public JTextField txtPlaca;
    public JTextField txtMarca;
    public JTextField txtModelo;
    public JTextField txtAnio;
    public JTextField txtColor;

    // Campos de texto para los datos del Propietario
    public JTextField txtNombrePropietario;
    public JTextField txtCedulaPropietario;
    public JTextField txtTelefonoPropietario;

    // Botón de acción
    public BotonFuturista btnGuardar;

    public PanelRegistroVehiculo() {
        setBackground(new Color(45, 50, 55));
        setBorder(new EmptyBorder(40, 40, 40, 40)); // Margen generoso
        setLayout(new BorderLayout(20, 20));

        // Título del panel
        JLabel lblTitulo = new JLabel("Registrar Nuevo Vehículo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // Panel para el formulario con GridLayout
        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 15, 15));
        panelFormulario.setBackground(new Color(45, 50, 55));

        // --- Campos del Vehículo ---
        panelFormulario.add(crearLabel("Placa:"));
        txtPlaca = new JTextField();
        panelFormulario.add(txtPlaca);

        panelFormulario.add(crearLabel("Marca:"));
        txtMarca = new JTextField();
        panelFormulario.add(txtMarca);

        panelFormulario.add(crearLabel("Modelo:"));
        txtModelo = new JTextField();
        panelFormulario.add(txtModelo);

        panelFormulario.add(crearLabel("Año:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        panelFormulario.add(crearLabel("Color:"));
        txtColor = new JTextField();
        panelFormulario.add(txtColor);

        // Separador visual
        panelFormulario.add(new JSeparator());
        panelFormulario.add(new JSeparator());

        // --- Campos del Propietario ---
        panelFormulario.add(crearLabel("Nombre del Propietario:"));
        txtNombrePropietario = new JTextField();
        panelFormulario.add(txtNombrePropietario);

        panelFormulario.add(crearLabel("Cédula del Propietario:"));
        txtCedulaPropietario = new JTextField();
        panelFormulario.add(txtCedulaPropietario);

        panelFormulario.add(crearLabel("Teléfono del Propietario:"));
        txtTelefonoPropietario = new JTextField();
        panelFormulario.add(txtTelefonoPropietario);

        add(panelFormulario, BorderLayout.CENTER);

        // Botón de Guardar
        btnGuardar = new BotonFuturista("Guardar Vehículo");
        add(btnGuardar, BorderLayout.SOUTH);
    }

    // Método de ayuda para crear etiquetas con el estilo deseado
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }
}