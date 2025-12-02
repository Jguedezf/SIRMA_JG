/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelReportes.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Módulo de Vista que proporciona la interfaz de usuario para el subsistema
 * de generación de reportes. Permite al usuario seleccionar el formato de
 * exportación (HTML o TXT) y acceder a los archivos generados.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Panel que presenta las opciones para la generación de reportes del sistema.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel}.
 */
public class PanelReportes extends JPanel {

    // --- ATRIBUTOS DE UI (Públicos para acceso desde VentanaPrincipal) ---
    public BotonFuturista btnGenerarHTML;
    public BotonFuturista btnGenerarTXT;
    public BotonFuturista btnAbrirCarpeta;
    public BotonFuturista btnVolverInicio;
    public JLabel lblEstado;

    // --- ATRIBUTOS INTERNOS ---
    private Image backgroundImage;

    /**
     * Constructor del panel de reportes.
     * Construye la interfaz utilizando un GridBagLayout para centrar vertical
     * y horizontalmente todos los componentes.
     * PRINCIPIO POO: Composición - La interfaz se construye anidando componentes.
     */
    public PanelReportes() {
        // Carga de la imagen de fondo.
        try {
            backgroundImage = ImageIO.read(new File("fondo/fondo.png"));
        } catch (Exception e) {
            e.printStackTrace();
            setBackground(new Color(45, 50, 55));
        }

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // --- SECCIÓN 1: ENCABEZADO ---
        JLabel lblIcono = new JLabel();
        cargarIconoEnLabel(lblIcono, "fondo/icono_imprimir_reporte.png", 80);
        gbc.gridwidth = 2; // El ícono ocupa dos columnas.
        add(lblIcono, gbc);

        gbc.gridy++;
        JLabel lblTitulo = new JLabel("Centro de Reportes y Cierre", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, gbc);

        gbc.gridy++;
        JLabel lblDesc = new JLabel("Seleccione el formato de salida deseado:", SwingConstants.CENTER);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDesc.setForeground(Color.LIGHT_GRAY);
        add(lblDesc, gbc);

        // --- SECCIÓN 2: BOTONES DE GENERACIÓN DE REPORTES ---
        gbc.gridwidth = 1; // Se revierte a una columna por componente.
        gbc.gridy++;
        btnGenerarHTML = new BotonFuturista("Generar Reporte HTML (Visual)");
        btnGenerarHTML.setBackground(new Color(0, 120, 215));
        btnGenerarHTML.setPreferredSize(new Dimension(250, 50));
        add(btnGenerarHTML, gbc);

        gbc.gridx = 1;
        btnGenerarTXT = new BotonFuturista("Generar Reporte TXT (Plano)");
        btnGenerarTXT.setPreferredSize(new Dimension(250, 50));
        add(btnGenerarTXT, gbc);

        // --- SECCIÓN 3: ETIQUETA DE ESTADO (FEEDBACK PARA EL USUARIO) ---
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        lblEstado = new JLabel(" ", SwingConstants.CENTER); // Se inicia con un espacio para reservar el lugar.
        lblEstado.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblEstado.setForeground(new Color(255, 200, 0)); // Color inicial neutral.
        lblEstado.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblEstado, gbc);

        // --- SECCIÓN 4: BOTONES DE UTILIDAD ---
        gbc.gridy++;
        btnAbrirCarpeta = new BotonFuturista("Abrir Carpeta de Reportes");
        btnAbrirCarpeta.setBackground(new Color(60, 65, 70));
        add(btnAbrirCarpeta, gbc);

        gbc.gridy++;
        btnVolverInicio = new BotonFuturista("Volver al Inicio");
        btnVolverInicio.setBackground(new Color(150, 50, 50)); // Color rojo para acción de salida/retorno.
        add(btnVolverInicio, gbc);
    }

    /**
     * Actualiza el mensaje y el color de la etiqueta de estado para proporcionar
     * feedback visual al usuario sobre el resultado de una operación.
     * @param mensaje El texto a mostrar.
     * @param esError {@code true} si el mensaje es de error (rojo), {@code false} si es de éxito (verde).
     */
    public void setMensajeEstado(String mensaje, boolean esError) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(esError ? new Color(255, 80, 80) : new Color(0, 200, 83));
    }

    /**
     * Método de utilidad para cargar y escalar una imagen en un JLabel.
     * @param label El JLabel donde se colocará el ícono.
     * @param ruta La ruta del archivo de imagen.
     * @param size El tamaño (ancho y alto) al que se escalará la imagen.
     */
    private void cargarIconoEnLabel(JLabel label, String ruta, int size) {
        try {
            File f = new File(ruta);
            if (f.exists()) {
                Image img = ImageIO.read(f);
                label.setIcon(new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH)));
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo de imagen personalizado.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura).
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}