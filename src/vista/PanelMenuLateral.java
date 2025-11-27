/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Descripcion: Menu lateral. Se elimino el icono de usuario y se quito la
 *              capa de opacidad sobre la imagen de fondo.
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

    public BotonFuturista btnInicio;
    public BotonFuturista btnRegistrarVehiculo;
    public BotonFuturista btnAgregarMantenimiento;
    public BotonFuturista btnVerHistorial;
    public BotonFuturista btnSalir;

    private JLabel lblUsuario;
    private Image imagenFondoMenu;

    public PanelMenuLateral() {
        setPreferredSize(new Dimension(260, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        try {
            File f = new File("fondo/fondo_menu.png");
            if(f.exists()) imagenFondoMenu = ImageIO.read(f);
        } catch(IOException e) {}

        // --- SECCION DE PERFIL (Solo Texto, Sin Icono) ---
        JPanel panelPerfil = new JPanel();
        panelPerfil.setOpaque(false);
        panelPerfil.setLayout(new BoxLayout(panelPerfil, BoxLayout.Y_AXIS));
        panelPerfil.setBorder(new EmptyBorder(20, 0, 30, 0));

        // ELIMINADO: JLabel lblAvatar (Ya no esta el muñequito)

        lblUsuario = new JLabel("Usuario", SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRol = new JLabel("Administrador", SwingConstants.CENTER);
        lblRol.setFont(new Font("Arial", Font.ITALIC, 14));
        lblRol.setForeground(new Color(100, 200, 255));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPerfil.add(lblUsuario); // Solo nombre
        panelPerfil.add(lblRol);     // Solo rol

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(200, 5));
        panelPerfil.add(Box.createVerticalStrut(15));
        panelPerfil.add(separador);

        add(panelPerfil, BorderLayout.NORTH);

        // --- BOTONES ---
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 0, 10));
        panelBotones.setOpaque(false);

        btnInicio = new BotonFuturista("Inicio");
        btnRegistrarVehiculo = new BotonFuturista("Registro de Vehiculo");
        btnAgregarMantenimiento = new BotonFuturista("Gestion Mantenimiento");
        btnVerHistorial = new BotonFuturista("Bitacora General");

        panelBotones.add(btnInicio);
        panelBotones.add(btnRegistrarVehiculo);
        panelBotones.add(btnAgregarMantenimiento);
        panelBotones.add(btnVerHistorial);

        add(panelBotones, BorderLayout.CENTER);

        // --- SALIR ---
        JPanel panelSur = new JPanel(new FlowLayout());
        panelSur.setOpaque(false);
        btnSalir = new BotonFuturista("Cerrar Sesion");
        btnSalir.setBackground(new Color(150, 50, 50));
        panelSur.add(btnSalir);
        add(panelSur, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> System.exit(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(imagenFondoMenu != null) {
            // CORRECCION: Dibuja la imagen DIRECTA, sin capa negra encima
            g.drawImage(imagenFondoMenu, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(35, 40, 45));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void setNombreUsuario(String nombre) {
        this.lblUsuario.setText("Hola, " + nombre);
    }
}