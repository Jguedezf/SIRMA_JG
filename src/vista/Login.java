/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Descripcion: Login con boton de cierre estilo Windows (Sutil y Funcional).
 * -----------------------------------------------------------------------------
 */
package vista;

import controlador.ControladorSIRMA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private BotonFuturista btnEntrar;
    private Image imagenFondoLogin;
    private Image imagenIconoCandado;

    public Login() {
        setTitle("Acceso Seguro - SIRMA JG");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(new BorderLayout());

        cargarImagenes();

        // --- BARRA SUPERIOR ---
        // Usamos un panel transparente para que se vea el fondo si queremos,
        // o un color solido que combine con el tema.
        JPanel panelBarra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBarra.setBackground(new Color(35, 40, 45));

        // Boton de Cierre Estilo Windows
        JButton btnCerrar = new JButton("✕"); // Usamos un caracter Unicode mas bonito
        btnCerrar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setPreferredSize(new Dimension(45, 30)); // Un poco mas ancho
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);

        // Efecto Hover Personalizado
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setOpaque(true); // Necesario para que se pinte el fondo
        btnCerrar.setBackground(new Color(35, 40, 45)); // Fondo inicial (invisible)

        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCerrar.setBackground(new Color(232, 17, 35)); // Rojo al pasar mouse
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCerrar.setBackground(new Color(35, 40, 45)); // Volver al color normal
            }
        });

        btnCerrar.addActionListener(e -> System.exit(0));

        panelBarra.add(btnCerrar);
        add(panelBarra, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Igual que antes) ---
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondoLogin != null) {
                    g.drawImage(imagenFondoLogin, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(35, 40, 45));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panel.setBorder(new EmptyBorder(10, 40, 40, 40));
        add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Icono
        JLabel lblIcono = new JLabel();
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        if (imagenIconoCandado != null) {
            Image icono = imagenIconoCandado.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(icono));
        } else {
            lblIcono.setText("🔐");
            lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            lblIcono.setForeground(Color.WHITE);
        }
        panel.add(lblIcono, gbc);

        gbc.gridy++;
        JLabel lblTitulo = new JLabel("INICIAR SESION", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo, gbc);

        gbc.gridy++;
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.LIGHT_GRAY);
        panel.add(lblUser, gbc);

        gbc.gridy++;
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUsuario.setHorizontalAlignment(JTextField.CENTER);
        panel.add(txtUsuario, gbc);

        gbc.gridy++;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.LIGHT_GRAY);
        panel.add(lblPass, gbc);

        gbc.gridy++;
        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Arial", Font.PLAIN, 16));
        txtClave.setHorizontalAlignment(JTextField.CENTER);
        panel.add(txtClave, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        btnEntrar = new BotonFuturista("INGRESAR");
        btnEntrar.setBackground(new Color(0, 120, 215));
        panel.add(btnEntrar, gbc);

        btnEntrar.addActionListener(e -> validarIngreso());
    }

    private void cargarImagenes() {
        try {
            File f1 = new File("fondo/fondo_login.png");
            if (f1.exists()) imagenFondoLogin = ImageIO.read(f1);
            File f2 = new File("fondo/icono_login.png");
            if (f2.exists()) imagenIconoCandado = ImageIO.read(f2);
        } catch (Exception e) {}
    }

    private void validarIngreso() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());
        if ((u.equalsIgnoreCase("admin") && p.equals("1234")) || (u.equalsIgnoreCase("Johanna") && p.equals("1234"))) {
            this.dispose();
            abrirSistema(u);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales Invalidas");
        }
    }

    private void abrirSistema(String u) {
        SwingUtilities.invokeLater(() -> {
            ControladorSIRMA c = new ControladorSIRMA();
            VentanaPrincipal v = new VentanaPrincipal(c);
            v.establecerUsuarioLogueado(u);
            v.setVisible(true);
        });
    }
}