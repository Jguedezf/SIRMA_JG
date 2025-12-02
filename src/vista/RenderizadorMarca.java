/*
 * ============================================================================
 * PROYECTO:     Sistema Inteligente de Registro de Mantenimiento Automotriz (SIRMA_JG)
 * INSTITUCIÓN:  Universidad Nacional Experimental de Guayana (UNEG)
 * ASIGNATURA:   Técnicas de Programación 3 - Sección 3
 * AUTORA:       Johanna Gabriela Guedez Flores
 * CÉDULA:       V-14.089.807
 * DOCENTE:      Ing. Dubraska Roca
 * ARCHIVO:      RenderizadorMarca.java
 * FECHA:        Diciembre 2025
 * DESCRIPCIÓN TÉCNICA:
 * Componente de presentación para JTable que enriquece visualmente la columna
 * "Marca". Busca dinámicamente un logo correspondiente a la marca del vehículo
 * y lo muestra junto al texto en la celda, aplicando Herencia y Polimorfismo.
 * ============================================================================
 */
package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;

/**
 * Clase RenderizadorMarca que personaliza la apariencia de las celdas de la
 * columna "Marca" en una JTable, añadiendo un ícono si se encuentra.
 * PRINCIPIO POO: Herencia - Extiende de {@link DefaultTableCellRenderer}.
 */
public class RenderizadorMarca extends DefaultTableCellRenderer {

    /**
     * Sobrescribe el método de renderizado para añadir un ícono junto al nombre de la marca.
     * PRINCIPIO POO: Polimorfismo (Sobrescritura / Overriding) - Redefine el método
     * `getTableCellRendererComponent` para modificar el contenido visual de la celda.
     *
     * @return El componente JLabel configurado con texto y, opcionalmente, un ícono.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        // 1. LLAMADA AL MÉTODO PADRE:
        // Obtiene el componente base (JLabel) con la configuración por defecto.
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // 2. PROCESAMIENTO DEL VALOR:
        String marca = (value != null) ? value.toString() : "";
        setText(marca); // Se asegura de que el texto siempre se muestre.
        setHorizontalAlignment(SwingConstants.LEFT); // Alineación estándar para texto.

        // 3. LIMPIEZA DE ESTADO PREVIO:
        // Las celdas en JTable se reutilizan para mejorar el rendimiento. Es CRUCIAL
        // resetear el ícono en cada llamada para evitar que una celda sin logo
        // muestre el logo de una celda anterior.
        setIcon(null);

        // 4. ALGORITMO DE BÚSQUEDA Y CARGA DINÁMICA DE LOGO:
        if (!marca.isEmpty()) {
            // Se "normaliza" el nombre de la marca (a minúsculas) para construir la ruta del archivo.
            // Ej: "TOYOTA" -> "fondo/toyota.png"
            String ruta = "fondo/" + marca.toLowerCase().trim() + ".png";
            File f = new File(ruta);

            // Validación: Si el archivo del logo existe, se carga y se asigna.
            if (f.exists()) {
                // Se escala la imagen a un tamaño adecuado para la celda (24x24 px).
                ImageIcon icon = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
                setIcon(icon);
                setIconTextGap(10); // Espacio estético entre el ícono y el texto.
            }
        }

        // 5. GESTIÓN DE COLORES DE SELECCIÓN:
        // En renderizadores personalizados que modifican contenido, es una buena práctica
        // gestionar explícitamente los colores de fondo y texto para los estados
        // seleccionado y no seleccionado.
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }
}