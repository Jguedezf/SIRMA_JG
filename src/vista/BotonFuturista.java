/*
 * -----------------------------------------------------------------------------
 * Autora: Johanna Guedez - V14089807
 * Profesora: Ing. Dubraska Roca
 * Descripcion del Programa: Registro de mantenimiento de vehiculo (SIRMA JG)
 *
 * Archivo: BotonFuturista.java
 * Descripcion: Componente visual personalizado (hereda de JButton) que
 *              implementa un efecto visual moderno, con cambio de color al
 *              pasar el mouse (hover) y efecto de relieve.
 * Fecha: Noviembre 2025
 * Version: 3.1
 * -----------------------------------------------------------------------------
 */
package vista;

import javax.swing.*;
import java.awt.*;

public class BotonFuturista extends JButton {

    // --- Atributos de Estilo ---
    private Color colorFondoNormal = new Color(60, 65, 70);
    private Color colorFondoHover = new Color(0, 120, 215);
    private Color colorBordeSombra = new Color(30, 30, 30);
    private Color colorBordeLuz = new Color(100, 100, 100);

    /**
     * Constructor de la clase BotonFuturista.
     * @param texto El texto que se mostrara en el boton.
     */
    public BotonFuturista(String texto) {
        super(texto);
        // --- Configuracion Inicial del Boton ---
        setContentAreaFilled(false); // No queremos el fondo por defecto de Swing.
        setFocusPainted(false);      // No dibujar el recuadro de foco.
        setBorderPainted(false);     // No dibujar el borde por defecto.
        setForeground(Color.WHITE);  // Color del texto.
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a una mano.
    }

    /**
     * Sobrescritura del metodo paintComponent.
     * Este metodo es llamado por Swing cada vez que el boton necesita ser dibujado.
     * Aqui definimos como se vera nuestro boton personalizado.
     * @param g El contexto grafico para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Se crea una copia del contexto grafico para no afectar otros componentes.
        Graphics2D g2d = (Graphics2D) g.create();
        // Activa el antialiasing para que los bordes redondeados se vean suaves.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // --- Logica de Estado del Boton ---
        // getModel() nos da acceso al estado actual del boton (presionado, hover, etc.)
        boolean estaPresionado = getModel().isPressed();
        boolean estaSobre = getModel().isRollover();

        if (estaPresionado) {
            // Si el boton esta presionado, se dibuja un poco mas oscuro para dar efecto de "hundido".
            g2d.setColor(colorFondoHover.darker());
            g2d.fillRoundRect(2, 2, ancho - 4, alto - 4, 15, 15);
        } else {
            // Si no esta presionado, se elige el color segun si el mouse esta encima o no.
            g2d.setColor(estaSobre ? colorFondoHover : colorFondoNormal);
            g2d.fillRoundRect(0, 0, ancho - 2, alto - 2, 15, 15);

            // --- Efecto de Borde 3D (sutil) ---
            g2d.setStroke(new BasicStroke(1f));
            g2d.setColor(colorBordeLuz); // Borde superior e izquierdo
            g2d.drawRoundRect(0, 0, ancho - 2, alto - 2, 15, 15);
            g2d.setColor(colorBordeSombra); // Borde inferior y derecho
            g2d.drawRoundRect(1, 1, ancho - 2, alto - 2, 15, 15);
        }

        // Si el boton esta presionado, se desplaza el texto ligeramente para simular movimiento.
        if (estaPresionado) {
            g.translate(1, 1);
        }

        // Finalmente, se llama al metodo original de JButton para que dibuje el texto del boton.
        super.paintComponent(g);
        g2d.dispose(); // Libera los recursos del contexto grafico.
    }

    // --- Metodos para personalizar colores desde fuera de la clase ---
    public void setColorFondoNormal(Color color) {
        this.colorFondoNormal = color;
        repaint(); // Vuelve a dibujar el boton con el nuevo color.
    }

    public void setColorFondoHover(Color color) {
        this.colorFondoHover = color;
        repaint();
    }
}