/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación III - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      BotonFuturista.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Componente de UI personalizado que extiende JButton para crear un botón con
 * una estética moderna. Aplica los principios de Herencia y Polimorfismo
 * para redefinir su apariencia visual mediante la sobreescritura del método
 * 'paintComponent', implementando efectos dinámicos para 'hover' y 'pressed'.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Una clase de botón personalizado que proporciona una estética moderna y consistente.
 * PRINCIPIO POO: Herencia - Esta clase `extends JButton`, heredando toda la
 * funcionalidad base de un botón (manejo de clics, texto, iconos, etc.) y
 * especializándola con un nuevo comportamiento visual.
 */
public class BotonFuturista extends JButton {

    // --- ATRIBUTOS DE ESTILO ---
    // PRINCIPIO POO: Encapsulamiento - Los colores son atributos privados,
    // controlando el estilo interno del componente.
    private Color colorFondo = new Color(60, 65, 70);
    private Color colorHover = new Color(0, 120, 215);
    private Color colorBordeSombra = new Color(30, 30, 30);
    private Color colorBordeLuz = new Color(100, 100, 100);

    /**
     * Constructor principal del BotonFuturista.
     * @param texto El texto que se mostrará en el botón.
     */
    public BotonFuturista(String texto) {
        super(texto); // Llamada al constructor de la clase padre (JButton).

        // --- PROCESO DE CONFIGURACIÓN INICIAL ---
        // Se desactiva el pintado por defecto de Swing para tomar control total del renderizado.
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        // Se establecen propiedades estéticas por defecto.
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(5, 15, 5, 15));
    }

    /**
     * Sobrescribe el método `paintComponent` para personalizar el dibujado del botón.
     * Este es el núcleo del componente y un ejemplo claro de Polimorfismo.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura / Overriding) - Se redefine el
     * método `paintComponent` heredado de JComponent para implementar una lógica
     * de renderizado completamente nueva y personalizada.
     *
     * @param g El contexto gráfico proporcionado por Swing para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Se convierte a Graphics2D para acceder a funcionalidades avanzadas.
        Graphics2D g2 = (Graphics2D) g.create();

        // Se activa el Anti-aliasing para suavizar los bordes y obtener un acabado profesional.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Se consulta el modelo del botón para obtener su estado actual (presionado, hover).
        boolean presionado = getModel().isPressed();
        boolean hover = getModel().isRollover();

        // --- LÓGICA DE RENDERIZADO CONDICIONAL ---
        if (presionado) {
            // Estado presionado: se dibuja un fondo más oscuro y ligeramente desplazado.
            g2.setColor(colorHover.darker());
            g2.fillRoundRect(2, 2, w - 4, h - 4, 15, 15); // Efecto visual de "hundimiento".
        } else {
            // Estado normal o hover: se elige el color correspondiente.
            g2.setColor(hover ? colorHover : colorFondo);
            g2.fillRoundRect(0, 0, w - 2, h - 2, 15, 15);

            // Se dibuja manualmente un borde con efecto 3D (luz y sombra).
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(colorBordeLuz);
            g2.drawRoundRect(0, 0, w - 2, h - 2, 15, 15);
            g2.setColor(colorBordeSombra);
            g2.drawRoundRect(1, 1, w - 1, h - 1, 15, 15);
        }

        // Si el botón está presionado, se desplaza el contexto gráfico 1px
        // para que el texto y el icono también se "hundan".
        if (presionado) { g.translate(1, 1); }

        // Se llama al método `paintComponent` de la clase padre (JButton).
        // ESTA LÍNEA ES CRÍTICA: delega a JButton la tarea de pintar el texto y el icono.
        super.paintComponent(g);

        // Se revierte el desplazamiento para no afectar otros componentes.
        if (presionado) { g.translate(-1, -1); }

        // Se liberan los recursos del contexto gráfico para optimizar memoria.
        g2.dispose();
    }

    /**
     * Permite cambiar el color de fondo base del botón desde fuera de la clase.
     * @param colorFondo El nuevo color de fondo.
     */
    public void setBackgroundColor(Color colorFondo) {
        this.colorFondo = colorFondo;
        repaint(); // Vuelve a dibujar el componente con el nuevo color.
    }
}