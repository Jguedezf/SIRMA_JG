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
 *
 * DESCRIPCIÓN TÉCNICA:
 * Módulo de Acceso y Autenticación. Esta clase representa la puerta de entrada
 * al sistema, implementando una interfaz gráfica personalizada (Undecorated)
 * con controles de ventana propietarios y validación de credenciales.
 *
 * PRINCIPIOS DEL PARADIGMA ORIENTADO A OBJETOS (POO) APLICADOS:
 * 1. HERENCIA: La clase extiende de 'javax.swing.JFrame' para heredar el comportamiento de ventana.
 * 2. ENCAPSULAMIENTO: Los atributos de la UI se declaran 'private' para proteger el estado interno.
 * 3. POLIMORFISMO: Se sobrescribe el método 'paintComponent' para personalizar el renderizado gráfico.
 * 4. ABSTRACCIÓN: Se utilizan interfaces de eventos (MouseListener/MouseAdapter) para la interacción.
 * ============================================================================
 */
package vista;

import controlador.ControladorSIRMA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Clase Login: Gestiona la autenticación de usuarios y la inicialización del entorno.
 */
public class Login extends JFrame {

    // ========================================================================
    // 1. ATRIBUTOS (ESTADO DEL OBJETO - ENCAPSULAMIENTO)
    // ========================================================================

    // Componentes de la Interfaz (View)
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private BotonFuturista btnEntrar;

    // Recursos Gráficos (Assets)
    private Image imagenFondoLogin;
    private Image imagenIconoCandado;

    // Variables auxiliares para la lógica de desplazamiento (Drag & Drop)
    private int pX, pY;

    /**
     * CONSTRUCTOR: Inicializa la ventana de autenticación.
     * ALGORITMO DE INICIALIZACIÓN:
     * 1. Configuración de propiedades del contenedor (Frame).
     * 2. Carga de recursos externos (Imágenes).
     * 3. Composición de la interfaz (Layouts y Componentes).
     * 4. Asignación de controladores de eventos (Listeners).
     */
    public Login() {
        // --- FASE 1: CONFIGURACIÓN BASE (HERENCIA) ---
        setTitle("Acceso Seguro - SIRMA JG");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se elimina la decoración estándar del SO para un diseño personalizado.
        setUndecorated(true);
        setLayout(new BorderLayout());

        // --- FASE 2: GESTIÓN DE RECURSOS (I/O) ---
        cargarImagenes();

        // --- FASE 3: COMPOSICIÓN DE LA INTERFAZ ---

        // A. Barra Superior (Controles de Ventana)
        JPanel panelBarra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panelBarra.setOpaque(false);

        // Instanciación de componentes mediante métodos de fábrica
        JButton btnMinimizar = crearBotonMinimizarVectorial();
        JButton btnCerrar = crearBotonCierreVectorial();

        panelBarra.add(btnMinimizar);
        panelBarra.add(btnCerrar);
        add(panelBarra, BorderLayout.NORTH);

        // B. Panel Central (Contenedor Principal con Fondo)
        // USO DE CLASE ANÓNIMA para aplicar POLIMORFISMO en el pintado.
        JPanel panelCentral = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Lógica de renderizado condicional
                if (imagenFondoLogin != null) {
                    g.drawImage(imagenFondoLogin, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(35, 40, 45)); // Fallback color
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelCentral.setBorder(new EmptyBorder(20, 40, 40, 40));
        add(panelCentral, BorderLayout.CENTER);

        // --- FASE 4: LÓGICA DE INTERACCIÓN (EVENT LISTENER) ---
        // Implementación de funcionalidad "Arrastrar y Soltar" (Drag & Drop) para ventanas undecorated.
        MouseAdapter moverVentana = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Captura de coordenadas iniciales (ENTRADA)
                pX = e.getX();
                pY = e.getY();
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                // Cálculo de nueva posición (PROCESO) y actualización (SALIDA)
                int x = getLocation().x + e.getX() - pX;
                int y = getLocation().y + e.getY() - pY;
                setLocation(x, y);
            }
        };

        // Vinculación del listener a los paneles interactivos
        panelBarra.addMouseListener(moverVentana);
        panelBarra.addMouseMotionListener(moverVentana);
        panelCentral.addMouseListener(moverVentana);
        panelCentral.addMouseMotionListener(moverVentana);

