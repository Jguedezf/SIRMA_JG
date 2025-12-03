/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelBusquedaInteligente.java
 * FECHA:        Diciembre 2025
 *
 * DESCRIPCIÓN TÉCNICA DEL MÓDULO:
 * Clase de la capa de Vista (View) responsable de la interfaz de Consultas Avanzadas.
 * Implementa un patrón de diseño de 'Filtro Compuesto', permitiendo al usuario
 * refinar el conjunto de datos mediante criterios acumulativos (Texto, Estado, Fecha).
 *
 * ESTRUCTURA ALGORÍTMICA:
 * - ENTRADA: Criterios seleccionados por el usuario en los componentes GUI.
 * - PROCESO: Delegación de la lógica de filtrado al Controlador (Patrón MVC).
 * - SALIDA: Actualización dinámica del modelo de datos de la JTable (Data Binding).
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
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;

/**
 * PanelBusquedaInteligente: Vista especializada para la minería y filtrado de datos.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel} para integrarse en el contenedor principal.
 */
public class PanelBusquedaInteligente extends JPanel {

    // ========================================================================
    // 1. ATRIBUTOS Y COMPONENTES DE INTERFAZ (ENCAPSULAMIENTO DE UI)
    // ========================================================================

    // Componentes de Entrada de Datos (Inputs)
    public JTextField txtBusqueda;          // Entrada para criterios textuales (Placa/ID)
    public JComboBox<String> cmbEstado;     // Selector categórico de estado
    public JDateChooser dccDesde, dccHasta; // Selectores de rango temporal

    // Componentes de Visualización (Outputs)
    public JTable tablaResultados;          // Grilla de datos
    public DefaultTableModel modeloTabla;   // Modelo de datos subyacente
    public JLabel lblTotalResultados;       // Feedback cuantitativo

    // Componentes de Control (Acciones)
    public JButton btnBuscar, btnLimpiar;
    public BotonFuturista btnAccionEditar, btnAccionEliminar, btnAccionNuevaOrden;

    // Recursos Gráficos
    private Image imagenFondoBusqueda;

