/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      PanelContenidoPrincipal.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Clase de la capa de Vista que actúa como el contenedor principal para las
 * diferentes vistas (paneles) de la aplicación. Es gestionado por un CardLayout
 * en VentanaPrincipal para intercambiar las pantallas que ve el usuario.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * PanelContenidoPrincipal es el "lienzo" central de la aplicación donde se muestran
 * los diferentes módulos (Dashboard, Gestión de Vehículos, etc.).
 * PRINCIPIO POO: Herencia - Extiende de {@link JPanel} para ser un contenedor de Swing.
 */
public class PanelContenidoPrincipal extends JPanel {

    // Atributo encapsulado para almacenar la imagen de fondo.
    private Image backgroundImage;

    /**
     * Constructor de la clase.
     * Carga la imagen de fondo desde un archivo. Si la carga falla, establece
     * un color de fondo oscuro como alternativa.
     * Nota: En la arquitectura actual, cada panel hijo dibuja su propio fondo,
     * pero este panel podría centralizarlo si se refactoriza para que los hijos
     * sean transparentes (setOpaque(false)).
     */
    public PanelContenidoPrincipal() {
        // PROCESO: Intenta cargar el recurso gráfico desde el sistema de archivos.
        try {
            backgroundImage = ImageIO.read(new File("fondo/fondo.png"));
        } catch (Exception e) {
            // Manejo de errores: si la imagen no se encuentra, se informa en consola
            // y se establece un color de fondo sólido como respaldo.
            e.printStackTrace();
            setBackground(new Color(45, 50, 55));
        }
    }

    /**
     * Sobrescribe el método de pintado del panel para dibujar el fondo personalizado.
     * Este método es llamado automáticamente por el sistema de renderizado de Swing.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura) - Redefine el comportamiento de
     * `paintComponent` para añadir la lógica de dibujado de la imagen.
     *
     * @param g El contexto gráfico (`Graphics`) proporcionado por Swing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Llama al método de la superclase para asegurar que el pintado base se realice.
        super.paintComponent(g);

        // 1. Dibuja la imagen de fondo, escalándola para que ocupe todo el tamaño del panel.
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // 2. Dibuja una capa oscura semitransparente sobre la imagen.
        // Esto mejora el contraste y la legibilidad del texto y componentes que estén encima.
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}