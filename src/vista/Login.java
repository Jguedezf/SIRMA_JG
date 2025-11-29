/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: Login.java
 * Descripcion: Ventana de inicio de sesion (capa de seguridad). Valida las
 *              credenciales del usuario antes de dar acceso al sistema.
 * Fecha: Noviembre 2025
 * Version: 2.1 (Correccion de import faltante)
 * -----------------------------------------------------------------------------
 */
package vista;

import controlador.ControladorSIRMA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException; // <--- ESTA ES LA LINEA QUE FALTABA
import javax.imageio.ImageIO;

public class Login extends JFrame {

    // --- Atributos de Componentes UI ---
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private BotonFuturista btnEntrar;
    private Image imagenFondoLogin;
    private Image imagenIconoCandado;

    /**
     * Constructor de la ventana de Login.
     */
    public Login() {
        setTitle("Acceso Seguro - SIRMA JG");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Ventana sin bordes estandar de Windows/OS
        setLayout(new BorderLayout());

        cargarImagenes();

        // --- BARRA SUPERIOR PERSONALIZADA ---
        JPanel panelBarra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBarra.setBackground(new Color(35, 40, 45));
        panelBarra.add(crearBotonCierre());
        add(panelBarra, BorderLayout.NORTH);

        // --- PANEL CENTRAL CON FORMULARIO ---
        JPanel panelContenido = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondoLogin != null) {
                    g.drawImage(imagenFondoLogin, 0, 0, getWidth(), getHeight(), this);
                    // Capa oscura para legibilidad
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(35, 40, 45));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelContenido.setBorder(new EmptyBorder(10, 40, 40, 40));
        add(panelContenido, BorderLayout.CENTER);

        // --- Organizacion de componentes con GridBagLayout ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Icono de candado
        JLabel lblIcono = new JLabel();
        if (imagenIconoCandado != null) {
            Image iconoEscalado = imagenIconoCandado.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(iconoEscalado));
        }
        panelContenido.add(lblIcono, gbc);

        gbc.gridy++;
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelContenido.add(lblTitulo, gbc);

        gbc.gridy++;
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.LIGHT_GRAY);
        panelContenido.add(lblUser, gbc);

        gbc.gridy++;
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsuario.setHorizontalAlignment(JTextField.CENTER);
        panelContenido.add(txtUsuario, gbc);

        gbc.gridy++;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.LIGHT_GRAY);
        panelContenido.add(lblPass, gbc);

        gbc.gridy++;
        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtClave.setHorizontalAlignment(JTextField.CENTER);
        panelContenido.add(txtClave, gbc);

        gbc.gridy++; gbc.insets = new Insets(30, 10, 10, 10);
        btnEntrar = new BotonFuturista("INGRESAR");
        panelContenido.add(btnEntrar, gbc);

        // --- Asignacion de Eventos ---
        btnEntrar.addActionListener(e -> validarIngreso());
        // Permite presionar Enter para ingresar
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    validarIngreso();
                }
            }
        };
        txtUsuario.addKeyListener(enterListener);
        txtClave.addKeyListener(enterListener);
    }

    /**
     * Carga las imagenes para la interfaz desde la carpeta 'fondo'.
     */
    private void cargarImagenes() {
        try {
            File f1 = new File("fondo/fondo_login.png");
            if (f1.exists()) imagenFondoLogin = ImageIO.read(f1);
            File f2 = new File("fondo/icono_login.png");
            if (f2.exists()) imagenIconoCandado = ImageIO.read(f2);
        } catch (IOException e) {
            System.err.println("Error al cargar imagenes del login: " + e.getMessage());
        }
    }

    /**
     * Valida las credenciales ingresadas por el usuario.
     */
    private void validarIngreso() {
        String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        // Logica de validacion (simulada, no segura para produccion)
        if ((usuario.equalsIgnoreCase("admin") && clave.equals("1234")) ||
                (usuario.equalsIgnoreCase("Johanna") && clave.equals("1234"))) {
            this.dispose(); // Cierra la ventana de login
            abrirSistemaPrincipal(usuario); // Abre la ventana principal
        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenciales inválidas. Por favor, intente de nuevo.",
                    "Error de Acceso",
                    JOptionPane.ERROR_MESSAGE);
            txtClave.setText("");
        }
    }

    /**
     * Inicia la aplicacion principal (VentanaPrincipal).
     * @param nombreUsuario El nombre del usuario para mostrar en el menu.
     */
    private void abrirSistemaPrincipal(String nombreUsuario) {
        SwingUtilities.invokeLater(() -> {
            // 1. Se crea el Controlador (el cerebro)
            ControladorSIRMA controlador = new ControladorSIRMA();
            // 2. Se crea la Vista Principal y se le pasa el controlador
            VentanaPrincipal vista = new VentanaPrincipal(controlador);
            // 3. Se personaliza la vista con el nombre de usuario
            vista.establecerUsuarioLogueado(nombreUsuario);
            // 4. Se hace visible la ventana principal
            vista.setVisible(true);
        });
    }

    /**
     * Crea un boton de cierre personalizado estilo Windows.
     * @return un JButton configurado para cerrar la aplicacion.
     */
    private JButton crearBotonCierre() {
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setPreferredSize(new Dimension(45, 30));
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setOpaque(true);
        btnCerrar.setBackground(new Color(35, 40, 45));

        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCerrar.setBackground(new Color(232, 17, 35)); // Rojo al pasar mouse
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCerrar.setBackground(new Color(35, 40, 45));
            }
        });
        btnCerrar.addActionListener(e -> System.exit(0));
        return btnCerrar;
    }
}