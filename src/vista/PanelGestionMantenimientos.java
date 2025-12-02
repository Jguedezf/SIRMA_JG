/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelGestionMantenimientos.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista que implementa el formulario transaccional para
 * la creación (Alta) y modificación (Edición) de órdenes de servicio. Utiliza
 * un GridBagLayout para una maquetación de formulario compleja y flexible.
 * ============================================================================
 */
package vista;

import modelo.Mantenimiento;
import modelo.Vehiculo;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Panel para la gestión de órdenes de mantenimiento.
 * Proporciona una interfaz para que el usuario ingrese o edite los detalles de un servicio.
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel}.
 */
public class PanelGestionMantenimientos extends JPanel {

    // --- ATRIBUTOS DE UI (Públicos para acceso desde VentanaPrincipal) ---
    public JComboBox<String> comboVehiculos;
    public JComboBox<String> comboServicios;
    public JComboBox<String> comboEstado;
    public JTextArea areaDescripcion;
    public JTextField campoKilometraje;
    public JTextField campoCostoManoObra;
    public JTextField campoCostoRepuestos;
    public JTextField campoMecanicoAsignado;
    public BotonFuturista btnGuardar;
    public BotonFuturista btnImprimirSolicitud;
    public BotonFuturista btnLimpiar;
    public BotonFuturista btnVolver;

    // --- ATRIBUTOS DE ESTADO INTERNO ---
    private Mantenimiento mantenimientoEnEdicion = null;
    private Image backgroundImage;

    /**
     * Constructor del panel de gestión de mantenimientos.
     * Se encarga de la composición de todos los componentes del formulario.
     * PRINCIPIO POO: Composición - La interfaz se construye anidando múltiples
     * componentes y paneles con diferentes layouts (BorderLayout, FlowLayout, GridBagLayout).
     */
    public PanelGestionMantenimientos() {
        // Carga de la imagen de fondo.
        try {
            backgroundImage = ImageIO.read(new File("fondo/fondo.png"));
        } catch (Exception e) {
            e.printStackTrace();
            setBackground(new Color(45, 50, 55));
        }

        // Se configura como opaco para poder dibujar su propio fondo.
        setOpaque(true);
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(10, 30, 10, 30));

        // --- SECCIÓN NORTE: TÍTULO Y BOTONERA SUPERIOR ---
        JLabel lblTitulo = new JLabel("Registrar Nueva Orden de Servicio", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);

        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        pBotones.setOpaque(false);
        btnGuardar = crearBoton("Guardar", "fondo/icono_guardar.png");
        btnImprimirSolicitud = crearBoton("Imprimir Solicitud", "fondo/icono_imprimir_solicitud.png");
        btnImprimirSolicitud.setEnabled(false);
        btnLimpiar = crearBoton("Limpiar", "fondo/limpiar_formulario.png");
        btnVolver = crearBoton("Volver", "fondo/icono_volver.png");
        pBotones.add(btnGuardar);
        pBotones.add(btnImprimirSolicitud);
        pBotones.add(btnLimpiar);
        pBotones.add(btnVolver);

        JPanel pNorte = new JPanel(new BorderLayout());
        pNorte.setOpaque(false);
        pNorte.add(lblTitulo, BorderLayout.NORTH);
        pNorte.add(pBotones, BorderLayout.CENTER);
        add(pNorte, BorderLayout.NORTH);

        // --- SECCIÓN CENTRAL: FORMULARIO DE DATOS (GridBagLayout) ---
        JPanel pForm = new JPanel(new GridBagLayout());
        pForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Columna Izquierda
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.5;
        addEtiqueta(pForm, "Vehículo (Placa):", gbc);
        gbc.gridy++;
        comboVehiculos = new JComboBox<>();
        comboVehiculos.setFont(new Font("Arial", Font.PLAIN, 14));
        pForm.add(comboVehiculos, gbc);
        gbc.gridy++;
        addEtiqueta(pForm, "Tipo de Servicio:", gbc);
        gbc.gridy++;
        comboServicios = new JComboBox<>(modelo.CatalogoServicios.getServicios());
        comboServicios.setFont(new Font("Arial", Font.PLAIN, 14));
        pForm.add(comboServicios, gbc);
        gbc.gridy++;
        addEtiqueta(pForm, "Estado de la Orden:", gbc);
        gbc.gridy++;
        comboEstado = new JComboBox<>(new String[]{"Pendiente", "En Proceso", "Finalizado"});
        comboEstado.setFont(new Font("Arial", Font.PLAIN, 14));
        pForm.add(comboEstado, gbc);

