/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelRegistroVehiculo.java
 * Descripcion: Formulario optimizado con GridBagLayout para un diseño visual
 *              centrado, compacto y profesional.
 * Fecha: Noviembre 2025
 * Version: 3.0 (UX Mejorada)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelRegistroVehiculo extends JPanel {

    public JTextField txtPlaca, txtMarca, txtModelo, txtAnio, txtColor;
    public JTextField txtNombrePropietario, txtCedulaPropietario, txtTelefonoPropietario;
    public BotonFuturista btnGuardar;

    public PanelRegistroVehiculo() {
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Registrar Nuevo Vehiculo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        // Panel Contenedor Central
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(45, 50, 55));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Datos del Vehiculo ---
        gbc.gridwidth = 2; gbc.gridy = 0;
        panelCentro.add(crearSeparador("Datos del Vehiculo"), gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0; panelCentro.add(crearLabel("Placa:"), gbc);
        gbc.gridx = 1; txtPlaca = new JTextField(15); panelCentro.add(txtPlaca, gbc);

        gbc.gridy++; gbc.gridx = 0; panelCentro.add(crearLabel("Marca:"), gbc);
        gbc.gridx = 1; txtMarca = new JTextField(15); panelCentro.add(txtMarca, gbc);

        gbc.gridy++; gbc.gridx = 0; panelCentro.add(crearLabel("Modelo:"), gbc);
        gbc.gridx = 1; txtModelo = new JTextField(15); panelCentro.add(txtModelo, gbc);

        gbc.gridy++; gbc.gridx = 0; panelCentro.add(crearLabel("Anio:"), gbc);
        gbc.gridx = 1; txtAnio = new JTextField(15); panelCentro.add(txtAnio, gbc);

        gbc.gridy++; gbc.gridx = 0; panelCentro.add(crearLabel("Color:"), gbc);
        gbc.gridx = 1; txtColor = new JTextField(15); panelCentro.add(txtColor, gbc);

        // --- Datos del Propietario ---
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        panelCentro.add(Box.createVerticalStrut(15), gbc);
        gbc.gridy++;
        panelCentro.add(crearSeparador("Datos del Propietario"), gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0; panelCentro.add(crearLabel("Nombre:"), gbc);
        gbc.gridx = 1; txtNombrePropietario = new JTextField(15); panelCentro.add(txtNombrePropietario, gbc);

        gbc.gridy++; gbc.gridx = 0; panelCentro.add(crearLabel("Cedula:"), gbc);
        gbc.gridx = 1; txtCedulaPropietario = new JTextField(15); panelCentro.add(txtCedulaPropietario, gbc);

        gbc.gridy++; gbc.gridx = 0; panelCentro.add(crearLabel("Telefono:"), gbc);
        gbc.gridx = 1; txtTelefonoPropietario = new JTextField(15); panelCentro.add(txtTelefonoPropietario, gbc);

        add(panelCentro, BorderLayout.CENTER);

        btnGuardar = new BotonFuturista("Guardar Vehiculo");
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(new Color(45, 50, 55));
        panelBtn.add(btnGuardar);
        add(panelBtn, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        l.setForeground(Color.WHITE);
        return l;
    }

    private JLabel crearSeparador(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        l.setForeground(new Color(200, 200, 200));
        return l;
    }
}