/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelBienvenida.java
 * Descripcion: Pantalla de inicio. Textos de tarjetas actualizados para
 *              reflejar mejor la navegacion del sistema.
 * Fecha: Noviembre 2025
 * Version: 2.6
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PanelBienvenida extends JPanel {

    public JButton btnVehiculos;
    public JButton btnMantenimientos;
    public JButton btnReportes;

    private Image imagenFondo;

    public PanelBienvenida() {
        setLayout(new BorderLayout());
        cargarImagenFondo();

        JLabel lblTitulo = new JLabel("<html><center>Bienvenido al Sistema Inteligente de<br>Registro de Mantenimiento Automotriz (SIRMA JG)</center></html>", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 20, 0));
        panelTarjetas.setOpaque(false);
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(20, 40, 200, 40));

        // TEXTOS CORREGIDOS PARA MAYOR CLARIDAD
        btnVehiculos = crearTarjetaBoton("Consultar Flota", "Ver Tabla General", new Color(0, 120, 215));
        btnMantenimientos = crearTarjetaBoton("Nuevo Ingreso", "Registrar Vehiculo", new Color(0, 153, 51));
        btnReportes = crearTarjetaBoton("Reportes y Cierre", "Generar Documentos", new Color(255, 102, 0));

        panelTarjetas.add(btnVehiculos);
        panelTarjetas.add(btnMantenimientos);
        panelTarjetas.add(btnReportes);

        add(panelTarjetas, BorderLayout.CENTER);
    }

    private void cargarImagenFondo() {
        try {
            File archivoImagen = new File("fondo.jpg");
            if (archivoImagen.exists()) {
                imagenFondo = ImageIO.read(archivoImagen);
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen de fondo.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(new Color(45, 50, 55));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private JButton crearTarjetaBoton(String titulo, String subtitulo, Color colorBorde) {
        JButton tarjeta = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(new Color(60, 63, 65, 230));
                } else {
                    g2.setColor(new Color(45, 45, 45, 200));
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(colorBorde);
                g2.fillRoundRect(0, 0, 10, getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };

        tarjeta.setLayout(new GridLayout(2, 1));
        tarjeta.setBorderPainted(false);
        tarjeta.setFocusPainted(false);
        tarjeta.setContentAreaFilled(false);
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblT = new JLabel(titulo, SwingConstants.CENTER);
        lblT.setFont(new Font("Arial", Font.BOLD, 18));
        lblT.setForeground(Color.WHITE);

        JLabel lblS = new JLabel(subtitulo, SwingConstants.CENTER);
        lblS.setFont(new Font("Arial", Font.PLAIN, 14));
        lblS.setForeground(Color.LIGHT_GRAY);

        tarjeta.add(lblT);
        tarjeta.add(lblS);

        return tarjeta;
    }
}