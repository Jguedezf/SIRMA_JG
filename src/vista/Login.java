/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      Login.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista que implementa la ventana de autenticación.
 * Utiliza un JFrame sin decoración (`undecorated`) y pintura personalizada
 * (`paintComponent`) para lograr una interfaz de usuario moderna y única,
 * actuando como el punto de entrada visual a la aplicación.
 * ============================================================================
 */
package vista;

import controlador.ControladorSIRMA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Clase Login que gestiona la interfaz de inicio de sesión del sistema.
 * PRINCIPIO POO: Herencia - Extiende de {@link JFrame} para adquirir las
 * características y comportamientos de una ventana de aplicación.
 */
public class Login extends JFrame {

    // --- ATRIBUTOS ---
    // PRINCIPIO POO: Encapsulamiento - Los componentes de la UI son privados
    // para que su gestión se realice exclusivamente dentro de esta clase.
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private BotonFuturista btnEntrar;
    private Image imagenFondoLogin;
    private Image imagenIconoCandado;

    /**
     * Constructor de la ventana de Login.
     * Se encarga de inicializar, componer y estilizar todos los elementos gráficos.
     * PRINCIPIO POO: Composición - La interfaz se construye anidando múltiples
     * componentes (paneles, etiquetas, botones) para formar una estructura compleja.
     */
    public Login() {
        // --- 1. CONFIGURACIÓN DEL FRAME ---
        setTitle("Acceso Seguro - SIRMA JG");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Crea una ventana sin la barra de título estándar del SO.
        setLayout(new BorderLayout());

        // --- 2. CARGA DE RECURSOS GRÁFICOS ---
        cargarImagenes();

        // --- 3. BARRA SUPERIOR PERSONALIZADA ---
        JPanel panelBarra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panelBarra.setOpaque(false); // Transparente para mostrar el fondo.

        // Botón de cierre con pintado vectorial personalizado.
        JButton btnCerrar = crearBotonCierreVectorial();
        panelBarra.add(btnCerrar);
        add(panelBarra, BorderLayout.NORTH);

        // --- 4. PANEL CENTRAL CON FONDO PERSONALIZADO ---
        // PRINCIPIO POO: Polimorfismo (Sobrescritura) - Se usa una clase anónima que
        // hereda de JPanel para sobreescribir `paintComponent` y dibujar un fondo
        // con imagen y un filtro de oscurecimiento.
        JPanel panelCentral = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondoLogin != null) {
                    g.drawImage(imagenFondoLogin, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(0, 0, 0, 180)); // Capa oscura semitransparente.
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(new Color(35, 40, 45)); // Color de respaldo.
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelCentral.setBorder(new EmptyBorder(20, 40, 40, 40));
        add(panelCentral, BorderLayout.CENTER);

        // --- 5. COMPOSICIÓN DE ELEMENTOS EN EL PANEL CENTRAL ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Icono de seguridad
        JLabel lblIcono = new JLabel();
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        if (imagenIconoCandado != null) {
            lblIcono.setIcon(new ImageIcon(imagenIconoCandado.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        }
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(lblIcono, gbc);

        // Título
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        panelCentral.add(lblTitulo, gbc);

        // Campo de Usuario
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 5, 10);
        panelCentral.add(new JLabel("Usuario:") {{ setForeground(Color.LIGHT_GRAY); }}, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 10, 10);
        txtUsuario = new JTextField();
        estilizarCampo(txtUsuario);
        panelCentral.add(txtUsuario, gbc);

        // Campo de Contraseña
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 5, 10);
        panelCentral.add(new JLabel("Contraseña:") {{ setForeground(Color.LIGHT_GRAY); }}, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 10, 10);
        txtClave = new JPasswordField();
        estilizarCampo(txtClave);
        panelCentral.add(txtClave, gbc);

        // Botón de Ingreso
        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        btnEntrar = new BotonFuturista("INGRESAR AL SISTEMA");
        btnEntrar.setBackground(new Color(60, 65, 70));
        btnEntrar.setPreferredSize(new Dimension(200, 45));
        panelCentral.add(btnEntrar, gbc);

        // --- 6. VINCULACIÓN DE EVENTOS ---
        btnEntrar.addActionListener(e -> validarIngreso());
    }

    /**
     * Método de fábrica para crear el botón de cierre con renderizado vectorial.
     * @return Un JButton personalizado para cerrar la ventana.
     */
    private JButton crearBotonCierreVectorial() {
        JButton btnCerrar = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(232, 17, 35));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(Color.WHITE);
                } else {
                    g2.setColor(new Color(200, 200, 200));
                }
                int size = 12;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine(x, y, x + size, y + size);
                g2.drawLine(x + size, y, x, y + size);
                g2.dispose();
            }
        };
        btnCerrar.setPreferredSize(new Dimension(45, 30));
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> System.exit(0));
        return btnCerrar;
    }

    /**
     * Aplica un estilo visual consistente a los campos de texto.
     * @param campo El JTextField o JPasswordField a estilizar.
     */
    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campo.setPreferredSize(new Dimension(200, 35));
    }

    /**
     * Carga las imágenes de fondo e icono desde los archivos.
     * Maneja excepciones de I/O de forma segura.
     */
    private void cargarImagenes() {
        try {
            File f1 = new File("fondo/fondo_login.png");
            if (f1.exists()) imagenFondoLogin = ImageIO.read(f1);
            File f2 = new File("fondo/icono_login.png");
            if (f2.exists()) imagenIconoCandado = ImageIO.read(f2);
        } catch (Exception e) {
            System.err.println("Error de I/O: No se pudieron cargar los assets gráficos del login.");
        }
    }

    /**
     * Valida las credenciales ingresadas por el usuario.
     * Contiene la lógica de negocio para la autenticación.
     */
    private void validarIngreso() {
        // ENTRADA: Se obtienen los datos de los campos de texto.
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());

        // PROCESO: Validación de credenciales.
        // Nota para la defensa: En un sistema real, esto se haría contra una base de datos
        // y las contraseñas estarían encriptadas (hashed). Aquí están "hardcoded"
        // por simplicidad para este prototipo académico.
        if ((u.equalsIgnoreCase("admin") && p.equals("1234")) ||
                (u.equalsIgnoreCase("Johanna") && p.equals("1234"))) {

            // SALIDA (Éxito):
            this.dispose(); // Cierra y libera los recursos de la ventana de login.
            abrirSistema(u); // Inicia la transición a la ventana principal.

        } else {
            // SALIDA (Error): Muestra un mensaje de error.
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Inicia la ventana principal de la aplicación después de una autenticación exitosa.
     * Este método se encarga de instanciar el Controlador y la Vista Principal,
     * inyectando la dependencia del controlador en la vista (Patrón MVC).
     * @param usuarioLogueado El nombre del usuario que ha iniciado sesión.
     */
    private void abrirSistema(String usuarioLogueado) {
        // PRINCIPIO DE CONCURRENCIA: Se asegura de que la creación de la nueva
        // ventana se ejecute en el Hilo de Despacho de Eventos (EDT) de Swing,
        // que es la práctica correcta y segura para manejar interfaces gráficas en Java.
        SwingUtilities.invokeLater(() -> {
            ControladorSIRMA c = new ControladorSIRMA();
            VentanaPrincipal v = new VentanaPrincipal(c); // Inyección de dependencia (Controlador -> Vista)
            v.establecerUsuarioLogueado(usuarioLogueado);
            v.setVisible(true);
        });
    }
}