    /**
     * CONSTRUCTOR: Inicializa el ciclo de vida del panel.
     * FASES:
     * 1. Carga de Recursos (Imágenes).
     * 2. Configuración del Layout Manager (Gestión espacial).
     * 3. Instanciación y composición de componentes (Factory Pattern).
     * 4. Inyección de dependencias y listeners.
     *
     * @param controlador Referencia al controlador para operaciones lógicas (no usado directamente en constructor, pero disponible).
     */
    public PanelBusquedaInteligente(ControladorSIRMA controlador) {
        // --- FASE 1: CARGA DE RECURSOS (Manejo de Excepciones I/O) ---
        try {
            File f = new File("fondo/fondo2.png");
            // Validación de existencia del recurso para evitar NullPointerException
            if(f.exists()) {
                imagenFondoBusqueda = ImageIO.read(f);
            } else {
                imagenFondoBusqueda = ImageIO.read(new File("fondo/fondo.png")); // Fallback
            }
        } catch(Exception e){
            e.printStackTrace();
            setBackground(new Color(45, 50, 55)); // Color de seguridad en caso de error gráfico
        }

        // --- FASE 2: MAQUETACIÓN (LAYOUT) ---
        setLayout(new BorderLayout(10, 10)); // Espaciado H, V
        setBorder(new EmptyBorder(10, 30, 10, 30)); // Márgenes internos (Padding)

        // --- FASE 3: CONSTRUCCIÓN DE LA INTERFAZ ---

        // A. SECCIÓN NORTE: FORMULARIO DE FILTROS
        // Se utiliza GridBagLayout para un control preciso de la alineación de filtros.
        JPanel panelNorte = new JPanel(new GridBagLayout());
        panelNorte.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Título del Módulo
        JLabel lblTitulo = new JLabel("Búsqueda Inteligente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        panelNorte.add(lblTitulo, gbc);

        // Fila 1: Buscador Principal (Texto + Botones Acción)
        gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel pBuscador = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        pBuscador.setOpaque(false);

        pBuscador.add(crearLabel("Placa / Orden:", SwingConstants.RIGHT));

        // Instanciación del campo de texto con estilos personalizados
        txtBusqueda = new JTextField();
        estilizarCampo(txtBusqueda);
        txtBusqueda.setFont(new Font("Arial", Font.BOLD, 20));
        txtBusqueda.setPreferredSize(new Dimension(250, 45));
        pBuscador.add(txtBusqueda);

        // Creación de Botones mediante Métodos de Fábrica (Code Reuse)
        btnBuscar = crearBotonIcono("Buscar", "fondo/icono_lupa.png", 64);
        btnLimpiar = crearBotonIcono("Limpiar", "fondo/limpiar_formulario.png", 64);

        pBuscador.add(btnBuscar);
        pBuscador.add(btnLimpiar);
        panelNorte.add(pBuscador, gbc);

        // Fila 2: Filtros Secundarios (Estado y Fechas)
        gbc.gridy = 2;
        JPanel pSecundario = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pSecundario.setOpaque(false);
        pSecundario.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Filtro Categórico (ComboBox)
        pSecundario.add(crearLabel("Estado:", SwingConstants.RIGHT));
        cmbEstado = new JComboBox<>(new String[]{"Todos", "Pendiente", "En Proceso", "Finalizado"});
        cmbEstado.setPreferredSize(new Dimension(140, 35));
        pSecundario.add(cmbEstado);

        // Filtros de Rango (DateChoosers)
        pSecundario.add(crearLabel("Desde:", SwingConstants.RIGHT));
        dccDesde = new JDateChooser(); estilizarCalendario(dccDesde);
        pSecundario.add(dccDesde);

        pSecundario.add(crearLabel("Hasta:", SwingConstants.RIGHT));
        dccHasta = new JDateChooser(); estilizarCalendario(dccHasta);
        pSecundario.add(dccHasta);

        panelNorte.add(pSecundario, gbc);
        add(panelNorte, BorderLayout.NORTH);

        // B. SECCIÓN CENTRAL: TABLA DE RESULTADOS (VISUALIZACIÓN)
        String[] cols = {"ID Orden", "Fecha", "Placa", "Marca", "Servicio", "Estado", "Total ($)"};

        // Polimorfismo: Clase anónima para sobrescribir `isCellEditable` y hacer la tabla de solo lectura.
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setRowHeight(30);
        tablaResultados.setFont(new Font("Arial", Font.PLAIN, 14));

        // --- ESTILIZADO DE ENCABEZADO (Identidad Corporativa) ---
        JTableHeader header = tablaResultados.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(255, 204, 0)); // Amarillo SIRMA
        header.setForeground(Color.BLACK);
        header.setOpaque(true);

        // Inyección de Renderizadores (Visualización personalizada de celdas)
        tablaResultados.getColumnModel().getColumn(3).setCellRenderer(new RenderizadorMarca());
        tablaResultados.getColumnModel().getColumn(5).setCellRenderer(new RenderizadorEstadoOrden());
        configurarAlineacionTabla(); // Método auxiliar para alinear celdas

        // Configuración del ScrollPane
        JScrollPane scroll = new JScrollPane(tablaResultados);
        scroll.getViewport().setBackground(Color.WHITE);

        // AJUSTE DE DIMENSIONES VISUALES:
        // Altura fijada en 332px para mostrar 10 filas exactas + encabezado.
        // Esto evita que la fila 11 se vea "cortada" visualmente.
        scroll.setPreferredSize(new Dimension(0, 332));

        // Borde con Título
        TitledBorder bordeTitulo = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Resultados de la Búsqueda");
        bordeTitulo.setTitleColor(Color.BLACK);
        bordeTitulo.setTitleFont(new Font("Arial", Font.BOLD, 14));
        scroll.setBorder(bordeTitulo);

        add(scroll, BorderLayout.CENTER);

        // C. SECCIÓN SUR: ACCIONES CONTEXTUALES
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setOpaque(false);

        // Feedback al usuario (Conteo de registros)
        lblTotalResultados = new JLabel("Listo para buscar...", SwingConstants.RIGHT);
        lblTotalResultados.setForeground(Color.CYAN); // Contraste alto sobre fondo oscuro
        lblTotalResultados.setBorder(new EmptyBorder(5,0,5,10));
        panelSur.add(lblTotalResultados, BorderLayout.NORTH);

        JPanel pAcc = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pAcc.setOpaque(false);
        Dimension d = new Dimension(280, 50);

        // Botones de acción sobre el registro seleccionado
        btnAccionEditar = crearBtnAcc("Editar / Ver Detalle", "fondo/icono_editar.png", null, d);
        btnAccionEliminar = crearBtnAcc("Eliminar Orden", "fondo/icono_eliminar.png", new Color(150,50,50), d);
        btnAccionNuevaOrden = crearBtnAcc("Nueva Orden de Servicio", "fondo/icono_agregar.png", new Color(40,167,69), d);

        pAcc.add(btnAccionEditar); pAcc.add(btnAccionEliminar); pAcc.add(btnAccionNuevaOrden);
        panelSur.add(pAcc, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        // --- PATRÓN OBSERVER (Event Handling) ---
        // Habilitación dinámica de botones según el estado de selección de la tabla.
        tablaResultados.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionada = tablaResultados.getSelectedRow() != -1;
            btnAccionEditar.setEnabled(seleccionada);
            btnAccionEliminar.setEnabled(seleccionada);
            btnAccionNuevaOrden.setEnabled(true); // Siempre disponible
        });
    }

    /**
     * MÉTODO: paintComponent
     * DESCRIPCIÓN: Renderizado personalizado del contenedor (Custom Painting).
     * Aplica la imagen de fondo y superpone una capa oscura (Overlay) para
     * garantizar la legibilidad de los componentes hijos.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(imagenFondoBusqueda != null) {
            g.drawImage(imagenFondoBusqueda, 0, 0, getWidth(), getHeight(), this);
            // Capa de oscurecimiento (Alpha Blending)
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(new Color(45, 50, 55));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // ============================================================================
    // MÉTODOS DE FÁBRICA Y UTILIDAD (UI FACTORY & HELPERS)
    // ============================================================================

    /**
     * Configura la alineación de las celdas de la tabla para mejorar la legibilidad.
     */
    private void configurarAlineacionTabla() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablaResultados.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        tablaResultados.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Fecha
        tablaResultados.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Placa

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tablaResultados.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);  // Total ($)
    }

    private JButton crearBotonIcono(String tooltip, String ruta, int size) {
        JButton b = new JButton();
        b.setToolTipText(tooltip);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            File f = new File(ruta);
            if (f.exists()) {
                b.setIcon(new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {}
        return b;
    }

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

    private JLabel crearLabel(String t, int alig) {
        JLabel l = new JLabel(t, alig);
        l.setForeground(Color.LIGHT_GRAY);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        return l;
    }

    private void estilizarCampo(JTextField c) {
        c.setBackground(Color.WHITE);
        c.setForeground(Color.BLACK);
        c.setCaretColor(Color.BLACK);
        c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    // ============================================================================
    // MÉTODOS DE ACCESO (GETTERS)
    // ============================================================================

    public String getFechaDesdeStr() {
        if (dccDesde.getDate() == null) return "";
        return new SimpleDateFormat("dd-MM-yyyy").format(dccDesde.getDate());
    }

    public String getFechaHastaStr() {
        if (dccHasta.getDate() == null) return "";
        return new SimpleDateFormat("dd-MM-yyyy").format(dccHasta.getDate());
    }

    /**
     * Resetea el estado del formulario y la visualización.
     */
    public void limpiarFormulario() {
        txtBusqueda.setText("");
        cmbEstado.setSelectedIndex(0);
        dccDesde.setDate(null);
        dccHasta.setDate(null);
        modeloTabla.setRowCount(0);
        lblTotalResultados.setText("Listo para buscar...");
    }
}