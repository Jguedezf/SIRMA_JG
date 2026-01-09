/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelGestionMecanicos.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista que implementa la interfaz para la gestión CRUD
 * (Crear, Leer, Actualizar, Eliminar) del personal técnico. Presenta una
 * maquetación asimétrica que combina un formulario de entrada de datos,
 * una botonera de acciones y una tabla de visualización con efecto 'hover'.
 * ============================================================================
 */
package vista;

import modelo.Mecanico;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * IDENTIFICACIÓN DE CLASE: PanelGestionMecanicos
 * Panel para la gestión operativa de la entidad Mecánico.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel} para reutilización de componentes gráficos.
 */
public class PanelGestionMecanicos extends JPanel {

    // -------------------------------------------------------------------------
    // IDENTIFICACIÓN DE ATRIBUTOS
    // -------------------------------------------------------------------------
    // Componentes de Entrada (Inputs)
    public JTextField txtNombre;
    public JTextField txtEspecialidad;

    // Componentes de Visualización (Outputs)
    public JTable tablaMecanicos;
    public DefaultTableModel modeloTabla;

    // Componentes de Control (Acciones)
    public BotonFuturista btnGuardar, btnLimpiar, btnEliminar;

    // Atributos de Estado Interno (Encapsulamiento)
    public Mecanico mecanicoEnEdicion = null;
    private int filaHover = -1;
    private Image backgroundImage;

