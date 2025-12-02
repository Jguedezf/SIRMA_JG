/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      DialogoSolicitudServicio.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Vista de detalle modal que renderiza una representación visual de la Orden de
 * Servicio, simulando una "hoja de solicitud" lista para imprimir.
 * Aplica principios de Herencia, Composición y patrones de Fábrica de UI.
 * ============================================================================
 */
package vista;

import modelo.Mantenimiento;
import modelo.Vehiculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Clase que genera un diálogo modal para previsualizar una orden de servicio.
 * PRINCIPIO POO: Herencia - Extiende de {@link JDialog} para obtener el
 * comportamiento de una ventana modal.
 */
public class DialogoSolicitudServicio extends JDialog {

    /**
     * Constructor que inicializa y construye la vista previa de la solicitud.
     * PRINCIPIO POO: Composición - La interfaz se construye anidando múltiples
     * paneles (JPanel) con diferentes LayoutManagers (BorderLayout, BoxLayout, etc.)
     * para lograr una maquetación compleja y estructurada.
     *
     * @param owner Ventana padre que será bloqueada mientras este diálogo esté visible.
     * @param mantenimiento Objeto del modelo que contiene los datos del servicio.
     * @param vehiculo Objeto del modelo que contiene los datos del vehículo y su propietario.
     */
    public DialogoSolicitudServicio(Frame owner, Mantenimiento mantenimiento, Vehiculo vehiculo) {
        super(owner, "Solicitud de Servicio - SIRMA JG", true); // `true` para modalidad.
        setSize(650, 750);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // --- PANEL CONTENEDOR PRINCIPAL ---
        // Se aísla el estilo: este panel fuerza un fondo blanco para simular una hoja de papel,
        // ignorando el tema oscuro global de la aplicación.
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(Color.WHITE);

        // Panel "Hoja" que contendrá toda la información.
        JPanel panelHoja = new JPanel();
        panelHoja.setLayout(new BoxLayout(panelHoja, BoxLayout.Y_AXIS)); // Apilado vertical.
        panelHoja.setBackground(Color.WHITE);
        panelHoja.setBorder(new EmptyBorder(40, 50, 40, 50)); // Simula márgenes de impresión.

        // --- SECCIÓN 1: ENCABEZADO (LOGO Y TÍTULO) ---
        JPanel panelEncabezado = new JPanel(new BorderLayout(20, 0));
        panelEncabezado.setOpaque(false); // Transparente para heredar el fondo blanco.

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(new ImageIcon("fondo/sirma.png").getImage().getScaledInstance(120, -1, Image.SCALE_SMOOTH));
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) {
            // Carga silenciosa: si el logo no se encuentra, simplemente no se muestra.
        }

        JLabel lblTitulo = new JLabel("<html><div style='text-align: center;'>SOLICITUD DE SERVICIO DE<br>MANTENIMIENTO AUTOMOTRIZ</div></html>");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelEncabezado.add(lblLogo, BorderLayout.WEST);
        panelEncabezado.add(lblTitulo, BorderLayout.CENTER);
        panelHoja.add(panelEncabezado);
        panelHoja.add(Box.createVerticalStrut(30)); // Espaciador vertical.

        // --- SECCIÓN 2: DATOS DEL CLIENTE Y VEHÍCULO ---
        panelHoja.add(crearSeccion("DATOS DEL VEHÍCULO Y PROPIETARIO"));
        panelHoja.add(Box.createVerticalStrut(10));
        panelHoja.add(crearFilaDoble("Placa:", vehiculo.getPlaca(), "Propietario:", vehiculo.getPropietario().getNombreCompleto()));
        panelHoja.add(crearFilaDoble("Vehículo:", vehiculo.getMarca() + " " + vehiculo.getModelo(), "Cédula:", vehiculo.getPropietario().getCedula()));
        panelHoja.add(crearFilaDoble("Año / Color:", vehiculo.getAnio() + " - " + vehiculo.getColor(), "Teléfono:", vehiculo.getPropietario().getTelefono()));
        panelHoja.add(Box.createVerticalStrut(20));

        // --- SECCIÓN 3: DETALLES DE LA ORDEN ---
        panelHoja.add(crearSeccion("DETALLES DEL SERVICIO"));
        panelHoja.add(Box.createVerticalStrut(10));