        // Columna Derecha
        gbc.gridx = 1; gbc.gridy = 0;
        addEtiqueta(pForm, "Kilometraje Actual:", gbc);
        gbc.gridy++;
        campoKilometraje = new JTextField();
        estilizarCampo(campoKilometraje);
        pForm.add(campoKilometraje, gbc);
        gbc.gridy++;
        addEtiqueta(pForm, "Costo Mano de Obra ($):", gbc);
        gbc.gridy++;
        campoCostoManoObra = new JTextField("0.00");
        estilizarCampo(campoCostoManoObra);
        campoCostoManoObra.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) { campoCostoManoObra.selectAll(); }
        });
        pForm.add(campoCostoManoObra, gbc);
        gbc.gridy++;
        addEtiqueta(pForm, "Costo Repuestos ($):", gbc);
        gbc.gridy++;
        campoCostoRepuestos = new JTextField("0.00");
        estilizarCampo(campoCostoRepuestos);
        campoCostoRepuestos.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) { campoCostoRepuestos.selectAll(); }
        });
        pForm.add(campoCostoRepuestos, gbc);

        // Sección Inferior
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2;
        addEtiqueta(pForm, "Mecánico Asignado (Automático):", gbc);
        gbc.gridy++;
        campoMecanicoAsignado = new JTextField();
        estilizarCampo(campoMecanicoAsignado);
        campoMecanicoAsignado.setEditable(false);
        campoMecanicoAsignado.setBackground(new Color(60, 65, 70));
        campoMecanicoAsignado.setForeground(Color.CYAN);
        pForm.add(campoMecanicoAsignado, gbc);
        gbc.gridy++;
        addEtiqueta(pForm, "Descripción Detallada del Servicio:", gbc);
        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        areaDescripcion = new JTextArea(4, 20);
        areaDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollDesc = new JScrollPane(areaDescripcion);
        pForm.add(scrollDesc, gbc);
        add(pForm, BorderLayout.CENTER);
    }

    /**
     * Sobrescribe el método de pintado para dibujar el fondo de imagen personalizado.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(new Color(45, 50, 55));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // =========================================================================
    // MÉTODOS PÚBLICOS DE GESTIÓN DE ESTADO
    // =========================================================================

    /**
     * Prepara el formulario para el registro de una nueva orden.
     * @param vehiculos Lista de vehículos para poblar el ComboBox.
     */
    public void prepararParaNuevaOrden(List<Vehiculo> vehiculos) {
        mantenimientoEnEdicion = null;
        comboVehiculos.removeAllItems();
        for(Vehiculo v : vehiculos) {
            comboVehiculos.addItem(v.getPlaca() + " (" + v.getMarca() + " " + v.getModelo() + ")");
        }
        comboVehiculos.setSelectedIndex(-1);
        comboServicios.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        campoKilometraje.setText("");
        campoCostoManoObra.setText("0.00");
        campoCostoRepuestos.setText("0.00");
        campoMecanicoAsignado.setText("");
        areaDescripcion.setText("");
        btnImprimirSolicitud.setEnabled(false);
    }

    /**
     * Prepara el formulario para la edición de una orden existente.
     * @param m Objeto Mantenimiento a editar.
     * @param v Objeto Vehículo asociado a la orden.
     */
    public void prepararParaEditarOrden(Mantenimiento m, Vehiculo v) {
        mantenimientoEnEdicion = m;
        comboVehiculos.removeAllItems();
        comboVehiculos.addItem(v.getPlaca() + " (" + v.getMarca() + " " + v.getModelo() + ")");
        comboVehiculos.setSelectedItem(v.getPlaca() + " (" + v.getMarca() + " " + v.getModelo() + ")");
        comboServicios.setSelectedItem(m.getTipoServicio());
        comboEstado.setSelectedItem(m.getEstado());
        campoKilometraje.setText(String.valueOf(m.getKilometrajeActual()));
        campoCostoManoObra.setText(String.valueOf(m.getCostoManoObra()).replace(",", "."));
        campoCostoRepuestos.setText(String.valueOf(m.getCostoRepuestos()).replace(",", "."));
        areaDescripcion.setText(m.getDescripcionDetallada());
        btnImprimirSolicitud.setEnabled(true);
    }

    /**
     * @return El objeto Mantenimiento que se está editando, o null si es una nueva orden.
     */
    public Mantenimiento getMantenimientoEnEdicion() { return mantenimientoEnEdicion; }

    /**
     * Extrae la placa del vehículo seleccionado en el ComboBox.
     * @return La placa como String, o null si no hay selección.
     */
    public String getPlacaSeleccionada() {
        String item = (String) comboVehiculos.getSelectedItem();
        if (item != null && item.contains("(")) {
            return item.split("\\(")[0].trim();
        }
        return null;
    }

    // =========================================================================
    // MÉTODOS DE FÁBRICA Y UTILIDAD (HELPERS)
    // =========================================================================

    private void addEtiqueta(JPanel p, String texto, GridBagConstraints gbc) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        l.setForeground(Color.WHITE);
        p.add(l, gbc);
    }

    private void estilizarCampo(JTextField c) {
        c.setFont(new Font("Arial", Font.PLAIN, 14));
        c.setBackground(Color.WHITE);
        c.setForeground(Color.BLACK);
        c.setCaretColor(Color.BLACK);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private BotonFuturista crearBoton(String t, String r) {
        BotonFuturista b = new BotonFuturista(t);
        b.setPreferredSize(new Dimension(160, 80));
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        try {
            File f = new File(r);
            if(f.exists()) b.setIcon(new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH)));
        } catch(Exception e){}
        return b;
    }
}