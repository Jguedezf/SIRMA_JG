/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: BotonFuturista.java
 * Descripcion: Componente visual personalizado que implementa un efecto 3D
 *              de relieve y hundimiento al hacer clic.
 * Fecha: Noviembre 2025
 * Version: 3.0 (Efecto 3D)
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;

public class BotonFuturista extends JButton {

    private Color colorFondo = new Color(60, 65, 70);
    private Color colorHover = new Color(0, 120, 215);
    private Color colorBordeSombra = new Color(30, 30, 30);
    private Color colorBordeLuz = new Color(100, 100, 100);

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

        int w = getWidth();
        int h = getHeight();
        boolean presionado = getModel().isPressed();
        boolean hover = getModel().isRollover();

        if (presionado) {
            g2.setColor(colorHover.darker());
            g2.fillRoundRect(2, 2, w-4, h-4, 15, 15); // Efecto hundido
        } else {
            g2.setColor(hover ? colorHover : colorFondo);
            g2.fillRoundRect(0, 0, w-2, h-2, 15, 15);

            // Borde 3D
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(colorBordeLuz);
            g2.drawRoundRect(0, 0, w-2, h-2, 15, 15);
            g2.setColor(colorBordeSombra);
            g2.drawRoundRect(1, 1, w-1, h-1, 15, 15);
        }

        if (presionado) { g.translate(1, 1); } // Desplaza texto
        super.paintComponent(g);
        g2.dispose();
    }
}