/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelBusquedaInteligente.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista que implementa la interfaz de consultas avanzadas.
 * Permite al usuario filtrar las órdenes de servicio por múltiples criterios
 * (texto, estado, rango de fechas) e interactúa directamente con el Controlador
 * para obtener y mostrar los resultados.
 * ============================================================================
 */
package vista;

import controlador.ControladorSIRMA;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;

/**
 * Panel que contiene el formulario de búsqueda avanzada y la tabla de resultados.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel}.
 * PRINCIPIO POO: Composición - Se construye anidando múltiples paneles y componentes.
 */
public class PanelBusquedaInteligente extends JPanel {

    // --- ATRIBUTOS DE UI (Públicos para acceso desde VentanaPrincipal) ---
    public JTextField txtBusqueda;
    public JComboBox<String> cmbEstado;
    public JDateChooser dccDesde, dccHasta;
    public JTable tablaResultados;
    public DefaultTableModel modeloTabla;
    public JLabel lblTotalResultados;
    public JButton btnBuscar, btnLimpiar;
    public BotonFuturista btnAccionEditar, btnAccionEliminar, btnAccionNuevaOrden;

    // --- ATRIBUTOS INTERNOS ---
    private Image imagenFondo;
    // La referencia al controlador se elimina como atributo ya que no se usa internamente.

