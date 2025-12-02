/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      RenderizadorEstadoOrden.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Componente de presentación para JTable que aplica formato condicional.
 * Utiliza los principios de Herencia y Polimorfismo para modificar la apariencia
 * de una celda según su contenido, creando un efecto de "semáforo" visual
 * para los diferentes estados de una orden de servicio.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Clase RenderizadorEstadoOrden que personaliza la apariencia de las celdas de la
 * columna "Estado" en una JTable.
 * PRINCIPIO POO: Herencia - Extiende de {@link DefaultTableCellRenderer}, una clase
 * de Swing diseñada específicamente para ser extendida y personalizada.
 */
public class RenderizadorEstadoOrden extends DefaultTableCellRenderer {

    /**
     * Este método es el núcleo del renderizador y se llama automáticamente por la JTable
     * para cada celda que necesita ser dibujada en la columna asignada.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura / Overriding) - Se redefine el método
     * `getTableCellRendererComponent` de la clase padre para implementar una lógica
     * de pintado personalizada.
     *
     * @param table La JTable que está pidiendo el renderizado.
     * @param value El valor contenido en la celda (en este caso, un String como "Finalizado").
     * @param isSelected true si la celda está seleccionada.
     * @param hasFocus true si la celda tiene el foco.
     * @param row El índice de la fila que se está pintando.
     * @param column El índice de la columna que se está pintando.
     * @return El componente (que es un JLabel por herencia) configurado con los nuevos colores y estilos.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        // 1. LLAMADA AL MÉTODO PADRE:
        // Se obtiene el componente JLabel base ya configurado por la superclase
        // con los estilos por defecto (fuente, borde, etc.).
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // 2. PROCESAMIENTO DEL VALOR:
        // Se convierte el valor de la celda a un String para poder evaluarlo.
        String estado = (value != null) ? value.toString() : "";

        // 3. LÓGICA DE FORMATO CONDICIONAL:
        // Se aplican colores solo si la fila no está seleccionada, para no interferir
        // con el color de selección estándar del sistema, mejorando la experiencia de usuario (UX).
        if (!isSelected) {
            switch (estado.toUpperCase()) {
                case "FINALIZADO":
                    c.setBackground(new Color(0, 200, 83)); // Verde
                    c.setForeground(Color.WHITE);
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                case "PENDIENTE":
                    c.setBackground(new Color(211, 47, 47)); // Rojo
                    c.setForeground(Color.WHITE);
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                case "EN PROCESO":
                    c.setBackground(new Color(255, 214, 0)); // Amarillo
                    c.setForeground(Color.BLACK); // Texto negro para mejor contraste con amarillo.
                    setFont(getFont().deriveFont(Font.BOLD));
                    break;
                default:
                    // Si el estado no coincide, se usan los colores por defecto.
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
            }
        } else {
            // Si la fila está seleccionada, se usan los colores de selección definidos
            // por el Look & Feel del sistema para mantener la consistencia.
            c.setBackground(table.getSelectionBackground());
            c.setForeground(table.getSelectionForeground());
        }

        // 4. CONFIGURACIÓN FINAL:
        // Se centra el texto horizontalmente en la celda.
        setHorizontalAlignment(SwingConstants.CENTER);

        // SALIDA: Se retorna el componente configurado para ser dibujado por la JTable.
        return c;
    }
}