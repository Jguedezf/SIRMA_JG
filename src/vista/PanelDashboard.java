/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelDashboard.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista que implementa el dashboard principal o bitácora
 * del sistema. Muestra un listado general de todas las órdenes de servicio
 * y provee los controles principales para agregar, editar y eliminar órdenes.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * PanelDashboard es la vista principal para la gestión de órdenes de servicio.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel} para ser un componente de Swing.
 */
public class PanelDashboard extends JPanel {

    // --- ATRIBUTOS DE UI (Públicos para acceso desde el mediador, VentanaPrincipal) ---
    public JTable tablaOrdenes;
    public DefaultTableModel modeloTabla;
    public BotonFuturista btnAgregarOrden, btnEditarOrden, btnEliminarOrden;

    // Atributo encapsulado para la imagen de fondo.
    private Image backgroundImage;

    /**
     * Constructor del panel del dashboard.
     * Se encarga de la composición y maquetación de la interfaz de la bitácora.
     * PRINCIPIO POO: Composición - La interfaz se construye anidando múltiples
     * componentes (paneles, etiquetas, botones, tabla) para formar la vista completa.
     */
    public PanelDashboard() {
        // Carga de la imagen de fondo con manejo de errores.
        try {
            backgroundImage = ImageIO.read(new File("fondo/fondo.png"));
        } catch (Exception e) {
            e.printStackTrace();
            setBackground(new Color(45, 50, 55));
        }

        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- SECCIÓN NORTE: TÍTULO Y BOTONERA DE ACCIONES ---
        JLabel lblTitulo = new JLabel("Bitácora y Gestión de Órdenes de Servicio", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setOpaque(false);
        btnAgregarOrden = btn("Agregar Orden", "fondo/icono_agregar.png");
        btnEditarOrden = btn("Editar", "fondo/icono_editar.png");
        btnEditarOrden.setEnabled(false); // Deshabilitado hasta que se seleccione una fila.
        btnEliminarOrden = btn("Eliminar", "fondo/icono_eliminar.png");
        btnEliminarOrden.setBackground(new Color(150, 50, 50));
        btnEliminarOrden.setEnabled(false); // Deshabilitado hasta que se seleccione una fila.
        panelBotones.add(btnAgregarOrden);
        panelBotones.add(btnEditarOrden);
        panelBotones.add(btnEliminarOrden);

        JPanel norteContainer = new JPanel(new BorderLayout());
        norteContainer.setOpaque(false);
        norteContainer.add(lblTitulo, BorderLayout.NORTH);
        norteContainer.add(panelBotones, BorderLayout.CENTER);
        add(norteContainer, BorderLayout.NORTH);

        // --- SECCIÓN CENTRAL: TABLA DE ÓRDENES DE SERVICIO ---
        String[] columnas = {"ID Orden", "Placa", "Marca", "Servicio", "Fecha", "Estado", "Cédula Prop.", "Total ($)"};

        // PRINCIPIO POO: Polimorfismo (Sobrescritura) - Se utiliza una clase anónima
        // que hereda de DefaultTableModel para sobreescribir el método `isCellEditable`
        // y hacer que todas las celdas de la tabla no sean editables por el usuario.
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaOrdenes = new JTable(modeloTabla);
        tablaOrdenes.setRowHeight(30);
        tablaOrdenes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaOrdenes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Se asigna un renderizador personalizado a la columna "Estado" para el formato condicional.
        tablaOrdenes.getColumnModel().getColumn(5).setCellRenderer(new RenderizadorEstadoOrden());

        // PROCESO DE ALINEACIÓN DE CELDAS:
        // Se crean instancias de DefaultTableCellRenderer para controlar la alineación del texto.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        // Se aplican los renderizadores a las columnas correspondientes.
        tablaOrdenes.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID Orden
        tablaOrdenes.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Placa
        tablaOrdenes.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Fecha
        tablaOrdenes.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);  // Total ($)

        JScrollPane scrollPane = new JScrollPane(tablaOrdenes);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Método de Fábrica (Factory Method) para crear y configurar un BotonFuturista.
     * @param texto El texto del botón.
     * @param rutaIcono La ruta del archivo de ícono para el botón.
     * @return Un objeto BotonFuturista configurado.
     */
    private BotonFuturista btn(String texto, String rutaIcono) {
        BotonFuturista b = new BotonFuturista(texto);
        b.setPreferredSize(new Dimension(180, 50));
        try {
            b.setIcon(new ImageIcon(new ImageIcon(rutaIcono).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo de imagen personalizado.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura).
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}