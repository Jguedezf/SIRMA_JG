/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelReportes.java
 * Descripcion: Interfaz de usuario para la generacion de reportes. Permite al
 *              usuario exportar los datos del sistema a diferentes formatos
 *              y acceder a la carpeta donde se guardan.
 * Fecha: Noviembre 2025
 * Version: 3.0 (Funcional y con mensajes de estado)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelReportes extends JPanel {

    // --- Atributos de Componentes UI ---
    public BotonFuturista btnGenerarHTML;
    public BotonFuturista btnGenerarTXT;
    public BotonFuturista btnAbrirCarpeta;
    public BotonFuturista btnVolverInicio;
    public JLabel lblEstado;

    /**
     * Constructor del panel.
     */
    public PanelReportes() {
        setBackground(new Color(45, 50, 55));
        setLayout(new GridBagLayout()); // GridBagLayout es ideal para centrar contenido
        setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Hace que los botones tengan el mismo ancho
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // El componente ocupa 2 columnas

        // --- Titulo ---
        JLabel lblTitulo = new JLabel("Centro de Reportes y Cierre", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, gbc);

        // --- Descripcion ---
        gbc.gridy++;
        JLabel lblDesc = new JLabel("Seleccione el formato de salida deseado:", SwingConstants.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDesc.setForeground(Color.LIGHT_GRAY);
        add(lblDesc, gbc);

        // --- Botones de Generacion ---
        gbc.gridwidth = 1; // Ahora cada boton ocupa 1 columna
        gbc.gridy++;
        btnGenerarHTML = new BotonFuturista("Generar Reporte HTML (Visual)");
        btnGenerarHTML.setColorFondoNormal(new Color(0, 120, 215));
        add(btnGenerarHTML, gbc);

        gbc.gridx = 1;
        btnGenerarTXT = new BotonFuturista("Generar Reporte TXT (Plano)");
        add(btnGenerarTXT, gbc);

        // --- Area de Mensajes de Estado ---
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        lblEstado = new JLabel(" ", SwingConstants.CENTER); // Texto con espacio para que ocupe altura
        lblEstado.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblEstado.setForeground(new Color(46, 204, 113)); // Color verde exito
        lblEstado.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblEstado, gbc);

        // --- Botones Adicionales ---
        gbc.gridy++;
        btnAbrirCarpeta = new BotonFuturista("Abrir Carpeta de Reportes");
        add(btnAbrirCarpeta, gbc);

        gbc.gridy++;
        btnVolverInicio = new BotonFuturista("Volver al Inicio");
        add(btnVolverInicio, gbc);
    }

    /**
     * Metodo para mostrar un mensaje de estado al usuario.
     * @param mensaje El texto a mostrar.
     * @param esError true si es un mensaje de error (rojo), false si es de exito (verde).
     */
    public void setMensajeEstado(String mensaje, boolean esError) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(esError ? new Color(231, 76, 60) : new Color(46, 204, 113));
    }
}