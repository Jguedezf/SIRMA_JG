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
 * Servicio en formato HORIZONTAL (Apaisado) para mejor visualización.
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
 * PRINCIPIO POO: Herencia - Extiende de JDialog.
 */
public class DialogoSolicitudServicio extends JDialog {

    /**
     * Constructor que inicializa la vista previa horizontal.
     */
    public DialogoSolicitudServicio(Frame owner, Mantenimiento mantenimiento, Vehiculo vehiculo) {
        super(owner, "Solicitud de Servicio - SIRMA JG", true);

        // AJUSTE: Tamaño más ancho (Horizontal)
        setSize(900, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Panel Principal (Fondo Blanco)
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(Color.WHITE);

        // Panel "Hoja"
        JPanel panelHoja = new JPanel(new BorderLayout(20, 20)); // Márgenes internos
        panelHoja.setBackground(Color.WHITE);
        panelHoja.setBorder(new EmptyBorder(20, 30, 20, 30));

        // --- 1. ENCABEZADO ---
        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setOpaque(false);

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(new ImageIcon("fondo/sirma.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) { }

        JLabel lblTitulo = new JLabel("SOLICITUD DE SERVICIO DE MANTENIMIENTO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.BLACK);

        panelEncabezado.add(lblLogo, BorderLayout.WEST);
        panelEncabezado.add(lblTitulo, BorderLayout.CENTER);
        panelHoja.add(panelEncabezado, BorderLayout.NORTH);

        // --- 2. CUERPO (DIVIDIDO EN 2 COLUMNAS) ---
        JPanel panelCuerpo = new JPanel(new GridLayout(1, 2, 30, 0)); // 2 Columnas, separación de 30px
        panelCuerpo.setOpaque(false);

        // --- COLUMNA IZQUIERDA: DATOS Y VEHÍCULO ---
        JPanel panelIzq = new JPanel();
        panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
        panelIzq.setOpaque(false);

        panelIzq.add(crearSeccion("DATOS DEL VEHÍCULO Y CLIENTE"));
        panelIzq.add(Box.createVerticalStrut(10));
        panelIzq.add(crearCampo("Placa:", vehiculo.getPlaca()));
        panelIzq.add(crearCampo("Vehículo:", vehiculo.getMarca() + " " + vehiculo.getModelo()));
        panelIzq.add(crearCampo("Propietario:", vehiculo.getPropietario().getNombreCompleto()));
        panelIzq.add(crearCampo("Cédula:", vehiculo.getPropietario().getCedula()));
        panelIzq.add(crearCampo("Teléfono:", vehiculo.getPropietario().getTelefono()));

        panelIzq.add(Box.createVerticalStrut(20));
        panelIzq.add(crearSeccion("DETALLES OPERATIVOS"));
        panelIzq.add(Box.createVerticalStrut(10));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        panelIzq.add(crearCampo("ID Orden:", mantenimiento.getIdOrden()));
        panelIzq.add(crearCampo("Fecha:", mantenimiento.getFechaRealizacion().format(fmt)));
        panelIzq.add(crearCampo("Kilometraje:", mantenimiento.getKilometrajeActual() + " Km"));

        // --- COLUMNA DERECHA: SERVICIO Y COSTOS ---
        JPanel panelDer = new JPanel();
        panelDer.setLayout(new BoxLayout(panelDer, BoxLayout.Y_AXIS));
        panelDer.setOpaque(false);

        panelDer.add(crearSeccion("DESCRIPCIÓN DEL SERVICIO"));
        panelDer.add(Box.createVerticalStrut(10));
        panelDer.add(crearCampo("Tipo:", mantenimiento.getTipoServicio()));
        String desc = mantenimiento.getDescripcionDetallada().isEmpty() ? "(Sin observaciones)" : mantenimiento.getDescripcionDetallada();
        JTextArea txtDesc = new JTextArea(desc);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDesc.setBackground(new Color(245, 245, 245)); // Gris muy claro
        txtDesc.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelDer.add(txtDesc);

        panelDer.add(Box.createVerticalStrut(20));
        panelDer.add(crearSeccion("RESUMEN DE COSTOS"));
        panelDer.add(Box.createVerticalStrut(10));
        panelDer.add(crearCampo("Mano de Obra:", "$" + String.format(Locale.US, "%.2f", mantenimiento.getCostoManoObra())));
        panelDer.add(crearCampo("Repuestos:", "$" + String.format(Locale.US, "%.2f", mantenimiento.getCostoRepuestos())));

        JLabel lTotal = new JLabel("TOTAL: $" + String.format(Locale.US, "%.2f", mantenimiento.getCostoTotal()));
        lTotal.setFont(new Font("Arial", Font.BOLD, 24));
        lTotal.setForeground(new Color(0, 100, 0)); // Verde oscuro
        lTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDer.add(Box.createVerticalStrut(10));
        panelDer.add(lTotal);

        panelCuerpo.add(panelIzq);
        panelCuerpo.add(panelDer);
        panelHoja.add(panelCuerpo, BorderLayout.CENTER);

        // --- 3. PIE DE PÁGINA (FIRMA) ---
        JPanel panelPie = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelPie.setOpaque(false);
        panelPie.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel pFirma = new JPanel(new BorderLayout());
        pFirma.setOpaque(false);
        JLabel lLinea = new JLabel("_______________________________", SwingConstants.CENTER);
        JLabel lTexto = new JLabel("Firma del Propietario / Autorización", SwingConstants.CENTER);
        lTexto.setFont(new Font("Arial", Font.ITALIC, 11));
        pFirma.add(lLinea, BorderLayout.NORTH);
        pFirma.add(lTexto, BorderLayout.CENTER);

        panelPie.add(pFirma);
        panelHoja.add(panelPie, BorderLayout.SOUTH);

        // --- BOTÓN CERRAR ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(new Color(45, 50, 55));
        BotonFuturista btnCerrar = new BotonFuturista("Cerrar Vista");
        btnCerrar.addActionListener(e -> dispose());
        panelSur.add(btnCerrar);

        panelContenedor.add(panelHoja, BorderLayout.CENTER);
        panelContenedor.add(panelSur, BorderLayout.SOUTH);
        add(panelContenedor);
    }

    // --- MÉTODOS DE FÁBRICA ---

    private JPanel crearSeccion(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(1000, 30)); // Altura fija
        JLabel l = new JLabel(titulo);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        l.setForeground(new Color(0, 51, 102)); // Azul oscuro
        l.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 51, 102)));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    private JPanel crearCampo(String etiqueta, String valor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel le = new JLabel(etiqueta);
        le.setFont(new Font("Arial", Font.BOLD, 12));
        le.setPreferredSize(new Dimension(100, 20));
        JLabel lv = new JLabel(valor);
        lv.setFont(new Font("Arial", Font.PLAIN, 12));
        p.add(le);
        p.add(lv);
        return p;
    }
}