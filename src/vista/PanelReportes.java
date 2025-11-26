/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelReportes.java
 * Descripcion: Interfaz de generacion de reportes. Se limpio la redundancia
 *              en los mensajes de estado.
 * Fecha: Noviembre 2025
 * Version: 2.7
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelReportes extends JPanel {

    public BotonFuturista btnGenerarHTML;
    public BotonFuturista btnGenerarTXT;
    public BotonFuturista btnAbrirCarpeta;
    public BotonFuturista btnVolverInicio;
    public JLabel lblEstado;

    public PanelReportes() {
        setBackground(new Color(45, 50, 55));
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblTitulo = new JLabel("Centro de Reportes y Cierre", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridy++;
        JLabel lblDesc = new JLabel("Seleccione el formato de salida deseado:", SwingConstants.CENTER);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDesc.setForeground(Color.LIGHT_GRAY);
        add(lblDesc, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        btnGenerarHTML = new BotonFuturista("Generar Reporte HTML (Visual)");
        btnGenerarHTML.setBackground(new Color(0, 100, 200));
        add(btnGenerarHTML, gbc);

        gbc.gridx = 1;
        btnGenerarTXT = new BotonFuturista("Generar Reporte TXT (Plano)");
        add(btnGenerarTXT, gbc);

        // Area de Estado (Limpia al inicio para no ser redundante)
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        lblEstado = new JLabel("", SwingConstants.CENTER); // Texto vacio inicial
        lblEstado.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblEstado.setForeground(new Color(255, 200, 0));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblEstado, gbc);

        gbc.gridy++;
        btnAbrirCarpeta = new BotonFuturista("Abrir Carpeta de Reportes");
        add(btnAbrirCarpeta, gbc);

        gbc.gridy++;
        btnVolverInicio = new BotonFuturista("Volver al Inicio");
        add(btnVolverInicio, gbc);
    }

    public void setMensajeEstado(String mensaje, Color color) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(color);
    }
}