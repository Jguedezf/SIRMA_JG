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
import javax.swing.table.JTableHeader;
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
            File f = new File("fondo/fondo2.png");
            if (f.exists()) {
                backgroundImage = ImageIO.read(f);
            } else {
                // Fallback: Si no existe fondo2, intenta cargar el fondo normal
                backgroundImage = ImageIO.read(new File("fondo/fondo.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            setBackground(new Color(45, 50, 55));
        }

        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- SECCIÓN NORTE: TÍTULO Y BOTONERA DE ACCIONES (ICONOS GRANDES) ---
        JPanel norteContainer = new JPanel();
        norteContainer.setLayout(new BoxLayout(norteContainer, BoxLayout.Y_AXIS));
        norteContainer.setOpaque(false);

        JLabel lblTitulo = new JLabel("Bitácora y Gestión de Órdenes de Servicio", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel de botones con mayor separación para que respiren los iconos grandes
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        panelBotones.setOpaque(false);

        // Creación de botones con el nuevo estilo "Gran Ícono"
        btnAgregarOrden = crearBotonGrande("Agregar Orden", "fondo/icono_agregar.png");

        btnEditarOrden = crearBotonGrande("Editar / Ver", "fondo/icono_editar.png");
        btnEditarOrden.setEnabled(false); // Deshabilitado hasta que se seleccione una fila.

        btnEliminarOrden = crearBotonGrande("Eliminar", "fondo/icono_eliminar.png");
        btnEliminarOrden.setBackground(new Color(150, 50, 50)); // Rojo para eliminar
        btnEliminarOrden.setEnabled(false); // Deshabilitado hasta que se seleccione una fila.

        panelBotones.add(btnAgregarOrden);
        panelBotones.add(btnEditarOrden);
        panelBotones.add(btnEliminarOrden);

        norteContainer.add(lblTitulo);
        norteContainer.add(Box.createVerticalStrut(10)); // Espacio entre título y botones
        norteContainer.add(panelBotones);

        add(norteContainer, BorderLayout.NORTH);

        // --- SECCIÓN CENTRAL: TABLA DE ÓRDENES DE SERVICIO ---
        // CAMBIO: Se renombró la columna a "Identificación" para abarcar Cédula y RIF.
        String[] columnas = {"ID Orden", "Placa", "Marca", "Servicio", "Fecha", "Estado", "Identificación", "Total ($)"};

        // PRINCIPIO POO: Polimorfismo (Sobrescritura) - Se utiliza una clase anónima
        // que hereda de DefaultTableModel para sobreescribir el método `isCellEditable`.
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaOrdenes = new JTable(modeloTabla);
        tablaOrdenes.setRowHeight(30); // Altura de fila
        tablaOrdenes.setFont(new Font("Arial", Font.PLAIN, 14));

        // --- ESTILIZADO DEL ENCABEZADO (Amarillo Corporativo) ---
        JTableHeader header = tablaOrdenes.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(255, 204, 0)); // Amarillo fuerte
        header.setForeground(Color.BLACK); // Texto negro
        header.setOpaque(true);

        // Se asigna un renderizador personalizado a la columna "Estado" para el formato condicional.
        tablaOrdenes.getColumnModel().getColumn(5).setCellRenderer(new RenderizadorEstadoOrden());

        // PROCESO DE ALINEACIÓN DE CELDAS:
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        // Se aplican los renderizadores a las columnas correspondientes.
        tablaOrdenes.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID Orden
        tablaOrdenes.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Placa
        tablaOrdenes.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Fecha

        // Alineación CENTRADA para la Identificación (Columna 6)
        tablaOrdenes.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        // Alineación DERECHA para el Total (Columna 7)
        tablaOrdenes.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

        JScrollPane scrollPane = new JScrollPane(tablaOrdenes);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // AJUSTE DE ALTURA EXACTO:
        // Encabezado (~25px) + 10 Filas (300px) = 325px.
        // Esto asegura que la fila 11 quede oculta y active el scroll.
        scrollPane.setPreferredSize(new Dimension(0, 325));

        // Envolvemos el scroll en un panel BorderLayout para que respete la altura
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.add(scrollPane, BorderLayout.NORTH); // Pegado arriba del centro

        add(panelTabla, BorderLayout.CENTER);
    }

    /**
     * Metodo de Fabrica (Factory Method) Modificado para estilo "Gran Icono".
     * Crea botones grandes, transparentes, con el icono arriba y texto abajo.
     * @param texto El texto del botón.
     * @param rutaIcono La ruta del archivo de ícono para el botón.
     * @return Un objeto BotonFuturista configurado.
     */
    private BotonFuturista crearBotonGrande(String texto, String rutaIcono) {
        BotonFuturista b = new BotonFuturista(texto);

        // Configuración de Layout del Botón (Vertical)
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setHorizontalTextPosition(SwingConstants.CENTER);

        // Dimensiones más grandes para destacar los iconos
        b.setPreferredSize(new Dimension(140, 110));

        // Estilo Transparente/Oscuro para resaltar sobre el fondo
        b.setBackground(new Color(30, 30, 30, 180));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setIconTextGap(10); // Espacio entre imagen y texto

        try {
            File f = new File(rutaIcono);
            if (f.exists()) {
                // Icono escalado a mayor tamaño (55x55) para mayor vistosidad
                b.setIcon(new ImageIcon(new ImageIcon(rutaIcono).getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Sobrescribe el metodo de pintado para dibujar el fondo de imagen personalizado.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura).
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        // Capa oscura para resaltar los botones y la tabla sobre la imagen de fondo
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}