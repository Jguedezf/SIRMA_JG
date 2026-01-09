/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelHistorialVehiculo.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista encargada de visualizar el historial detallado de
 * mantenimientos. Implementa una tabla con formateo inteligente de datos y
 * gestión de eventos para la interacción con el usuario.
 * ============================================================================
 */
package vista;

import modelo.Mantenimiento;
import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;

/**
 * IDENTIFICACIÓN DE CLASE: PanelHistorialVehiculo
 * Interfaz gráfica para la consulta de históricos.
 * PRINCIPIO POO: Herencia - Extiende de JPanel para integrarse como un componente
 * reutilizable dentro de la ventana principal.
 */
public class PanelHistorialVehiculo extends JPanel {

    // -------------------------------------------------------------------------
    // IDENTIFICACIÓN DE ATRIBUTOS
    // -------------------------------------------------------------------------
    // Componentes gráficos (Widgets) accesibles por el Controlador.
    public JTable tablaHistorial;
    public DefaultTableModel modeloTabla;
    public BotonFuturista btnEliminarRegistro;
    public BotonFuturista btnVolver;
    public JLabel lblTituloVehiculo;

    // Atributos de estado interno (Encapsulamiento de datos temporales)
    private Vehiculo vehiculoActual;
    private List<Mantenimiento> listaActual;

    /**
     * MÉTODO CONSTRUCTOR
     * Inicializa la interfaz gráfica y configura los comportamientos visuales.
     * PRINCIPIO POO: Composición - Construye la interfaz agregando múltiples
     * objetos (JTable, JScrollPane, JPanel, JButton) en un layout organizado.
     */
    public PanelHistorialVehiculo() {
        // PROCESO: Configuración del contenedor principal
        setBackground(new Color(45, 50, 55));
        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // INSTANCIACIÓN: Etiqueta de Título
        lblTituloVehiculo = new JLabel("Historial de Mantenimiento", SwingConstants.CENTER);
        lblTituloVehiculo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloVehiculo.setForeground(Color.WHITE);
        add(lblTituloVehiculo, BorderLayout.NORTH);

        // INSTANCIACIÓN: Modelo de datos y Tabla
        // Se define la estructura de columnas para la visualización.
        String[] columnas = {"Fecha", "Servicio", "Descripcion", "Total ($)", "Km", "Prox. Servicio / Km"};

        // PRINCIPIO POO: Polimorfismo (Clase Anónima)
        // Se sobrescribe isCellEditable para hacer la tabla de solo lectura.
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // PROCESO: Estilización Visual (UX/UI)
        tablaHistorial.setRowHeight(25);
        tablaHistorial.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaHistorial.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaHistorial.getTableHeader().setBackground(new Color(255, 204, 0));

        // Alineación centrada de los datos
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaHistorial.getColumnCount(); i++) {
            tablaHistorial.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajuste de ancho para la columna de proyección (Fecha + Km)
        tablaHistorial.getColumnModel().getColumn(5).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        add(scrollPane, BorderLayout.CENTER);

        // SECCIÓN DE BOTONES DE ACCIÓN
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        panelBotones.setBackground(new Color(45, 50, 55));
        panelBotones.setBorder(new EmptyBorder(10, 0, 10, 0));

        btnEliminarRegistro = new BotonFuturista("Eliminar Registro");
        // CORRECCIÓN: Usamos setBackgroundColor en lugar de setBackground
        btnEliminarRegistro.setBackgroundColor(new Color(80, 85, 90)); // Estado inactivo
        btnEliminarRegistro.setPreferredSize(new Dimension(200, 45));
        btnEliminarRegistro.setEnabled(false); // VALIDACIÓN: Deshabilitado por defecto

        // Carga del Icono (Papelera) para consistencia visual
        try {
            File f = new File("fondo/icono_eliminar.png");
            if(f.exists()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
                btnEliminarRegistro.setIcon(icon);
                btnEliminarRegistro.setIconTextGap(10);
            }
        } catch (Exception ex) { }

        // PROCESO: Gestión de Eventos (Listener)
        // Lógica visual para cambiar el estado del botón según la selección del usuario.
        tablaHistorial.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean haySeleccion = tablaHistorial.getSelectedRow() != -1;
                btnEliminarRegistro.setEnabled(haySeleccion);

                // Feedback Visual: Cambio de color semántico (Rojo Alerta vs Gris Neutro)
                if (haySeleccion) {
                    // CORRECCIÓN: Usamos setBackgroundColor para forzar el repintado
                    btnEliminarRegistro.setBackgroundColor(new Color(220, 20, 60));
                } else {
                    btnEliminarRegistro.setBackgroundColor(new Color(80, 85, 90));
                }
            }
        });

        btnVolver = new BotonFuturista("Volver al Dashboard");
        btnVolver.setPreferredSize(new Dimension(220, 45));

        panelBotones.add(btnEliminarRegistro);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * METODO: cargarHistorial
     * Puebla la tabla con los datos del vehículo seleccionado.
     * * @param v Objeto Vehículo que contiene la lista de mantenimientos (ENTRADA).
     */
    public void cargarHistorial(Vehiculo v) {
        this.vehiculoActual = v;
        this.listaActual = v.getHistorialMantenimientos();

        // PROCESO: Actualización de la interfaz
        lblTituloVehiculo.setText("Historial: " + v.getPlaca() + " (" + v.getMarca() + ")");
        modeloTabla.setRowCount(0); // Limpieza previa

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // PROCESO: Iteración y formateo de datos
        for (Mantenimiento m : listaActual) {
            // Lógica de presentación para fecha próxima
            String datosProximo = (m.getFechaProximoServicio() != null)
                    ? m.getFechaProximoServicio().format(fmt)
                    : "N/A";

            // VALIDACIÓN: Si existe proyección de kilometraje, se concatena
            if (m.getKilometrajeProximoServicio() > 0) {
                datosProximo += " / " + m.getKilometrajeProximoServicio() + " Km";
            }

            // Formateo numérico para moneda (Locale US para punto decimal)
            String totalFormateado = String.format(Locale.US, "%.2f", m.getCostoTotal());

            // Creación de la fila (Objeto Array)
            Object[] fila = {
                    m.getFechaRealizacion().format(fmt),
                    m.getTipoServicio(),
                    m.getDescripcionDetallada(),
                    totalFormateado,
                    m.getKilometrajeActual() + " km",
                    datosProximo // SALIDA: Columna combinada inteligente
            };
            modeloTabla.addRow(fila);
        }
    }

    // --- MÉTODOS DE ACCESO (GETTERS) ---

    /**
     * Obtiene el vehículo que se está visualizando actualmente.
     * @return Objeto Vehiculo actual.
     */
    public Vehiculo getVehiculoActual() { return vehiculoActual; }

    /**
     * Obtiene el objeto mantenimiento seleccionado en la tabla.
     * VALIDACIÓN: Verifica que el índice de fila sea válido (!= -1).
     * @return Objeto Mantenimiento o null si no hay selección.
     */
    public Mantenimiento getMantenimientoSeleccionado() {
        int fila = tablaHistorial.getSelectedRow();
        if (fila != -1) return listaActual.get(fila);
        return null;
    }
}