/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelGestionVehiculos.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Módulo de Vista para la administración de la entidad 'Vehiculo'. Implementa
 * una interfaz de tipo Maestro-Detalle para las operaciones CRUD, combinando
 * un formulario de entrada de datos, una tabla de visualización y una botonera
 * de acciones con carga dinámica de recursos gráficos.
 * ============================================================================
 */
package vista;

import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Panel que proporciona la interfaz gráfica para las operaciones CRUD sobre la flota vehicular.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel}.
 */
public class PanelGestionVehiculos extends JPanel {

    // --- ATRIBUTOS DE UI (Públicos para acceso desde VentanaPrincipal) ---
    public JTextField txtPlaca, txtMarca, txtModelo, txtAnio, txtColor;
    public JTextField txtNombrePropietario, txtCedulaPropietario, txtTelefonoPropietario, txtDireccion;
    public JTable tablaVehiculos;
    public DefaultTableModel modeloTabla;
    public BotonFuturista btnGuardar, btnLimpiar, btnEliminar;
    public Vehiculo vehiculoEnEdicion = null;

    // --- ATRIBUTOS INTERNOS ---
    private JLabel lblLogoMarca;
    private int filaHover = -1;
    private Image imagenFondo;

    /**
     * Constructor del panel de gestión de vehículos.
     * Construye la compleja interfaz gráfica anidando múltiples paneles y layouts.
     * PRINCIPIO POO: Composición - La UI se ensambla a partir de componentes más simples.
     */
    public PanelGestionVehiculos() {
        try {
            imagenFondo = ImageIO.read(new File("fondo/fondo.png"));
        } catch(Exception e){ e.printStackTrace(); }

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 20, 10, 20));

        // --- 1. ENCABEZADO ---
        JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pNorte.setOpaque(false);
        JLabel lblTitulo = new JLabel("Registro de Vehículos para Mantenimiento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        pNorte.add(lblTitulo);
        add(pNorte, BorderLayout.NORTH);

        // --- 2. ÁREA CENTRAL (Formulario + Tabla) ---
        JPanel pCentro = new JPanel(new BorderLayout(0, 15));
        pCentro.setOpaque(false);
        JPanel pFormularioContenedor = new JPanel(new BorderLayout(10, 0));
        pFormularioContenedor.setOpaque(false);

        JPanel pInputs = new JPanel(new GridBagLayout());
        pInputs.setOpaque(false);
        TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Ficha Técnica del Vehículo");
        borde.setTitleColor(Color.WHITE);
        borde.setTitleFont(new Font("Arial", Font.BOLD, 14));
        pInputs.setBorder(borde);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        addCampo(pInputs, "Placa:", txtPlaca = new JTextField(12), 0, 0, gbc);
        addCampo(pInputs, "Marca:", txtMarca = new JTextField(12), 2, 0, gbc);
        txtMarca.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) { actualizarLogoMarca(txtMarca.getText()); }
        });

        addCampo(pInputs, "Modelo:", txtModelo = new JTextField(12), 0, 1, gbc);
        addCampo(pInputs, "Año:", txtAnio = new JTextField(12), 2, 1, gbc);
        addCampo(pInputs, "Color:", txtColor = new JTextField(12), 0, 2, gbc);
        JSeparator sep = new JSeparator(); gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=4; pInputs.add(sep, gbc);
        gbc.gridwidth=1; gbc.gridx = 0; gbc.gridy = 4;
        pInputs.add(new JLabel("Propietario:") {{ setForeground(Color.WHITE); setFont(new Font("Arial", Font.BOLD, 14)); }}, gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtNombrePropietario = new JTextField(); estilizarCampo(txtNombrePropietario);
        pInputs.add(txtNombrePropietario, gbc);
        gbc.gridwidth = 1;
        addCampo(pInputs, "Cédula:", txtCedulaPropietario = new JTextField(12), 0, 5, gbc);
        addCampo(pInputs, "Teléfono:", txtTelefonoPropietario = new JTextField(12), 2, 5, gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        pInputs.add(new JLabel("Dirección:") {{ setForeground(Color.WHITE); setFont(new Font("Arial", Font.BOLD, 14)); }}, gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtDireccion = new JTextField(); estilizarCampo(txtDireccion);
        pInputs.add(txtDireccion, gbc);
        pFormularioContenedor.add(pInputs, BorderLayout.CENTER);

        JPanel pLateral = new JPanel();
        pLateral.setOpaque(false);
        pLateral.setLayout(new BoxLayout(pLateral, BoxLayout.Y_AXIS));
        pLateral.setBorder(new EmptyBorder(10, 10, 0, 0));
        lblLogoMarca = new JLabel();
        lblLogoMarca.setPreferredSize(new Dimension(100, 100));
        lblLogoMarca.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGuardar = crearBoton("GUARDAR", "fondo/icono_guardar.png");
        btnLimpiar = crearBoton("LIMPIAR", "fondo/limpiar_formulario.png");
        pLateral.add(lblLogoMarca);
        pLateral.add(Box.createVerticalStrut(20));
        pLateral.add(btnGuardar);
        pLateral.add(Box.createVerticalStrut(15));
        pLateral.add(btnLimpiar);
        pFormularioContenedor.add(pLateral, BorderLayout.EAST);
        pCentro.add(pFormularioContenedor, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"Placa", "Marca", "Modelo", "Año", "Propietario"}, 0) {
            public boolean isCellEditable(int r, int c){return false;}
        };

        tablaVehiculos = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row == filaHover ? new Color(200, 230, 255) : Color.WHITE);
                }
                return c;
            }
        };

        tablaVehiculos.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int nuevaFila = tablaVehiculos.rowAtPoint(e.getPoint());
                if (nuevaFila != filaHover) { filaHover = nuevaFila; tablaVehiculos.repaint(); }
            }
        });

        tablaVehiculos.setRowHeight(30);
        tablaVehiculos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaVehiculos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaVehiculos.getColumnModel().getColumn(1).setCellRenderer(new RenderizadorMarca());
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaVehiculos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tablaVehiculos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        JScrollPane scroll = new JScrollPane(tablaVehiculos);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setPreferredSize(new Dimension(0, 155));
        pCentro.add(scroll, BorderLayout.CENTER);
        add(pCentro, BorderLayout.CENTER);

        JPanel pSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pSur.setOpaque(false);
        btnEliminar = new BotonFuturista("Eliminar Vehículo Seleccionado");
        btnEliminar.setBackground(new Color(150, 50, 50));
        btnEliminar.setIcon(cargarIconoBtn("fondo/icono_eliminar.png", 24));
        btnEliminar.setPreferredSize(new Dimension(280, 45));
        btnEliminar.setIconTextGap(15);
        btnEliminar.setEnabled(false);
        pSur.add(btnEliminar);
        add(pSur, BorderLayout.SOUTH);
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo de imagen personalizado.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(new Color(45, 50, 55));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // =========================================================================
    // MÉTODOS DE FÁBRICA Y UTILIDAD (HELPERS)
    // =========================================================================

    private void addCampo(JPanel p, String lblText, JTextField txt, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        JLabel l = new JLabel(lblText);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        l.setForeground(Color.WHITE);
        p.add(l, gbc);

        gbc.gridx = x + 1;
        estilizarCampo(txt);
        p.add(txt, gbc);
    }

    private void estilizarCampo(JTextField txt) {
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.BLACK);
        txt.setCaretColor(Color.BLACK);
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100)),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        txt.setPreferredSize(new Dimension(150, 30));
    }

    private BotonFuturista crearBoton(String t, String r) {
        BotonFuturista b = new BotonFuturista(t);
        Dimension buttonSize = new Dimension(230, 65);
        b.setPreferredSize(buttonSize);
        b.setMinimumSize(buttonSize);
        b.setMaximumSize(buttonSize);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setIconTextGap(15);
        try{
            File f=new File(r);
            if(f.exists()) {
                b.setIcon(new ImageIcon(new ImageIcon(r).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            }
        }catch(Exception e){ e.printStackTrace(); }
        return b;
    }

    private void actualizarLogoMarca(String marca) {
        if (marca == null || marca.isEmpty()) { lblLogoMarca.setIcon(null); return; }
        String ruta = "fondo/" + marca.toLowerCase().trim() + ".png";
        cargarImagenEnLabel(lblLogoMarca, ruta, 90, 90);
    }

    private void cargarImagenEnLabel(JLabel l, String ruta, int w, int h) {
        try {
            File f = new File(ruta);
            if (f.exists()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
                l.setIcon(icon);
            } else {
                l.setIcon(null);
            }
        } catch(Exception e) { l.setIcon(null); e.printStackTrace(); }
    }

    private ImageIcon cargarIconoBtn(String ruta, int size) {
        try {
            return new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Resetea todos los campos del formulario a su estado inicial.
     */
    public void limpiarFormulario() {
        txtPlaca.setText(""); txtPlaca.setEditable(true);
        txtMarca.setText(""); txtModelo.setText(""); txtAnio.setText(""); txtColor.setText("");
        txtNombrePropietario.setText(""); txtCedulaPropietario.setText(""); txtTelefonoPropietario.setText(""); txtDireccion.setText("");
        lblLogoMarca.setIcon(null);
        vehiculoEnEdicion = null;
        if(btnGuardar instanceof JButton) btnGuardar.setText("GUARDAR");
        btnEliminar.setEnabled(false);
        tablaVehiculos.clearSelection();
    }
}