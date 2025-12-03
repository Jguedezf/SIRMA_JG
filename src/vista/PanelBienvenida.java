/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelBienvenida.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista que implementa la pantalla de bienvenida principal.
 * Utiliza pintura personalizada para renderizar un fondo de imagen escalado y
 * botones con diseño de tarjeta, aplicando Herencia y Polimorfismo.
 * ============================================================================
 */
package vista;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Panel de bienvenida que sirve como dashboard principal de la aplicación.
 * Muestra accesos directos a las funcionalidades más importantes del sistema.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel} para ser un componente de Swing.
 */
public class PanelBienvenida extends JPanel {

    // --- ATRIBUTOS (COMPONENTES DE UI) ---
    // Se declaran públicos para ser accesibles desde la clase controladora de la vista (VentanaPrincipal).
    public JButton btnGestionFlota;
    public JButton btnGestionServicios;
    public JButton btnReportesCierre;

    // Atributo encapsulado para la imagen de fondo.
    private Image imagenFondo;

    /**
     * Constructor del PanelBienvenida.
     * Se encarga de la composición y maquetación de la interfaz de bienvenida.
     */
    public PanelBienvenida() {
        // PROCESO: Se configura el layout y se cargan los recursos gráficos.
        setLayout(new BorderLayout());
        cargarImagenFondo();

        // =================================================================================
        // ESPACIO SUPERIOR INVISIBLE (Para que se vea el título de tu imagen de fondo)
        // =================================================================================
        JPanel panelEspacioSuperior = new JPanel();
        panelEspacioSuperior.setOpaque(false);
        panelEspacioSuperior.setPreferredSize(new Dimension(800, 220));
        add(panelEspacioSuperior, BorderLayout.NORTH);
        // =================================================================================

        // --- Panel de Tarjetas de Acceso Rápido ---
        // PRINCIPIO POO: Composición - Se utiliza un JPanel para agrupar los botones.
        JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 40, 0));
        panelTarjetas.setOpaque(false); // Se hace transparente para que se vea la imagen de fondo del panel principal.
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(20, 40, 200, 40));

        // Se crean los botones utilizando un método de fábrica para asegurar un estilo consistente.
        btnGestionFlota = crearTarjetaBoton("Flota de Vehículos", "Ver Listado General", new Color(0, 120, 215));
        btnGestionServicios = crearTarjetaBoton("Gestión de Servicios", "Registrar / Consultar", new Color(0, 153, 51));
        btnReportesCierre = crearTarjetaBoton("Reportes y Cierre", "Exportar HTML / TXT", new Color(255, 102, 0));

        panelTarjetas.add(btnGestionFlota);
        panelTarjetas.add(btnGestionServicios);
        panelTarjetas.add(btnReportesCierre);

        add(panelTarjetas, BorderLayout.CENTER);
    }

    /**
     * Carga la imagen de fondo desde el disco y la almacena en el atributo `imagenFondo`.
     * Maneja de forma segura posibles errores de I/O.
     */
    private void cargarImagenFondo() {
        try {
            File archivoImagen = new File("fondo/fondo.png");
            if (archivoImagen.exists()) {
                imagenFondo = ImageIO.read(archivoImagen);
            } else {
                System.out.println("Advertencia: No se encontró la imagen en 'fondo/fondo.png'");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo de imagen personalizado.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura) - Se redefine el comportamiento
     * de `paintComponent` para implementar una lógica de renderizado avanzada.
     *
     * @param g El contexto gráfico para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            Graphics2D g2d = (Graphics2D) g;

            // --- ALGORITMO DE ESCALADO DE IMAGEN "COVER" ---
            // Este algoritmo asegura que la imagen cubra todo el panel sin deformarse.
            int panelAncho = getWidth();
            int panelAlto = getHeight();
            int imgAncho = imagenFondo.getWidth(this);
            int imgAlto = imagenFondo.getHeight(this);

            double relacionAspectoPanel = (double) panelAncho / panelAlto;
            double relacionAspectoImg = (double) imgAncho / imgAlto;

            int nuevoAncho, nuevoAlto, x, y;

            if (relacionAspectoPanel > relacionAspectoImg) {
                nuevoAncho = panelAncho;
                nuevoAlto = (int) (panelAncho / relacionAspectoImg);
                x = 0;
                y = (panelAlto - nuevoAlto) / 2; // Centrado vertical.
            } else {
                nuevoAncho = (int) (panelAlto * relacionAspectoImg);
                nuevoAlto = panelAlto;
                x = (panelAncho - nuevoAncho) / 2; // Centrado horizontal.
                y = 0;
            }

            // Se activa la interpolación bilineal para un redimensionado de alta calidad.
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(imagenFondo, x, y, nuevoAncho, nuevoAlto, this);

            // --- MODIFICACIÓN: SE ELIMINÓ LA CAPA NEGRA ---
            // Antes había aquí un g2d.fillRect con color negro semitransparente.
            // Ahora la imagen se muestra tal cual es, brillante y a todo color.

        } else {
            // Si la imagen no se pudo cargar, se dibuja un color de fondo sólido.
            g.setColor(new Color(45, 50, 55));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Método de Fábrica (Factory Method) para crear los botones con estilo de tarjeta.
     * Encapsula la lógica de creación y estilización de un componente complejo.
     *
     * @param titulo Texto principal de la tarjeta.
     * @param subtitulo Texto secundario.
     * @param colorBorde Color de la barra lateral decorativa.
     * @return un {@link JButton} con el estilo de tarjeta personalizado.
     */
    private JButton crearTarjetaBoton(String titulo, String subtitulo, Color colorBorde) {
        // Se utiliza una clase anónima que hereda de JButton para aplicar
        // Polimorfismo y sobreescribir su método de pintado.
        JButton tarjeta = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Lógica de estado: el fondo cambia de color al pasar el ratón por encima (hover).
                Color colorFondo = getModel().isRollover() ? new Color(65, 70, 75, 230) : new Color(45, 45, 45, 200);
                g2d.setColor(colorFondo);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Barra lateral decorativa.
                g2d.setColor(colorBorde);
                g2d.fillRoundRect(0, 0, 10, getHeight(), 10, 10);

                // Llama al método padre para que dibuje el texto del botón encima de nuestro fondo.
                super.paintComponent(g);
            }
        };

        // Configuración de propiedades del botón.
        tarjeta.setLayout(new GridLayout(2, 1));
        tarjeta.setBorderPainted(false);
        tarjeta.setFocusPainted(false);
        tarjeta.setContentAreaFilled(false); // Crucial para que nuestro pintado personalizado funcione.
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Creación y estilización de las etiquetas de texto internas.
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