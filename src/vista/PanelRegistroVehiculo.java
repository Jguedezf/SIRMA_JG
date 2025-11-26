/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelRegistroVehiculo.java
 * Descripcion: Formulario redisenado para registrar un nuevo vehiculo y su propietario.
 *              Utiliza GridBagLayout para un diseno mas estetico y centrado.
 * Fecha: Noviembre 2025
 * Version: 2.0
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelRegistroVehiculo extends JPanel {

    public JTextField txtPlaca;
    public JTextField txtMarca;
    public JTextField txtModelo;
    public JTextField txtAnio;
    public JTextField txtColor;

    public JTextField txtNombrePropietario;
    public JTextField txtCedulaPropietario;
    public JTextField txtTelefonoPropietario;

    public BotonFuturista btnGuardar;

    public PanelRegistroVehiculo() {
        setBackground(new Color(45, 50, 55));
        setBorder(new EmptyBorder(20, 40, 20, 40));
        setLayout(new BorderLayout(20, 20));

        JLabel lblTitulo = new JLabel("Registrar Nuevo Vehiculo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // Panel del formulario usando GridBagLayout
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(45, 50, 55));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // Margen entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- DATOS DEL VEHICULO ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelFormulario.add(crearTituloSeccion("--- Datos del Vehiculo ---"), gbc);

        gbc.gridwidth = 1; gbc.gridy++; // Fila 1
        gbc.gridx = 0; panelFormulario.add(crearLabel("Placa:"), gbc);
        gbc.gridx = 1; txtPlaca = new JTextField(15); panelFormulario.add(txtPlaca, gbc);

        gbc.gridy++; // Fila 2
        gbc.gridx = 0; panelFormulario.add(crearLabel("Marca:"), gbc);
        gbc.gridx = 1; txtMarca = new JTextField(15); panelFormulario.add(txtMarca, gbc);

        gbc.gridy++; // Fila 3
        gbc.gridx = 0; panelFormulario.add(crearLabel("Modelo:"), gbc);
        gbc.gridx = 1; txtModelo = new JTextField(15); panelFormulario.add(txtModelo, gbc);

        gbc.gridy++; // Fila 4
        gbc.gridx = 0; panelFormulario.add(crearLabel("Anio:"), gbc);
        gbc.gridx = 1; txtAnio = new JTextField(15); panelFormulario.add(txtAnio, gbc);

        gbc.gridy++; // Fila 5
        gbc.gridx = 0; panelFormulario.add(crearLabel("Color:"), gbc);
        gbc.gridx = 1; txtColor = new JTextField(15); panelFormulario.add(txtColor, gbc);

        // --- DATOS DEL PROPIETARIO ---
        gbc.gridy++; // Separador
        gbc.gridx = 0; gbc.gridwidth = 2;
        panelFormulario.add(Box.createVerticalStrut(10), gbc);

        gbc.gridy++; // Titulo Propietario
        panelFormulario.add(crearTituloSeccion("--- Datos del Propietario ---"), gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0; panelFormulario.add(crearLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1; txtNombrePropietario = new JTextField(15); panelFormulario.add(txtNombrePropietario, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panelFormulario.add(crearLabel("Cedula:"), gbc);
        gbc.gridx = 1; txtCedulaPropietario = new JTextField(15); panelFormulario.add(txtCedulaPropietario, gbc);

        gbc.gridy++;
        gbc.gridx = 0; panelFormulario.add(crearLabel("Telefono:"), gbc);
        gbc.gridx = 1; txtTelefonoPropietario = new JTextField(15); panelFormulario.add(txtTelefonoPropietario, gbc);

        // Envolvemos en un panel flow para que se quede arriba y no se estire
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setBackground(new Color(45, 50, 55));
        wrapper.add(panelFormulario);
        add(wrapper, BorderLayout.CENTER);

        btnGuardar = new BotonFuturista("Guardar Vehiculo");
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(45, 50, 55));
        panelBoton.add(btnGuardar);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel crearTituloSeccion(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(200, 200, 200)); // Gris claro
        return label;
    }
}