    /**
     * Constructor del panel de búsqueda.
     * Recibe una instancia del controlador, aunque no la utiliza internamente,
     * la comunicación se gestiona desde la clase contenedora (VentanaPrincipal).
     *
     * @param controlador La instancia del controlador principal de la aplicación.
     */
    public PanelBusquedaInteligente(ControladorSIRMA controlador) {
        // Carga de Imagen de Fondo.
        try {
            File f = new File("fondo/fondo.png");
            if(f.exists()) imagenFondo = ImageIO.read(f);
        } catch(Exception e){ e.printStackTrace(); }

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 30, 10, 30));

        // --- 1. SECCIÓN NORTE: FORMULARIO DE FILTROS ---
        JPanel panelNorte = new JPanel(new GridBagLayout());
        panelNorte.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel lblTitulo = new JLabel("Búsqueda Inteligente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        panelNorte.add(lblTitulo, gbc);

        // Fila 1: Buscador principal de texto.
        gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel pBuscador = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        pBuscador.setOpaque(false);
        pBuscador.add(crearLabel("Placa / Orden:", SwingConstants.RIGHT));
        txtBusqueda = new JTextField(); estilizarCampo(txtBusqueda);
        txtBusqueda.setFont(new Font("Arial", Font.BOLD, 20));
        txtBusqueda.setPreferredSize(new Dimension(250, 45));
        pBuscador.add(txtBusqueda);
        btnBuscar = crearBotonIcono("Buscar", "fondo/icono_lupa.png");
        btnLimpiar = crearBotonIcono("Limpiar", "fondo/limpiar_formulario.png");
        pBuscador.add(btnBuscar);
        pBuscador.add(btnLimpiar);
        panelNorte.add(pBuscador, gbc);

        // Fila 2: Filtros secundarios (Estado y Fechas).
        gbc.gridy = 2;
        JPanel pSecundario = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pSecundario.setOpaque(false);
        pSecundario.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Filtro de Estado
        pSecundario.add(crearLabel("Estado:", SwingConstants.RIGHT));
        cmbEstado = new JComboBox<>(new String[]{"Todos", "Pendiente", "En Proceso", "Finalizado"});
        cmbEstado.setPreferredSize(new Dimension(140, 35));
        pSecundario.add(cmbEstado);

        // Filtro de Fechas con JCalendar
        pSecundario.add(crearLabel("Desde:", SwingConstants.RIGHT));
        dccDesde = new JDateChooser(); estilizarCalendario(dccDesde);
        pSecundario.add(dccDesde);
        pSecundario.add(crearLabel("Hasta:", SwingConstants.RIGHT));
        dccHasta = new JDateChooser(); estilizarCalendario(dccHasta);
        pSecundario.add(dccHasta);

        panelNorte.add(pSecundario, gbc);
        add(panelNorte, BorderLayout.NORTH);

        // --- 2. SECCIÓN CENTRAL: TABLA DE RESULTADOS ---
        String[] cols = {"ID Orden", "Fecha", "Placa", "Marca", "Servicio", "Estado", "Total ($)"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setRowHeight(30);
        tablaResultados.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaResultados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Aplicación de renderizadores personalizados.
        tablaResultados.getColumnModel().getColumn(3).setCellRenderer(new RenderizadorMarca());
        tablaResultados.getColumnModel().getColumn(5).setCellRenderer(new RenderizadorEstadoOrden());
        configurarAlineacionTabla();

        JScrollPane scroll = new JScrollPane(tablaResultados);
        scroll.getViewport().setBackground(Color.WHITE);
        TitledBorder bordeTitulo = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Resultados de la Búsqueda");
        bordeTitulo.setTitleColor(Color.WHITE);
        bordeTitulo.setTitleFont(new Font("Arial", Font.BOLD, 14));
        scroll.setBorder(bordeTitulo);
        add(scroll, BorderLayout.CENTER);

        // --- 3. SECCIÓN SUR: ACCIONES CONTEXTUALES Y TOTALES ---
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);
        lblTotalResultados = new JLabel("Listo...", SwingConstants.RIGHT);
        lblTotalResultados.setForeground(Color.CYAN);
        lblTotalResultados.setBorder(new EmptyBorder(5,0,5,10));
        panelSur.add(lblTotalResultados, BorderLayout.NORTH);

        JPanel pAcc = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pAcc.setOpaque(false);
        Dimension d = new Dimension(280, 50);
        btnAccionEditar = crearBtnAcc("Editar / Ver Detalle", "fondo/icono_editar.png", null, d);
        btnAccionEliminar = crearBtnAcc("Eliminar Orden", "fondo/icono_eliminar.png", new Color(150,50,50), d);
        btnAccionNuevaOrden = crearBtnAcc("Nueva Orden de Servicio", "fondo/icono_agregar.png", new Color(40,167,69), d);
        pAcc.add(btnAccionEditar); pAcc.add(btnAccionEliminar); pAcc.add(btnAccionNuevaOrden);
        panelSur.add(pAcc, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        // PATRÓN OBSERVER: La vista "observa" cambios en la selección de la tabla.
        tablaResultados.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionada = tablaResultados.getSelectedRow() != -1;
            btnAccionEditar.setEnabled(seleccionada);
            btnAccionEliminar.setEnabled(seleccionada);
            btnAccionNuevaOrden.setEnabled(seleccionada);
        });
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo de imagen personalizado.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura).
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

    // ============================================================================
    // MÉTODOS DE FÁBRICA Y UTILIDAD (HELPERS)
    // ============================================================================

    /**
     * Configura la alineación de las columnas de la tabla de resultados.
     */
    private void configurarAlineacionTabla() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaResultados.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        tablaResultados.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Fecha
        tablaResultados.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Placa

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tablaResultados.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);  // Total
    }

    /**
     * Método de fábrica para crear un JButton con solo un ícono y un tooltip.
     * @param tooltip Texto de ayuda que aparece al pasar el ratón.
     * @param ruta Ruta del archivo de imagen del ícono.
     * @return Un JButton estilizado.
     */
    private JButton crearBotonIcono(String tooltip, String ruta) {
        JButton b = new JButton();
        b.setToolTipText(tooltip);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            File f = new File(ruta);
            if (f.exists()) {
                b.setIcon(new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {}
        return b;
    }

    /**
     * Aplica un estilo visual al componente JDateChooser.
     * @param d El JDateChooser a estilizar.
     */
    private void estilizarCalendario(JDateChooser d) {
        d.setPreferredSize(new Dimension(130, 35));
        d.setDateFormatString("dd-MM-yyyy");
        JTextFieldDateEditor e = (JTextFieldDateEditor) d.getDateEditor();
        e.setBackground(Color.WHITE);
        e.setForeground(Color.BLACK);
        e.setCaretColor(Color.BLACK);
        e.setFont(new Font("Arial", Font.BOLD, 14));
        e.setHorizontalAlignment(JTextField.CENTER);
        e.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));
        e.setEditable(false);
        for (Component c : d.getComponents()) {
            if (c instanceof JButton) {
                c.setBackground(Color.WHITE);
                c.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
    }

    /**
     * Método de fábrica para crear los botones de acción contextuales del panel sur.
     */
    private BotonFuturista crearBtnAcc(String t, String r, Color c, Dimension d) {
        BotonFuturista b = new BotonFuturista(t);
        b.setPreferredSize(d);
        if (c != null) b.setBackground(c);
        try {
            File f = new File(r);
            if (f.exists()) {
                b.setIcon(new ImageIcon(new ImageIcon(r).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
                b.setHorizontalTextPosition(SwingConstants.RIGHT);
                b.setIconTextGap(10);
            }
        } catch (Exception e) {}
        b.setEnabled(false);
        return b;
    }

    /**
     * Carga y escala un ícono para un JLabel.
     */
    private void cargarIconoEnLabel(JLabel l, String r, int s) {
        try {
            File f = new File(r);
            if (f.exists()) {
                l.setIcon(new ImageIcon(new ImageIcon(r).getImage().getScaledInstance(s, s, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {}
    }

    /**
     * Método de fábrica para crear un JLabel estandarizado.
     */
    private JLabel crearLabel(String t, int alig) {
        JLabel l = new JLabel(t, alig);
        l.setForeground(Color.LIGHT_GRAY);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        return l;
    }

    /**
     * Aplica un estilo visual a un JTextField.
     */
    private void estilizarCampo(JTextField c) {
        c.setBackground(Color.WHITE);
        c.setForeground(Color.BLACK);
        c.setCaretColor(Color.BLACK);
        c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    // --- Métodos públicos para obtener los valores de los filtros ---

    public String getFechaDesdeStr() {
        if (dccDesde.getDate() == null) return "";
        return new SimpleDateFormat("dd-MM-yyyy").format(dccDesde.getDate());
    }

    public String getFechaHastaStr() {
        if (dccHasta.getDate() == null) return "";
        return new SimpleDateFormat("dd-MM-yyyy").format(dccHasta.getDate());
    }

    public void limpiarFormulario() {
        txtBusqueda.setText("");
        cmbEstado.setSelectedIndex(0);
        dccDesde.setDate(null);
        dccHasta.setDate(null);
        modeloTabla.setRowCount(0);
        lblTotalResultados.setText("Listo...");
    }
}