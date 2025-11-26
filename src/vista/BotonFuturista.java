/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez
 * Profesora: Ing. Dubraska Roca
 * Descripcion: Componente visual personalizado. Se ajusto la visibilidad
 *              para que el boton sea perceptible sin necesidad de 'hover'.
 * Fecha: Noviembre 2025
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;

public class BotonFuturista extends JButton {

    private Color colorFondoNormal = new Color(60, 65, 70); // Gris mas claro, visible
    private Color colorHover = new Color(0, 120, 215); // Azul al pasar el mouse
    private Color colorBorde = new Color(100, 100, 100); // Borde sutil

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

        // Logica de colores
        if (getModel().isRollover()) {
            g2.setColor(colorHover); // Color azul al pasar el mouse
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        } else {
            g2.setColor(colorFondoNormal); // Color base visible
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            // Dibujar borde para dar efecto de relieve sutil
            g2.setColor(colorBorde);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }

        super.paintComponent(g);
        g2.dispose();
    }
}