/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelMenuLateral.java
 * Descripcion: Menu de navegacion principal del sistema. Proporciona acceso
 *              a todos los modulos principales de la aplicacion y muestra
 *              la informacion del usuario que inicio sesion.
 * Fecha: Noviembre 2025
 * Version: 8.1 (Diseño y Estructura Final)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PanelMenuLateral extends JPanel {

    // --- Atributos de Componentes UI (publicos para acceso desde VentanaPrincipal) ---
    public BotonFuturista btnInicio;
    public BotonFuturista btnGestionVehiculos;
    public BotonFuturista btnOrdenesServicio;
    public BotonFuturista btnBusquedaInteligente;
    public BotonFuturista btnGestionMecanicos;
    public BotonFuturista btnCentroReportes;
    public BotonFuturista btnCerrarSesion;

    // --- Atributos Internos ---
    private final JLabel lblUsuario;
    private Image imagenFondoMenu;

    /**
     * Constructor del PanelMenuLateral.
     * Proceso: Configura el layout, carga la imagen de fondo, inicializa
     * y organiza todos los componentes visuales del menu.
     */
    public PanelMenuLateral() {
        setPreferredSize(new Dimension(280, 0)); // Ancho fijo del menu
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20)); // Margenes internos

        cargarImagenFondo();

        // --- Panel Superior: Informacion del Usuario ---
        JPanel panelPerfil = new JPanel();
        panelPerfil.setOpaque(false);
        panelPerfil.setLayout(new BoxLayout(panelPerfil, BoxLayout.Y_AXIS));
        panelPerfil.setBorder(new EmptyBorder(20, 0, 30, 0));

        lblUsuario = new JLabel("Usuario", SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRol = new JLabel("Administrador", SwingConstants.CENTER);
        lblRol.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblRol.setForeground(new Color(100, 200, 255));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(200, 5));

        panelPerfil.add(lblUsuario);
        panelPerfil.add(lblRol);
        panelPerfil.add(Box.createVerticalStrut(15));
        panelPerfil.add(separador);
        add(panelPerfil, BorderLayout.NORTH);

        // --- Panel Central: Botones de Navegacion ---
        JPanel panelBotones = new JPanel(new GridLayout(7, 1, 0, 15)); // 7 filas, 1 col, espaciado vertical de 15px
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

        // --- Panel Inferior: Boton de Salida ---
        JPanel panelSur = new JPanel(new FlowLayout());
        panelSur.setOpaque(false);
        btnCerrarSesion = new BotonFuturista("Cerrar Sesión");
        btnCerrarSesion.setColorFondoNormal(new Color(200, 70, 70)); // Color rojo para destacar
        btnCerrarSesion.setColorFondoHover(new Color(231, 90, 90));
        panelSur.add(btnCerrarSesion);
        add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Metodo privado para cargar la imagen de fondo del menu.
     * Proceso: Intenta leer un archivo de imagen. Si falla, imprime un error
     * pero no detiene la ejecucion del programa.
     */
    private void cargarImagenFondo() {
        try {
            File archivoImagen = new File("fondo/fondo_menu.png");
            if (archivoImagen.exists()) {
                imagenFondoMenu = ImageIO.read(archivoImagen);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo del menu: " + e.getMessage());
        }
    }

    /**
     * Sobrescritura del metodo paintComponent para dibujar la imagen de fondo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondoMenu != null) {
            // Dibuja la imagen cubriendo todo el panel.
            g.drawImage(imagenFondoMenu, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Si la imagen no se cargo, dibuja un color de fondo solido.
            g.setColor(new Color(35, 40, 45));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Metodo publico para actualizar el nombre del usuario en el menu.
     * @param nombre El nombre del usuario que ha iniciado sesion.
     */
    public void setNombreUsuario(String nombre) {
        // Capitaliza la primera letra para un formato mas estetico.
        String nombreFormateado = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
        this.lblUsuario.setText("Hola, " + nombreFormateado);
    }
}