        // --- FASE 5: AGREGADO DE COMPONENTES AL GRIDBAGLAYOUT ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Icono Central
        JLabel lblIcono = new JLabel();
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        if (imagenIconoCandado != null) {
            lblIcono.setIcon(new ImageIcon(imagenIconoCandado.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        }
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(lblIcono, gbc);

        // Título del Formulario
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        panelCentral.add(lblTitulo, gbc);

        // Inputs: Usuario
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 5, 10);
        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        panelCentral.add(lblUser, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 10, 10);
        txtUsuario = new JTextField();
        estilizarCampo(txtUsuario);
        panelCentral.add(txtUsuario, gbc);

        // Inputs: Contraseña
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 5, 10);
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Arial", Font.BOLD, 14));
        panelCentral.add(lblPass, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 10, 10);
        txtClave = new JPasswordField();
        estilizarCampo(txtClave);
        panelCentral.add(txtClave, gbc);

        // Botón de Acción Principal
        gbc.gridy++;
        gbc.insets = new Insets(30, 10, 10, 10);
        btnEntrar = new BotonFuturista("INGRESAR AL SISTEMA");
        btnEntrar.setBackground(new Color(60, 65, 70));
        btnEntrar.setPreferredSize(new Dimension(200, 45));
        panelCentral.add(btnEntrar, gbc);

        // Vinculación de la acción de validación
        btnEntrar.addActionListener(e -> validarIngreso());
    }

    /**
     * Crea un botón de cierre con dibujo vectorial personalizado (Gráficos 2D).
     * Aplica POLIMORFISMO al sobrescribir `paintComponent`.
     * @return JButton configurado.
     */
    private JButton crearBotonCierreVectorial() {
        JButton btnCerrar = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Feedback visual (Hover)
                if (getModel().isRollover()) {
                    g2.setColor(new Color(232, 17, 35)); // Rojo al pasar el mouse
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(Color.WHITE);
                } else {
                    g2.setColor(new Color(200, 200, 200)); // Color base
                }
                // Dibujo de la 'X'
                int size = 12;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine(x, y, x + size, y + size);
                g2.drawLine(x + size, y, x, y + size);
                g2.dispose();
            }
        };
        configurarBotonVentana(btnCerrar);
        btnCerrar.addActionListener(e -> System.exit(0)); // Salida del sistema
        return btnCerrar;
    }

    /**
     * Crea un botón de minimizar con dibujo vectorial personalizado.
     * @return JButton configurado.
     */
    private JButton crearBotonMinimizarVectorial() {
        JButton btnMin = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(100, 100, 100));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(Color.WHITE);
                } else {
                    g2.setColor(new Color(200, 200, 200));
                }
                // Dibujo de la línea '_'
                int size = 12;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine(x, y + size, x + size, y + size);
                g2.dispose();
            }
        };
        configurarBotonVentana(btnMin);
        // Acción: Minimizar a la barra de tareas
        btnMin.addActionListener(e -> setState(Frame.ICONIFIED));
        return btnMin;
    }

    // Método helper para configuración común de botones de ventana
    private void configurarBotonVentana(JButton btn) {
        btn.setPreferredSize(new Dimension(45, 30));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Método helper para estilización de campos de texto
    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 16));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 120, 215), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campo.setPreferredSize(new Dimension(200, 35));
    }

    private void cargarImagenes() {
        try {
            File f1 = new File("fondo/fondo_login.png");
            if (f1.exists()) imagenFondoLogin = ImageIO.read(f1);
            File f2 = new File("fondo/icono_login.png");
            if (f2.exists()) imagenIconoCandado = ImageIO.read(f2);
        } catch (Exception e) {
            System.err.println("Error de I/O cargando recursos gráficos.");
        }
    }

    /**
     * Lógica de Validación de Credenciales.
     * ALGORITMO (ENTRADA - PROCESO - SALIDA):
     * 1. Entrada: Captura de datos de los campos de texto.
     * 2. Proceso: Comparación contra credenciales almacenadas (Hardcoded para prototipo).
     * 3. Salida: Transición a la Ventana Principal o mensaje de error.
     */
    private void validarIngreso() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());

        // Validación lógica
        if ((u.equalsIgnoreCase("admin") && p.equals("1234")) ||
                (u.equalsIgnoreCase("Johanna") && p.equals("1234"))) {
            this.dispose(); // Cierre de ventana actual
            abrirSistema(u); // Transición de flujo
        } else {
            // Feedback de error
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para la instanciación dinámica de la ventana principal.
     * Utiliza reflexión para desacoplar dependencias directas en tiempo de compilación.
     */
    private void abrirSistema(String u) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Instanciación del Controlador (Lógica)
                Class<?> ctrlClass = Class.forName("controlador.ControladorSIRMA");
                Object ctrl = ctrlClass.getDeclaredConstructor().newInstance();

                // Instanciación de la Vista Principal (Inyección de Controlador)
                Class<?> winClass = Class.forName("vista.VentanaPrincipal");
                JFrame v = (JFrame) winClass.getDeclaredConstructor(ctrlClass).newInstance(ctrl);

                // Configuración de sesión
                winClass.getMethod("establecerUsuarioLogueado", String.class).invoke(v, u);
                v.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}