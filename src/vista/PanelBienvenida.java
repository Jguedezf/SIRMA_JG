/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelBienvenida.java
 * Descripcion: Pantalla de inicio. Renderiza una imagen de fondo personalizada
 *              y presenta los accesos directos principales.
 * Fecha: Noviembre 2025
 * Version: 2.7
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase PanelBienvenida
 * Panel con imagen de fondo y botones tipo tarjeta.
 */
public class PanelBienvenida extends JPanel {

    public JButton btnVehiculos;
    public JButton btnMantenimientos;
    public JButton btnReportes;

    private Image imagenFondo;

    public PanelBienvenida() {
        setLayout(new BorderLayout());
        cargarImagenFondo(); // Intenta cargar la imagen

        JLabel lblTitulo = new JLabel("<html><center>Bienvenido al Sistema Inteligente de<br>Registro de Mantenimiento Automotriz (SIRMA JG)</center></html>", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        // Sombra negra para que el texto se lea sobre cualquier foto
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 20, 0));
        panelTarjetas.setOpaque(false); // Transparente para ver el fondo
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(20, 40, 200, 40));

        btnVehiculos = crearTarjetaBoton("Flota de Vehiculos", "Ver Listado General", new Color(0, 120, 215));
        btnMantenimientos = crearTarjetaBoton("Gestion de Servicios", "Registrar / Consultar", new Color(0, 153, 51));
        btnReportes = crearTarjetaBoton("Reportes y Cierre", "Exportar HTML / TXT", new Color(255, 102, 0));

        panelTarjetas.add(btnVehiculos);
        panelTarjetas.add(btnMantenimientos);
        panelTarjetas.add(btnReportes);

        add(panelTarjetas, BorderLayout.CENTER);
    }

    /**
     * Metodo: cargarImagenFondo
     * Busca el archivo 'fondo.png' en la carpeta 'fondo' del proyecto.
     */
    private void cargarImagenFondo() {
        try {
            // ACTUALIZACION: Ruta ajustada a tu carpeta y formato PNG
            File archivoImagen = new File("fondo/fondo.png");
            if (archivoImagen.exists()) {
                imagenFondo = ImageIO.read(archivoImagen);
            } else {
                System.out.println("Advertencia: No se encontro la imagen en 'fondo/fondo.png'");
            }
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen de fondo.");
        }
    }

    /**
     * Sobrescritura del metodo paintComponent para dibujar la imagen de fondo
     * manteniendo su relacion de aspecto (estilo 'Cover').
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            Graphics2D g2 = (Graphics2D) g;

            // 1. Obtener dimensiones
            int panelAncho = getWidth();
            int panelAlto = getHeight();
            int imgAncho = imagenFondo.getWidth(this);
            int imgAlto = imagenFondo.getHeight(this);

            // 2. Calcular la escala para cubrir todo el panel sin deformar (COVER)
            double escalaAncho = (double) panelAncho / imgAncho;
            double escalaAlto = (double) panelAlto / imgAlto;
            double escala = Math.max(escalaAncho, escalaAlto); // Usar la mayor para llenar todo

            // 3. Calcular nuevas dimensiones y posicion centrada
            int nuevoAncho = (int) (imgAncho * escala);
            int nuevoAlto = (int) (imgAlto * escala);
            int x = (panelAncho - nuevoAncho) / 2;
            int y = (panelAlto - nuevoAlto) / 2;

            // 4. Activar interpolacion para que no se pixele al redimensionar
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // 5. Dibujar la imagen escalada y centrada
            g2.drawImage(imagenFondo, x, y, nuevoAncho, nuevoAlto, this);

            // 6. Capa oscura semitransparente para legibilidad
            g2.setColor(new Color(0, 0, 0, 120)); // Un poco mas suave (120 en vez de 150)
            g2.fillRect(0, 0, panelAncho, panelAlto);

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

                // Fondo de la tarjeta semi-transparente
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