    /**
     * MÉTODO CONSTRUCTOR
     * Inicializa la interfaz, carga recursos gráficos y configura listeners.
     * PRINCIPIO POO: Composición - Construye la UI agregando paneles y componentes anidados.
     */
    public PanelGestionMecanicos() {
        // PROCESO: Carga de recursos (Imagen de fondo)
        try {
            File f = new File("fondo/fondo2.png");
            if (f.exists()) {
                backgroundImage = ImageIO.read(f);
            } else {
                backgroundImage = ImageIO.read(new File("fondo/fondo.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            setBackground(new Color(45, 50, 55)); // Fallback color
        }

        // Configuración del Layout Principal
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 40, 20, 40));

        // --- SECCIÓN NORTE: TÍTULO E ICONO ---
        // INSTANCIACIÓN: Panel de Encabezado
        JPanel pNorte = new JPanel(new BorderLayout());
        pNorte.setOpaque(false);

        JLabel lblTitulo = new JLabel("Gestión de Personal Técnico");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(0, 180, 255));

        JLabel lblIcono = new JLabel();
        cargarIconoEnLabel(lblIcono, "fondo/mecanico.png", 100);

        pNorte.add(lblTitulo, BorderLayout.WEST);
        pNorte.add(lblIcono, BorderLayout.EAST);
        add(pNorte, BorderLayout.NORTH);

        // --- SECCIÓN CENTRAL: FORMULARIO Y TABLA ---
        JPanel pCentro = new JPanel(new BorderLayout(20, 20));
        pCentro.setOpaque(false);

        // Sub-panel Superior (Formulario y Botones)
        JPanel pSup = new JPanel(new BorderLayout(20, 0));
        pSup.setOpaque(false);

        // INSTANCIACIÓN: Formulario (GridBagLayout para alineación precisa)
        JPanel pForm = new JPanel(new GridBagLayout());
        pForm.setOpaque(false);
        pForm.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Datos del Especialista", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 12) , Color.WHITE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campo Nombre
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        pForm.add(new JLabel("Nombre Completo:") {{ setFont(new Font("Arial", Font.BOLD, 16)); setForeground(Color.WHITE); }}, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNombre = new JTextField();
        style(txtNombre);
        // CORRECCIÓN CRÍTICA: Aseguramos que nazca habilitado y con foco
        txtNombre.setEditable(true);
        txtNombre.setFocusable(true);
        pForm.add(txtNombre, gbc);

        // Campo Especialidad
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        pForm.add(new JLabel("Especialidad:") {{ setFont(new Font("Arial", Font.BOLD, 16)); setForeground(Color.WHITE); }}, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtEspecialidad = new JTextField();
        style(txtEspecialidad);
        pForm.add(txtEspecialidad, gbc);

        pSup.add(pForm, BorderLayout.CENTER);

        // INSTANCIACIÓN: Botonera Lateral
        JPanel pBot = new JPanel(new GridLayout(3, 1, 0, 20));
        pBot.setOpaque(false);

        btnGuardar = btn("Registrar Mecánico", "fondo/icono_guardar.png");
        btnLimpiar = btn("Limpiar Formulario", "fondo/limpiar_formulario.png");

        // Configuración inicial del botón Eliminar
        btnEliminar = btn("Eliminar", "fondo/icono_eliminar.png");
        btnEliminar.setBackgroundColor(new Color(80, 85, 90)); // VALIDACIÓN: Estado inicial Gris (Inactivo)
        btnEliminar.setEnabled(false);

        pBot.add(btnGuardar);
        pBot.add(btnLimpiar);
        pBot.add(btnEliminar);
        pSup.add(pBot, BorderLayout.EAST);
        pCentro.add(pSup, BorderLayout.NORTH);

        // INSTANCIACIÓN: Tabla de Datos
        // PRINCIPIO POO: Polimorfismo (Clase Anónima para desactivar edición)
        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Especialidad"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        // Personalización del Renderizado (Efecto Hover)
        tablaMecanicos = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int rw, int c) {
                Component cp = super.prepareRenderer(r, rw, c);
                if (!isRowSelected(rw)) {
                    cp.setBackground(rw == filaHover ? new Color(230, 240, 255) : Color.WHITE);
                }
                return cp;
            }
        };

        // PROCESO: Captura de Eventos de Mouse (Hover)
        tablaMecanicos.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                int r = tablaMecanicos.rowAtPoint(e.getPoint());
                if (r != filaHover) {
                    filaHover = r;
                    tablaMecanicos.repaint();
                }
            }
        });

        // ====================================================================
        // PROCESO: LISTENER DE SELECCIÓN (FEEDBACK VISUAL BOTÓN ELIMINAR)
        // ====================================================================
        tablaMecanicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean haySeleccion = tablaMecanicos.getSelectedRow() != -1;
                btnEliminar.setEnabled(haySeleccion);

                // VALIDACIÓN VISUAL: Cambio de color semántico
                if (haySeleccion) {
                    btnEliminar.setBackgroundColor(new Color(220, 20, 60)); // Rojo Alerta (Activo)
                } else {
                    btnEliminar.setBackgroundColor(new Color(80, 85, 90));  // Gris Neutro (Inactivo)
                }
            }
        });
        // ====================================================================

        // Estilización de la Tabla
        tablaMecanicos.setRowHeight(35);
        tablaMecanicos.setFont(new Font("Arial", Font.BOLD, 14));

        JTableHeader header = tablaMecanicos.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(255, 204, 0)); // Amarillo Corporativo
        header.setForeground(Color.BLACK);
        header.setOpaque(true);

        JScrollPane sc = new JScrollPane(tablaMecanicos);
        sc.getViewport().setBackground(Color.WHITE);
        pCentro.add(sc, BorderLayout.CENTER);
        add(pCentro, BorderLayout.CENTER);
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo personalizado.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        // Capa oscura para mejorar legibilidad (Overlay)
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * MÉTODO: limpiarFormulario
     * Reinicia los campos de texto y el estado de los botones.
     * PROCESO: Restaura el estado inicial de la vista.
     */
    public void limpiarFormulario() {
        txtNombre.setText("");
        // CORRECCIÓN 2: Forzamos el desbloqueo explícito del campo
        txtNombre.setEditable(true);
        txtNombre.setBackground(Color.WHITE); // Asegura visualmente que está activo
        txtEspecialidad.setText("");

        mecanicoEnEdicion = null;
        btnGuardar.setText("Registrar Mecánico");

        // Resetear estado visual del botón Eliminar
        btnEliminar.setEnabled(false);
        btnEliminar.setBackgroundColor(new Color(80, 85, 90));

        tablaMecanicos.clearSelection();
    }

    // --- MÉTODOS PRIVADOS DE ESTILIZACIÓN (HELPERS) ---

    private void style(JTextField c) {
        c.setPreferredSize(new Dimension(300, 40));
        c.setFont(new Font("Arial", Font.PLAIN, 16));
        c.setBorder(BorderFactory.createLineBorder(new Color(0,120,215),1));
    }

    private BotonFuturista btn(String t, String r) {
        BotonFuturista b = new BotonFuturista(t);
        b.setPreferredSize(new Dimension(250, 55));
        try {
            File f = new File(r);
            if(f.exists()) b.setIcon(new ImageIcon(new ImageIcon(r).getImage().getScaledInstance(28,28,4)));
        } catch(Exception e) { e.printStackTrace(); }
        return b;
    }

    private void cargarIconoEnLabel(JLabel l, String r, int s) {
        try {
            File f = new File(r);
            if(f.exists()) l.setIcon(new ImageIcon(new ImageIcon(r).getImage().getScaledInstance(s,s,4)));
        } catch(Exception e) { e.printStackTrace(); }
    }
}