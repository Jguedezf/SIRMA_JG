/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Descripcion: Define un botón personalizado (JButton) con un estilo moderno
 *              y un efecto visual al pasar el cursor por encima (hover).
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;

public class BotonFuturista extends JButton {

    private Color colorNormal = new Color(50, 55, 60);
    private Color colorHover = new Color(0, 120, 215); // Un azul brillante para el efecto

    public BotonFuturista(String texto) {
        super(texto);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isRollover()) {
            g2.setColor(colorHover);
        } else {
            g2.setColor(colorNormal);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        super.paintComponent(g);
        g2.dispose();
    }
}