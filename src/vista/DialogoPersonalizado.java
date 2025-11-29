/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: DialogoPersonalizado.java
 * Descripcion: Clase que crea una ventana de dialogo personalizada.
 * Fecha: Noviembre 2025
 * Version: 1.1 (Boton centrado)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogoPersonalizado extends JDialog {

    public DialogoPersonalizado(Component owner, String titulo, String mensaje, int tipoMensaje) {
        super((Frame) SwingUtilities.getWindowAncestor(owner), titulo, true);

        setUndecorated(true);
        setLayout(new BorderLayout());

        JPanel panelFondo = new JPanel(new BorderLayout(10, 10));
        panelFondo.setBackground(new Color(45, 50, 55));
        panelFondo.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        JLabel lblIcono = new JLabel(getIconoPorTipo(tipoMensaje));
        lblIcono.setBorder(new EmptyBorder(20, 20, 20, 10));
        panelFondo.add(lblIcono, BorderLayout.WEST);

        JLabel lblMensaje = new JLabel("<html><div style='width: 250px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
        lblMensaje.setForeground(Color.WHITE);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelFondo.add(lblMensaje, BorderLayout.CENTER);

        // --- PANEL DE BOTON (Abajo) ---
        // <<-- CORRECCION: Se usa FlowLayout.CENTER para centrar el boton -->>
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelBoton.setOpaque(false);
        JButton btnOk = new JButton("OK");

        btnOk.setBackground(new Color(80, 85, 90));
        btnOk.setForeground(Color.WHITE);
        btnOk.setFocusPainted(false);
        btnOk.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 125, 130)),
                BorderFactory.createEmptyBorder(5, 25, 5, 25)
        ));
        btnOk.addActionListener(e -> dispose());
        panelBoton.add(btnOk);

        panelFondo.add(panelBoton, BorderLayout.SOUTH);

        add(panelFondo, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(owner);
    }

    private Icon getIconoPorTipo(int tipoMensaje) {
        switch (tipoMensaje) {
            case JOptionPane.ERROR_MESSAGE: return UIManager.getIcon("OptionPane.errorIcon");
            case JOptionPane.WARNING_MESSAGE: return UIManager.getIcon("OptionPane.warningIcon");
            default: return UIManager.getIcon("OptionPane.informationIcon");
        }
    }
}