        // Se define un formateador para mostrar la fecha en formato DD-MM-YYYY.
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        panelHoja.add(crearCampo("ID de Orden:", mantenimiento.getIdOrden()));
        panelHoja.add(crearCampo("Fecha de Emisión:", mantenimiento.getFechaRealizacion().format(formatoFecha)));
        panelHoja.add(crearCampo("Tipo de Servicio:", mantenimiento.getTipoServicio()));
        panelHoja.add(crearCampo("Kilometraje:", mantenimiento.getKilometrajeActual() + " Km"));
        String desc = mantenimiento.getDescripcionDetallada().isEmpty() ? "(Sin observaciones)" : mantenimiento.getDescripcionDetallada();
        panelHoja.add(crearCampo("Descripción:", "<html><p style='width:350px'>" + desc + "</p></html>"));
        panelHoja.add(Box.createVerticalStrut(20));

        // --- SECCIÓN 4: COSTOS ---
        panelHoja.add(crearSeccion("ESTIMACIÓN DE COSTOS"));
        panelHoja.add(Box.createVerticalStrut(10));
        panelHoja.add(crearFilaDoble(
                "Mano de Obra:", "$" + String.format(Locale.US, "%.2f", mantenimiento.getCostoManoObra()),
                "Repuestos:", "$" + String.format(Locale.US, "%.2f", mantenimiento.getCostoRepuestos())
        ));

        // Total destacado, alineado a la derecha.
        JPanel pTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pTotal.setOpaque(false);
        JLabel lTotal = new JLabel("TOTAL A PAGAR:  $" + String.format(Locale.US, "%.2f", mantenimiento.getCostoTotal()));
        lTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lTotal.setForeground(Color.BLACK);
        pTotal.add(lTotal);
        panelHoja.add(pTotal);

        panelHoja.add(Box.createVerticalGlue()); // Componente flexible que empuja el footer hacia abajo.

        // --- FOOTER: FIRMA ---
        JLabel lblFirmaLine = new JLabel("_______________________________");
        lblFirmaLine.setForeground(Color.BLACK);
        lblFirmaLine.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblFirmaText = new JLabel("Firma del Propietario / Autorización");
        lblFirmaText.setFont(new Font("Arial", Font.ITALIC, 12));
        lblFirmaText.setForeground(Color.BLACK);
        lblFirmaText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHoja.add(lblFirmaLine);
        panelHoja.add(lblFirmaText);

        // --- BOTÓN DE CIERRE (EXTERNO A LA HOJA) ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(new Color(45, 50, 55)); // Se mantiene el tema oscuro del sistema.
        BotonFuturista btnCerrar = new BotonFuturista("Cerrar Vista Previa");
        btnCerrar.addActionListener(e -> dispose());
        panelSur.add(btnCerrar);

        panelContenedor.add(panelHoja, BorderLayout.CENTER);
        panelContenedor.add(panelSur, BorderLayout.SOUTH);
        add(panelContenedor);
    }

    // =========================================================================
    // MÉTODOS DE FÁBRICA DE UI (UI FACTORY METHODS)
    // Estos métodos privados se encargan de crear componentes reutilizables,
    // asegurando un estilo consistente y simplificando el código del constructor.
    // =========================================================================

    /**
     * Método de Fábrica para crear un título de sección con un borde inferior.
     * @param titulo El texto del título.
     * @return Un JPanel formateado como un título de sección.
     */
    private JPanel crearSeccion(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel l = new JLabel(titulo);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        l.setForeground(Color.BLACK);
        l.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    /**
     * Método de Fábrica para crear una fila de datos simple (Etiqueta: Valor).
     * @param etiqueta El texto de la etiqueta.
     * @param valor El texto del valor.
     * @return Un JPanel que contiene una etiqueta y su valor.
     */
    private JPanel crearCampo(String etiqueta, String valor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        p.setOpaque(false);
        JLabel le = new JLabel(etiqueta);
        le.setFont(new Font("Arial", Font.BOLD, 12));
        le.setPreferredSize(new Dimension(120, 20));
        le.setForeground(Color.BLACK);
        JLabel lv = new JLabel(valor);
        lv.setFont(new Font("Arial", Font.PLAIN, 12));
        lv.setForeground(Color.BLACK);
        p.add(le);
        p.add(lv);
        return p;
    }

    /**
     * Método de Fábrica para crear una fila de datos con dos columnas.
     * @return Un JPanel que contiene dos pares de etiqueta-valor.
     */
    private JPanel crearFilaDoble(String et1, String v1, String et2, String v2) {
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.setOpaque(false);
        p.add(crearCampo(et1, v1));
        p.add(crearCampo(et2, v2));
        return p;
    }
}