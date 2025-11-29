/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelBusquedaInteligente.java
 * Descripcion: Panel de interfaz de usuario (UI) dedicado a la busqueda y
 *              filtrado avanzado de ordenes de servicio. Permite al usuario
 *              localizar registros especificos usando multiples criterios.
 * Fecha: Noviembre 2025
 * Version: 2.0 (Layout Mejorado y Funcional)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelBusquedaInteligente extends JPanel {

    // --- Atributos de Componentes UI ---
    public JTextField txtBusquedaGeneral;
    public JComboBox<String> comboEstado;
    public JTextField txtFechaDesde, txtFechaHasta;
    public BotonFuturista btnRealizarBusqueda;
    public BotonFuturista btnLimpiarBusqueda;

    /**
     * Constructor del panel.
     */
    public PanelBusquedaInteligente() {
        setBackground(new Color(45, 50, 55));
        // Se usa BorderLayout para controlar las areas superior, central e inferior.
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 50, 50, 50));

        // --- TITULO (Area Superior) ---
        JLabel lblTitulo = new JLabel("Centro de Búsqueda Inteligente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 40, 0)); // Margen inferior para separarlo del contenido
        add(lblTitulo, BorderLayout.NORTH);

        // --- PANEL DE FILTROS (Area Central) ---
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.WEST; // Alinear componentes a la izquierda

        // Fila 1: Busqueda por Placa/ID
        txtBusquedaGeneral = new JTextField(25);
        gbc.gridx = 0; gbc.gridy = 0;
        panelFiltros.add(crearCampoConIcono("Buscar por Placa o ID de Orden:", "fondo/icono_lupa.png", txtBusquedaGeneral), gbc);

        // Fila 2: Filtro por Estado
        comboEstado = new JComboBox<>(new String[]{"Cualquier Estado", "Pendiente", "En Proceso", "Finalizado"});
        comboEstado.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        panelFiltros.add(crearCampoConIcono("Filtrar por Estado de la Orden:", "fondo/icono_estado.png", comboEstado), gbc);

        // Fila 3: Fecha Desde
        txtFechaDesde = new JTextField(15);
        gbc.gridy = 2;
        panelFiltros.add(crearCampoConIcono("Desde Fecha (DD-MM-AAAA):", "fondo/icono_calendario.png", txtFechaDesde), gbc);

        // Fila 4: Fecha Hasta
        txtFechaHasta = new JTextField(15);
        gbc.gridy = 3;
        panelFiltros.add(crearCampoConIcono("Hasta Fecha (DD-MM-AAAA):", "fondo/icono_calendario.png", txtFechaHasta), gbc);

        add(panelFiltros, BorderLayout.CENTER);

        // --- PANEL DE BOTONES (Area Inferior) ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(new EmptyBorder(30, 0, 20, 0)); // Margen superior para separarlo

        btnRealizarBusqueda = new BotonFuturista("Realizar Búsqueda");
        btnLimpiarBusqueda = new BotonFuturista("Limpiar Filtros");
        panelBotones.add(btnRealizarBusqueda);
        panelBotones.add(btnLimpiarBusqueda);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Metodo de fabrica para crear un panel que combina un icono, una etiqueta y un componente de entrada.
     * @param labelText El texto de la etiqueta.
     * @param iconPath La ruta al icono.
     * @param component El JTextField, JComboBox, etc. que se agregara.
     * @return un JPanel con el layout combinado.
     */
    private JPanel crearCampoConIcono(String labelText, String iconPath, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setOpaque(false);

        // Icono
        ImageIcon icon = new ImageIcon(iconPath);
        JLabel lblIcono = new JLabel(icon);
        panel.add(lblIcono);

        // Etiqueta
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        panel.add(label);

        // Componente de entrada
        component.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(component);

        return panel;
    }

    /**
     * Metodo publico para limpiar todos los filtros a su estado por defecto.
     */
    public void limpiarFiltros() {
        txtBusquedaGeneral.setText("");
        comboEstado.setSelectedIndex(0);
        txtFechaDesde.setText("");
        txtFechaHasta.setText("");
    }
}