/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: DialogoSolicitudServicio.java
 * Descripcion: Ventana de dialogo que simula una solicitud de servicio.
 * Fecha: Noviembre 2025
 * Version: 3.1 (Correccion final de color de texto)
 * -----------------------------------------------------------------------------
 */
package vista;

import modelo.Mantenimiento;
import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogoSolicitudServicio extends JDialog {

    public DialogoSolicitudServicio(Frame owner, Mantenimiento mantenimiento, Vehiculo vehiculo) {
        super(owner, "Solicitud de Servicio - SIRMA JG", true);
        setSize(700, 720);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(Color.WHITE);

        JPanel panelHoja = new JPanel();
        panelHoja.setLayout(new BoxLayout(panelHoja, BoxLayout.Y_AXIS));
        panelHoja.setBackground(Color.WHITE);
        panelHoja.setBorder(new EmptyBorder(20, 30, 20, 30));

        // --- Encabezado ---
        JPanel panelEncabezado = new JPanel(new BorderLayout(20, 0));
        panelEncabezado.setOpaque(false);
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(new ImageIcon("fondo/sirma.png").getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH));
            lblLogo.setIcon(logoIcon);
        } catch (Exception e) {
            System.err.println("Error al cargar el logo.");
        }
        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);
        JLabel lblTitulo = new JLabel("<html><div style='text-align: center;'>SOLICITUD DE SERVICIO DE<br>MANTENIMIENTO AUTOMOTRIZ</div></html>");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        panelTitulo.add(lblTitulo);
        panelEncabezado.add(lblLogo, BorderLayout.WEST);
        panelEncabezado.add(panelTitulo, BorderLayout.CENTER);

        panelHoja.add(panelEncabezado);
        panelHoja.add(Box.createVerticalStrut(20));

        // --- Datos ---
        panelHoja.add(crearSeccion("Datos del Vehículo y Propietario"));
        panelHoja.add(crearFilaDoble("Placa:", vehiculo.getPlaca(), "Nombre:", vehiculo.getPropietario().getNombreCompleto()));
        panelHoja.add(crearFilaDoble("Marca/Modelo:", vehiculo.getMarca() + " " + vehiculo.getModelo(), "Cédula:", vehiculo.getPropietario().getCedula()));
        panelHoja.add(crearFilaDoble("Año:", String.valueOf(vehiculo.getAnio()), "Teléfono:", vehiculo.getPropietario().getTelefono()));
        panelHoja.add(Box.createVerticalStrut(20));
        panelHoja.add(crearSeccion("Detalles del Servicio"));
        panelHoja.add(crearCampo("ID de Orden:", mantenimiento.getIdOrden()));
        panelHoja.add(crearCampo("Fecha de Ingreso:", mantenimiento.getFechaRealizacion().toString()));
        panelHoja.add(crearCampo("Tipo de Servicio:", mantenimiento.getTipoServicio()));
        String descripcion = mantenimiento.getDescripcionDetallada().isEmpty() ? "(Sin descripción detallada)" : mantenimiento.getDescripcionDetallada();
        panelHoja.add(crearCampo("Descripción:", "<html><p style='width:500px'>" + descripcion + "</p></html>"));

        panelHoja.add(Box.createVerticalGlue());

        // --- Firma ---
        JLabel lblAcuerdo = new JLabel("Acepto los términos y condiciones del servicio.", SwingConstants.CENTER);
        lblAcuerdo.setFont(new Font("Serif", Font.ITALIC, 12));
        lblAcuerdo.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        lblAcuerdo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblFirma = new JLabel("_________________________", SwingConstants.CENTER);
        lblFirma.setFont(new Font("Serif", Font.PLAIN, 16));
        lblFirma.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        lblFirma.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblNombreFirma = new JLabel("Firma del Propietario", SwingConstants.CENTER);
        lblNombreFirma.setFont(new Font("Serif", Font.PLAIN, 14));
        lblNombreFirma.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        lblNombreFirma.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelHoja.add(lblAcuerdo);
        panelHoja.add(Box.createVerticalStrut(40));
        panelHoja.add(lblFirma);
        panelHoja.add(lblNombreFirma);

        // --- Boton de Cierre ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(Color.WHITE);
        panelSur.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(220, 220, 220));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btnCerrar.addActionListener(e -> dispose());
        panelSur.add(btnCerrar);

        panelContenedor.add(panelHoja, BorderLayout.CENTER);
        panelContenedor.add(panelSur, BorderLayout.SOUTH);

        add(panelContenedor);
    }

    private JPanel crearSeccion(String t) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setOpaque(false);
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 18));
        l.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));
        l.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        p.add(l);
        return p;
    }

    private JPanel crearCampo(String et, String val) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        p.setOpaque(false);
        JLabel le = new JLabel(et);
        le.setFont(new Font("Segoe UI", Font.BOLD, 14));
        le.setPreferredSize(new Dimension(150, 20));
        le.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        JLabel lv = new JLabel(val);
        lv.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lv.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        p.add(le);
        p.add(lv);
        return p;
    }

    private JPanel crearFilaDoble(String et1, String v1, String et2, String v2) {
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.setOpaque(false);

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        p1.setOpaque(false);
        JLabel l1 = new JLabel(et1);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l1.setPreferredSize(new Dimension(100, 20));
        l1.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        JLabel v1l = new JLabel(v1);
        v1l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        v1l.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        p1.add(l1);
        p1.add(v1l);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        p2.setOpaque(false);
        JLabel l2 = new JLabel(et2);
        l2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l2.setPreferredSize(new Dimension(100, 20));
        l2.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        JLabel v2l = new JLabel(v2);
        v2l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        v2l.setForeground(Color.BLACK); // <<-- FORZAR COLOR NEGRO
        p2.add(l2);
        p2.add(v2l);

        p.add(p1);
        p.add(p2);
        return p;
    }
}