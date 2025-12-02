/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelMenuLateral.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Módulo de Vista que implementa la barra de navegación lateral principal.
 * Contiene los botones de acceso a los diferentes módulos del sistema, así
 * como la información del usuario logueado. Aplica principios de Herencia,
 * Composición y Polimorfismo para su construcción y apariencia.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Panel que actúa como la barra de navegación lateral estática de la aplicación.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel} para ser un componente de Swing.
 */
public class PanelMenuLateral extends JPanel {

    // --- ATRIBUTOS DE UI (Públicos para acceso desde el mediador, VentanaPrincipal) ---
    public BotonFuturista btnInicio;
    public BotonFuturista btnGestionVehiculos;
    public BotonFuturista btnOrdenesServicio;
    public BotonFuturista btnBusquedaInteligente;
    public BotonFuturista btnGestionMecanicos;
    public BotonFuturista btnCentroReportes;
    public BotonFuturista btnCerrarSesion;

    // --- ATRIBUTOS INTERNOS ---
    // PRINCIPIO POO: Encapsulamiento - Componentes internos que no necesitan ser
    // accedidos desde fuera se declaran privados.
    private JLabel lblUsuario;
    private Image imagenFondoMenu;

    /**
     * Constructor del PanelMenuLateral.
     * Se encarga de la composición y maquetación de toda la barra de navegación.
     * PRINCIPIO POO: Composición - La interfaz se construye anidando múltiples
     * componentes (paneles, botones, etiquetas) para formar la estructura final.
     */
    public PanelMenuLateral() {
        // --- CONFIGURACIÓN BASE DEL PANEL ---
        setPreferredSize(new Dimension(300, 0)); // Ancho fijo, altura flexible.
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Carga de la imagen de fondo con manejo de errores.
        try {
            File f = new File("fondo/fondo_menu.png");
            if (f.exists()) imagenFondoMenu = ImageIO.read(f);
        } catch (IOException e) {
            System.err.println("Advertencia: No se pudo cargar el fondo del menú: " + e.getMessage());
        }

        // --- SECCIÓN NORTE: PERFIL DE USUARIO ---
        JPanel panelPerfil = new JPanel();
        panelPerfil.setOpaque(false);
        panelPerfil.setLayout(new BoxLayout(panelPerfil, BoxLayout.Y_AXIS)); // Apilado vertical.
        panelPerfil.setBorder(new EmptyBorder(20, 0, 30, 0));

        lblUsuario = new JLabel("Usuario", SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 20));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRol = new JLabel("Administrador", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.ITALIC, 14));
        lblRol.setForeground(new Color(100, 200, 255));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPerfil.add(lblUsuario);
        panelPerfil.add(lblRol);
        panelPerfil.add(Box.createVerticalStrut(15));
        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(240, 5));
        panelPerfil.add(separador);
        add(panelPerfil, BorderLayout.NORTH);

        // --- SECCIÓN CENTRAL: BOTONERA DE NAVEGACIÓN ---
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 0, 15));
        panelBotones.setOpaque(false);

        btnInicio = new BotonFuturista("Inicio");
        btnGestionVehiculos = new BotonFuturista("Gestión de Vehículos");
        btnOrdenesServicio = new BotonFuturista("Órdenes de Servicio");
        btnBusquedaInteligente = new BotonFuturista("Búsqueda Inteligente");
        btnGestionMecanicos = new BotonFuturista("Gestión de Mecánicos");
        btnCentroReportes = new BotonFuturista("Centro de Reportes");

        panelBotones.add(btnInicio);
        panelBotones.add(btnGestionVehiculos);
        panelBotones.add(btnOrdenesServicio);
        panelBotones.add(btnBusquedaInteligente);
        panelBotones.add(btnGestionMecanicos);
        panelBotones.add(btnCentroReportes);
        add(panelBotones, BorderLayout.CENTER);

        // --- SECCIÓN SUR: BOTÓN DE CIERRE DE SESIÓN ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setOpaque(false);
        btnCerrarSesion = new BotonFuturista("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(150, 50, 50));
        btnCerrarSesion.setPreferredSize(new Dimension(220, 45));
        panelSur.add(btnCerrarSesion);
        add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo de imagen personalizado.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura).
     * @param g El contexto gráfico para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondoMenu != null) {
            g.drawImage(imagenFondoMenu, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Color de respaldo si la imagen de fondo no se carga.
            g.setColor(new Color(35, 40, 45));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Actualiza dinámicamente el nombre de usuario mostrado en el panel de perfil.
     * @param nombre El nombre del usuario que ha iniciado sesión.
     */
    public void setNombreUsuario(String nombre) {
        this.lblUsuario.setText("Hola, " + nombre);
    }
}