/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: PanelDashboard.java
 * Descripcion: Muestra la bitacora de todas las ordenes de servicio. Actua como
 *              el centro de operaciones para la gestion de mantenimientos,
 *              con una interfaz grafica basada en iconos para las acciones.
 * Fecha: Noviembre 2025
 * Version: 9.3 (Ajuste final de estilo de botones a version original)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelDashboard extends JPanel {

    // --- Atributos de Componentes UI ---
    public JTable tablaOrdenes;
    public DefaultTableModel modeloTabla;
    public JButton btnAgregarOrden, btnEditarOrden, btnEliminarOrden;

    /**
     * Constructor del panel.
     */
    public PanelDashboard() {
        setLayout(new BorderLayout(10, 20));
        setBackground(new Color(45, 50, 55));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setOpaque(false);

        JLabel lblTitulo = new JLabel("Bitácora y Gestión de Órdenes de Servicio", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
        panelAcciones.setOpaque(false);

        // Se crean los botones usando el nuevo metodo de estilo
        btnAgregarOrden = crearBotonEstiloOriginal("Agregar Orden", "fondo/icono_agregar.png");
        btnEditarOrden = crearBotonEstiloOriginal("Editar", "fondo/icono_editar.png");
        btnEliminarOrden = crearBotonEstiloOriginal("Eliminar", "fondo/icono_eliminar.png");

        panelAcciones.add(btnAgregarOrden);
        panelAcciones.add(btnEditarOrden);
        panelAcciones.add(btnEliminarOrden);
        panelNorte.add(panelAcciones, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID Orden", "Placa", "Marca", "Servicio", "Fecha", "Estado", "Cédula Prop.", "Total ($)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaOrdenes = new JTable(modeloTabla);
        tablaOrdenes.setRowHeight(30);
        tablaOrdenes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaOrdenes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaOrdenes.getColumnModel().getColumn(5).setCellRenderer(new EstadoCellRenderer());

        add(new JScrollPane(tablaOrdenes), BorderLayout.CENTER);

        btnEditarOrden.setEnabled(false);
        btnEliminarOrden.setEnabled(false);
    }

    /**
     * Metodo de fabrica para crear los botones con el estilo visual que te gusta.
     * @param texto El texto que ira debajo del icono.
     * @param rutaIcono La ruta a la imagen del icono.
     * @return Un JButton con el estilo final.
     */
    private JButton crearBotonEstiloOriginal(String texto, String rutaIcono) {
        JButton boton = new JButton();

        // 1. Cargar y escalar el icono
        try {
            ImageIcon icono = new ImageIcon(new ImageIcon(rutaIcono).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            boton.setIcon(icono);
        } catch (Exception e) {
            System.err.println("Error cargando icono: " + rutaIcono);
            boton.setText(texto); // Si falla, muestra solo texto
        }

        // 2. Panel interno para el texto
        JPanel panelTexto = new JPanel();
        panelTexto.setOpaque(false);
        JLabel lblTexto = new JLabel(texto, SwingConstants.CENTER);
        lblTexto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTexto.setForeground(Color.WHITE);
        panelTexto.add(lblTexto);

        // 3. Layout del boton para poner icono arriba y texto abajo
        boton.setLayout(new BorderLayout());
        boton.add(panelTexto, BorderLayout.SOUTH);

        // 4. Estilo general del boton
        boton.setPreferredSize(new Dimension(160, 120)); // Tamaño fijo y generoso
        boton.setBackground(new Color(60, 63, 65));
        boton.setBorder(BorderFactory.createLineBorder(new Color(85, 85, 85)));
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 5. Efecto Hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (boton.isEnabled()) {
                    boton.setBackground(new Color(90, 93, 95));
                    boton.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(60, 63, 65));
                boton.setBorder(BorderFactory.createLineBorder(new Color(85, 85, 85)));
            }
        });

        return boton;
    }

    private static class EstadoCellRenderer extends DefaultTableCellRenderer {
        // ... (Este codigo no cambia, lo dejo por completitud)
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component celda = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String estado = (value == null) ? "" : value.toString();

            switch (estado) {
                case "Pendiente":
                    celda.setBackground(new Color(217, 83, 79));
                    celda.setForeground(Color.WHITE);
                    break;
                case "En Proceso":
                    celda.setBackground(new Color(240, 173, 78));
                    celda.setForeground(Color.BLACK);
                    break;
                case "Finalizado":
                    celda.setBackground(new Color(92, 184, 92));
                    celda.setForeground(Color.WHITE);
                    break;
                default:
                    celda.setBackground(table.getBackground());
                    celda.setForeground(table.getForeground());
                    break;
            }

            if (isSelected) {
                celda.setBackground(celda.getBackground().darker());
            }

            setHorizontalAlignment(CENTER);
            setFont(new Font("Segoe UI", Font.BOLD, 12));

            return celda;
        }
    }
}