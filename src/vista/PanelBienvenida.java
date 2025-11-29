/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelBienvenida.java
 * Descripcion: Pantalla de inicio (Dashboard Principal). Renderiza una imagen
 *              de fondo y presenta los accesos directos principales en un
 *              formato de tarjetas visualmente atractivo.
 * Fecha: Noviembre 2025
 * Version: 3.0 (Con escalado de imagen de fondo mejorado)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PanelBienvenida extends JPanel {

    // --- Atributos de Componentes UI ---
    public JButton btnGestionFlota;
    public JButton btnGestionServicios;
    public JButton btnReportesCierre;

    // --- Atributos Internos ---
    private Image imagenFondo;

    /**
     * Constructor del PanelBienvenida.
     */
    public PanelBienvenida() {
        setLayout(new BorderLayout());
        cargarImagenFondo();

        // --- Titulo Principal ---
        JLabel lblTitulo = new JLabel("<html><center>Bienvenido al Sistema Inteligente de<br>Registro de Mantenimiento Automotriz (SIRMA JG)</center></html>", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0)); // Espaciado
        add(lblTitulo, BorderLayout.NORTH);

        // --- Panel de Tarjetas de Acceso Rapido ---
        JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 40, 0)); // 1 fila, 3 cols, espaciado horizontal de 40px
        panelTarjetas.setOpaque(false); // Transparente para ver el fondo
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(20, 40, 200, 40)); // Margenes

        btnGestionFlota = crearTarjetaBoton("Flota de Vehículos", "Ver Listado General", new Color(0, 120, 215));
        btnGestionServicios = crearTarjetaBoton("Gestión de Servicios", "Registrar / Consultar", new Color(0, 153, 51));
        btnReportesCierre = crearTarjetaBoton("Reportes y Cierre", "Exportar HTML / TXT", new Color(255, 102, 0));

        panelTarjetas.add(btnGestionFlota);
        panelTarjetas.add(btnGestionServicios);
        panelTarjetas.add(btnReportesCierre);

        add(panelTarjetas, BorderLayout.CENTER);
    }

    /**
     * Metodo privado para cargar la imagen de fondo.
     */
    private void cargarImagenFondo() {
        try {
            File archivoImagen = new File("fondo/fondo.png");
            if (archivoImagen.exists()) {
                imagenFondo = ImageIO.read(archivoImagen);
            } else {
                System.out.println("Advertencia: No se encontro la imagen en 'fondo/fondo.png'");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }
    }

    /**
     * Sobrescritura del metodo paintComponent para dibujar el fondo.
     * Logica de escalado "cover": La imagen se redimensiona para cubrir todo el
     * panel sin deformarse, recortando lo que sobre. Es similar al 'background-size: cover' en CSS.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            Graphics2D g2d = (Graphics2D) g;

            int panelAncho = getWidth();
            int panelAlto = getHeight();
            int imgAncho = imagenFondo.getWidth(this);
            int imgAlto = imagenFondo.getHeight(this);

            // Calcular la relacion de aspecto del panel y de la imagen
            double relacionAspectoPanel = (double) panelAncho / panelAlto;
            double relacionAspectoImg = (double) imgAncho / imgAlto;

            int nuevoAncho, nuevoAlto, x, y;

            if (relacionAspectoPanel > relacionAspectoImg) {
                // El panel es mas ancho que la imagen: la altura de la imagen debe coincidir con la del panel
                nuevoAncho = panelAncho;
                nuevoAlto = (int) (panelAncho / relacionAspectoImg);
                x = 0;
                y = (panelAlto - nuevoAlto) / 2; // Centrar verticalmente
            } else {
                // El panel es mas alto (o igual) que la imagen: el ancho de la imagen debe coincidir
                nuevoAncho = (int) (panelAlto * relacionAspectoImg);
                nuevoAlto = panelAlto;
                x = (panelAncho - nuevoAncho) / 2; // Centrar horizontalmente
                y = 0;
            }

            // Activar interpolacion para que no se pixele al redimensionar
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // Dibujar la imagen escalada y centrada
            g2d.drawImage(imagenFondo, x, y, nuevoAncho, nuevoAlto, this);

            // Capa oscura semitransparente para mejorar legibilidad del texto
            g2d.setColor(new Color(0, 0, 0, 120));
            g2d.fillRect(0, 0, panelAncho, panelAlto);

        } else {
            // Color de respaldo si no hay imagen
            g.setColor(new Color(45, 50, 55));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Metodo de fabrica para crear los botones con estilo de tarjeta.
     * @param titulo Texto principal de la tarjeta.
     * @param subtitulo Texto secundario.
     * @param colorBorde Color de la barra lateral decorativa.
     * @return un objeto JButton con el estilo personalizado.
     */
    private JButton crearTarjetaBoton(String titulo, String subtitulo, Color colorBorde) {
        // Se crea un JButton anonimo para sobrescribir su paintComponent
        JButton tarjeta = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo de la tarjeta (cambia de color con el hover)
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(65, 70, 75, 230));
                } else {
                    g2d.setColor(new Color(45, 45, 45, 200));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Barra lateral de color
                g2d.setColor(colorBorde);
                g2d.fillRoundRect(0, 0, 10, getHeight(), 10, 10); // Solo redondea las esquinas izquierdas

                super.paintComponent(g); // Dibuja el texto
            }
        };

        tarjeta.setLayout(new GridLayout(2, 1));
        tarjeta.setBorderPainted(false);
        tarjeta.setFocusPainted(false);
        tarjeta.setContentAreaFilled(false);
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblTituloTarjeta = new JLabel(titulo, SwingConstants.CENTER);
        lblTituloTarjeta.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTituloTarjeta.setForeground(Color.WHITE);

        JLabel lblSubtituloTarjeta = new JLabel(subtitulo, SwingConstants.CENTER);
        lblSubtituloTarjeta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtituloTarjeta.setForeground(Color.LIGHT_GRAY);

        tarjeta.add(lblTituloTarjeta);
        tarjeta.add(lblSubtituloTarjeta);

        return tarjeta;
    }
}